package ru.kpfu.itis.feature.popular.impl.presentation

import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.kpfu.itis.core.widget.model.ErrorType
import ru.kpfu.itis.core.widget.model.FilmBrief
import ru.kpfu.itis.feature.favorite.api.useCase.AddFavoriteFilmUseCase
import ru.kpfu.itis.feature.popular.api.GetPopularFilmsUseCase
import timber.log.Timber
import java.net.UnknownHostException

class PopularViewModel(
    private val getPopularFilmsUseCase: GetPopularFilmsUseCase,
    private val addFavoriteFilmUseCase: AddFavoriteFilmUseCase,
) : ScreenModel {

    private val _screenState = MutableStateFlow(PopularScreenState())
    val screenState: StateFlow<PopularScreenState> = _screenState.asStateFlow()

    private val _screenAction = MutableSharedFlow<PopularScreenAction?>()
    val screenAction: SharedFlow<PopularScreenAction?> = _screenAction.asSharedFlow()

    @Immutable
    data class PopularScreenState(
        val isLoading: Boolean = false,
        val popularFilms: PersistentList<FilmBrief> = persistentListOf(),
        val favoriteFilmsIds: PersistentList<Int> = persistentListOf(),
        val error: ErrorType? = null,
        val shouldShowDialog: Boolean = false,
        val selectedFilm: FilmBrief? = null
    )

    @Immutable
    sealed interface PopularScreenEvent {
        data object OnInit : PopularScreenEvent
        data class OnFilmCardClick(val filmId: Int) : PopularScreenEvent
        data object OnRetryButtonClick : PopularScreenEvent
        data object OnSearchIconClick : PopularScreenEvent
        data class OnFilmCardPress(val filmBrief: FilmBrief) : PopularScreenEvent
        data object OnDialogDismissRequest : PopularScreenEvent
        data class OnConfirmDialog(val filmBrief: FilmBrief) : PopularScreenEvent
    }

    @Immutable
    sealed interface PopularScreenAction {
        data class NavigateDetails(val filmId: Int) : PopularScreenAction
        data object NavigateSearchScreen : PopularScreenAction
        data class ShowSnackbar(val message: String) : PopularScreenAction
    }

    init {
        eventHandler(PopularScreenEvent.OnInit)
    }

    fun eventHandler(event: PopularScreenEvent) {
        when (event) {
            PopularScreenEvent.OnInit -> onInit()
            is PopularScreenEvent.OnFilmCardClick -> onFilmCardClick(event.filmId)
            PopularScreenEvent.OnRetryButtonClick -> onRetryButtonClick()
            PopularScreenEvent.OnSearchIconClick -> onSearchIconClick()
            is PopularScreenEvent.OnFilmCardPress -> onFilmCardPress(event.filmBrief)
            PopularScreenEvent.OnDialogDismissRequest -> onDialogDismissRequest()
            is PopularScreenEvent.OnConfirmDialog -> onConfirmDialog(event.filmBrief)
        }
    }

    private fun getPopularFilms() = screenModelScope.launch {

        getPopularFilmsUseCase().flowOn(Dispatchers.IO)
            .onStart {
                _screenState.emit(
                    _screenState.value.copy(
                        isLoading = true
                    )
                )
            }
            .onCompletion {
                _screenState.emit(
                    _screenState.value.copy(
                        isLoading = false
                    )
                )
            }.catch {
                when (it) {
                    is UnknownHostException -> _screenState.emit(
                        _screenState.value.copy(
                            error = ErrorType.UNKNOWN_HOST_EXCEPTION
                        )
                    )

                    else -> _screenState.emit(
                        _screenState.value.copy(
                            error = ErrorType.OTHER
                        )
                    )
                }
                Timber.d(it, "Popular Screen from Flow")
            }.collect {

                _screenState.emit(
                    _screenState.value.copy(
                        popularFilms = it.map { film ->
                            FilmBrief(
                                filmId = film.id,
                                nameRu = film.name ?: "",
                                posterUrlPreview = film.preview ?: "",
                                year = film.year ?: "",
                                genre = film.genre ?: "",
                                isFavorite = film.isFavorite ?: false
                            )
                        }.toPersistentList(),
                        error = null
                    )
                )
            }
    }

    private fun onInit() = getPopularFilms()

    private fun onFilmCardClick(filmId: Int) = screenModelScope.launch {
        _screenAction.emit(
            PopularScreenAction.NavigateDetails(filmId = filmId)
        )
    }

    private fun onRetryButtonClick() = getPopularFilms()

    private fun onSearchIconClick() = screenModelScope.launch {
        _screenAction.emit(
            PopularScreenAction.NavigateSearchScreen
        )
    }

    private fun onFilmCardPress(filmBrief: FilmBrief) = screenModelScope.launch {
        _screenState.emit(
            _screenState.value.copy(
                shouldShowDialog = true,
                selectedFilm = filmBrief
            )
        )
    }

    private fun onDialogDismissRequest() = screenModelScope.launch {
        _screenState.emit(
            _screenState.value.copy(
                shouldShowDialog = false
            )
        )
    }

    private fun onConfirmDialog(filmBrief: FilmBrief) = screenModelScope.launch {
        addFavoriteFilmUseCase(filmBrief.filmId).flowOn(Dispatchers.IO)
            .onStart {
                _screenState.emit(
                    _screenState.value.copy(
                        shouldShowDialog = false
                    )
                )
            }
            .catch {
                _screenAction.emit(
                    PopularScreenAction.ShowSnackbar(
                        message = "Ошибка"
                    )
                )
                Timber.d(it, "Ошибка добавления в избранное from Flow")
            }
            .collect {
                _screenAction.emit(
                    PopularScreenAction.ShowSnackbar(
                        message = "Фильм \"${filmBrief.nameRu}\" добавлен в избранное"
                    )
                )
            }
    }
}
