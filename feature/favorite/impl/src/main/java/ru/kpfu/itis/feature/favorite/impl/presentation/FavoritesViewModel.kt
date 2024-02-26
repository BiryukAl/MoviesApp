package ru.kpfu.itis.feature.favorite.impl.presentation

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
import kotlinx.coroutines.launch
import ru.kpfu.itis.core.widget.model.ErrorType
import ru.kpfu.itis.core.widget.model.FilmBrief
import ru.kpfu.itis.feature.favorite.api.FavoriteFilm
import ru.kpfu.itis.feature.favorite.api.useCase.DeleteFavoriteFilmUseCase
import ru.kpfu.itis.feature.favorite.api.useCase.GetAllFavoriteFilmUseCase
import timber.log.Timber

internal class FavoritesViewModel(
    private val deleteFavoriteFilmUseCase: DeleteFavoriteFilmUseCase,
    private val getAllFavoriteFilmUseCase: GetAllFavoriteFilmUseCase,
) : ScreenModel {


    private val _screenState = MutableStateFlow(FavoritesScreenState())
    val screenState: StateFlow<FavoritesScreenState> = _screenState.asStateFlow()

    private val _screenAction = MutableSharedFlow<FavoritesScreenAction?>()
    val screenAction: SharedFlow<FavoritesScreenAction?> = _screenAction.asSharedFlow()

    @Immutable
    data class FavoritesScreenState(
        val isLoading: Boolean = false,
        val favoriteFilms: PersistentList<FilmBrief> = persistentListOf(),
        val error: ErrorType? = null,
        val shouldShowDialog: Boolean = false,
        val selectedFilm: FilmBrief? = null
    )

    @Immutable
    sealed interface FavoritesScreenEvent {
        data object OnInit : FavoritesScreenEvent
        data class OnFilmCardClick(val filmId: Int) : FavoritesScreenEvent
        data class OnFilmCardPress(val filmBrief: FilmBrief) : FavoritesScreenEvent
        data object OnSearchIconClick : FavoritesScreenEvent
        data object OnRetryButtonClick : FavoritesScreenEvent
        data object OnDialogDismissRequest : FavoritesScreenEvent
        data class OnConfirmDialog(val filmBrief: FilmBrief) : FavoritesScreenEvent
    }

    @Immutable
    sealed interface FavoritesScreenAction {
        data class NavigateDetails(val kinopoiskId: Int) : FavoritesScreenAction
        data class ShowSnackbar(val message: String) : FavoritesScreenAction
        data object NavigateSearchScreen : FavoritesScreenAction
    }

    init {
        eventHandler(FavoritesScreenEvent.OnInit)
    }

    fun eventHandler(event: FavoritesScreenEvent) {
        when (event) {
            FavoritesScreenEvent.OnInit -> onInit()
            is FavoritesScreenEvent.OnFilmCardClick -> onFilmCardClick(event.filmId)
            is FavoritesScreenEvent.OnFilmCardPress -> onFilmCardPress(event.filmBrief)
            FavoritesScreenEvent.OnSearchIconClick -> onSearchIconClick()
            FavoritesScreenEvent.OnRetryButtonClick -> onRetryButtonClick()
            is FavoritesScreenEvent.OnConfirmDialog -> onConfirmDialog(event.filmBrief)
            FavoritesScreenEvent.OnDialogDismissRequest -> onDialogDismissRequest()
        }
    }


    private fun getAllFavoriteFilms() = screenModelScope.launch {
        getAllFavoriteFilmUseCase()
            .flowOn(Dispatchers.IO)
            .catch {
                _screenState.emit(
                    _screenState.value.copy(
                        error = ErrorType.OTHER
                    )
                )
                Timber.d(it, "All favorite in Flow")
            }
            .collect {
                it.fold(
                    onSuccess = { favoriteFilms ->
                        _screenState.emit(
                            _screenState.value.copy(
                                favoriteFilms = favoriteFilms.map { film -> film.toFilmBrief() }
                                    .toPersistentList(),
                                error = null
                            )
                        )
                    },
                    onFailure = { error ->
                        _screenState.emit(
                            _screenState.value.copy(
                                error = ErrorType.NO_DATA_IN_DB
                            )
                        )
                        Timber.d(error, "All favorite from DB")
                    }
                )
            }
    }

    private fun onInit() = getAllFavoriteFilms()

    private fun onFilmCardClick(kinopoiskId: Int) = screenModelScope.launch {
        _screenAction.emit(
            FavoritesScreenAction.NavigateDetails(kinopoiskId = kinopoiskId)
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

    private fun onSearchIconClick() = screenModelScope.launch {
        _screenAction.emit(
            FavoritesScreenAction.NavigateSearchScreen
        )
    }

    private fun onRetryButtonClick() = getAllFavoriteFilms()

    private fun onDialogDismissRequest() = screenModelScope.launch {
        _screenState.emit(
            _screenState.value.copy(
                shouldShowDialog = false
            )
        )
    }

    private fun onConfirmDialog(filmBrief: FilmBrief) = screenModelScope.launch {
        deleteFavoriteFilmUseCase(id = filmBrief.filmId).fold(
            onSuccess = {
                _screenState.emit(
                    _screenState.value.copy(
                        shouldShowDialog = false,
                        favoriteFilms = _screenState.value.favoriteFilms.remove(filmBrief)
                    )
                )
                _screenAction.emit(
                    FavoritesScreenAction.ShowSnackbar(
                        message = "Фильм \"${filmBrief.nameRu}\" удален из избранного"
                    )
                )
            },
            onFailure = {
                _screenAction.emit(
                    FavoritesScreenAction.ShowSnackbar(
                        message = "Ошибка удаления"

                    )
                )
                Timber.d(it, "Ошибка удаления")
            }
        )
    }

}


fun FavoriteFilm.toFilmBrief(): FilmBrief = FilmBrief(
    filmId = kinopoiskId,
    nameRu = name ?: "",
    posterUrlPreview = previewUrl ?: "",
    year = year?.toString() ?: "",
    genre = genres?.first() ?: "",
    isFavorite = true,
)
