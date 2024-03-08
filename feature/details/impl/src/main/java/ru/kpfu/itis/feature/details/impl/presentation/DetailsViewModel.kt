package ru.kpfu.itis.feature.details.impl.presentation


import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
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
import ru.kpfu.itis.core.util.AppDispatchers
import ru.kpfu.itis.core.widget.model.ErrorType
import ru.kpfu.itis.feature.details.api.FilmDetail
import ru.kpfu.itis.feature.details.api.GetFilmDetailsUseCase
import timber.log.Timber
import java.net.UnknownHostException

internal class DetailsViewModel(
    private val filmId: Int,
    private val getFilmDetailsUseCase: GetFilmDetailsUseCase,
    private val appDispatchers: AppDispatchers,
) : ScreenModel {

    private val _screenState = MutableStateFlow(DetailsScreenState())
    val screenState: StateFlow<DetailsScreenState> = _screenState.asStateFlow()

    private val _screenAction = MutableSharedFlow<DetailsScreenAction?>()
    val screenAction: SharedFlow<DetailsScreenAction?> = _screenAction.asSharedFlow()

    @Immutable
    data class DetailsScreenState(
        val isLoading: Boolean = false,
        val filmDetails: FilmDetail? = null,
        val error: ErrorType? = null
    )

    @Immutable
    sealed interface DetailsScreenEvent {
        data object OnInit : DetailsScreenEvent
        data object OnArrowBackClick : DetailsScreenEvent
        data object OnRetryButtonClick : DetailsScreenEvent
    }

    @Immutable
    sealed interface DetailsScreenAction {
        data object NavigateUp : DetailsScreenAction
    }

    init {
        eventHandler(DetailsScreenEvent.OnInit)
    }

    fun eventHandler(event: DetailsScreenEvent) {
        when (event) {
            DetailsScreenEvent.OnInit -> onInit()
            DetailsScreenEvent.OnArrowBackClick -> onArrowBackClick()
            DetailsScreenEvent.OnRetryButtonClick -> onRetryButtonClick()
        }
    }

    private fun getFilmDetail(filmId: Int) = screenModelScope.launch {
        getFilmDetailsUseCase(id = filmId).flowOn(appDispatchers.io)
            .onStart {
                _screenState.emit(
                    _screenState.value.copy(
                        isLoading = true
                    )
                )
            }.onCompletion {
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
                Timber.d(it, "Film details filmId: $filmId")
            }.collect { filmDetails ->
                _screenState.emit(
                    _screenState.value.copy(
                        filmDetails = filmDetails,
                        error = null
                    )
                )

            }
    }


    private fun onInit() {
        getFilmDetail(filmId = filmId)
    }

    private fun onArrowBackClick() = screenModelScope.launch {
        _screenAction.emit(
            DetailsScreenAction.NavigateUp
        )
    }

    private fun onRetryButtonClick() {
        getFilmDetail(filmId = filmId)
    }
}
