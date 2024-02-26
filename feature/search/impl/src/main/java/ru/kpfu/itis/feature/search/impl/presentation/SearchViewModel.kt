package ru.kpfu.itis.feature.search.impl.presentation


import androidx.compose.runtime.Immutable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.kpfu.itis.core.widget.model.ErrorType
import ru.kpfu.itis.core.widget.model.FilmBrief
import ru.kpfu.itis.feature.search.api.GetFilmsByQueryUseCase
import timber.log.Timber
import java.net.UnknownHostException

class SearchViewModel(
    private val getFilmsByQueryUseCase: GetFilmsByQueryUseCase,
) : ScreenModel {

    private val _screenState = MutableStateFlow(SearchScreenState())
    val screenState: StateFlow<SearchScreenState> = _screenState.asStateFlow()

    private val _screenAction = MutableSharedFlow<SearchScreenAction?>()
    val screenAction: SharedFlow<SearchScreenAction?> = _screenAction.asSharedFlow()

    private val _searchInput = MutableStateFlow("")
    val searchInput: StateFlow<String> = _searchInput.asStateFlow()

    private suspend fun searchFromRemoteSource(
        query: String
    ): StateFlow<List<FilmBrief>> = getFilmsByQueryUseCase(query = query)
        .flowOn(Dispatchers.IO)
        .onStart {
            _screenState.emit(
                _screenState.value.copy(
                    isLoading = true,
                    error = null
                )
            )
        }
        .onCompletion {
            _screenState.emit(
                _screenState.value.copy(
                    isLoading = false
                )
            )
        }
        .catch {
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
            Timber.d(it, "Search from Flow")
        }
        .map { films ->
            films.map {
                FilmBrief(
                    filmId = it.id,
                    nameRu = it.name ?: "",
                    posterUrlPreview = it.preview ?: "",
                    year = it.year ?: "",
                    genre = it.genre ?: "",
                )
            }
        }
        .onEach {
            _screenState.emit(
                _screenState.value.copy(
                    isSearchSuccessful = it.isNotEmpty()
                )
            )
        }
        .stateIn(screenModelScope)


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResult: Flow<List<FilmBrief>> = _searchInput
        .debounce(1000)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            when {
                query.isEmpty() -> flowOf(persistentListOf())
                else -> searchFromRemoteSource(query = query)
            }
        }

    @Immutable
    data class SearchScreenState(
        val shouldShowSearch: Boolean = false,
        val isLoading: Boolean = false,
        val error: ErrorType? = null,
        val isSearchSuccessful: Boolean = false
    )

    @Immutable
    sealed interface SearchScreenEvent {
        data object OnArrowBackClick : SearchScreenEvent
        data class OnQueryChange(val query: String) : SearchScreenEvent
        data class OnSearchResultClick(val filmId: Int) : SearchScreenEvent
        data class OnActiveChange(val isActive: Boolean) : SearchScreenEvent
    }

    @Immutable
    sealed interface SearchScreenAction {
        data object NavigateUp : SearchScreenAction
        data class NavigateDetails(val filmId: Int) : SearchScreenAction
    }

    fun eventHandler(event: SearchScreenEvent) {
        when (event) {
            SearchScreenEvent.OnArrowBackClick -> onArrowBackClick()
            is SearchScreenEvent.OnQueryChange -> onQueryChange(event.query)
            is SearchScreenEvent.OnSearchResultClick -> onSearchResultClick(event.filmId)
            is SearchScreenEvent.OnActiveChange -> onActiveChange(event.isActive)
        }
    }

    private fun onArrowBackClick() = screenModelScope.launch {
        _screenAction.emit(
            SearchScreenAction.NavigateUp
        )
    }

    private fun onQueryChange(query: String) = screenModelScope.launch {
        _searchInput.value = query
    }

    private fun onSearchResultClick(filmId: Int) = screenModelScope.launch {
        _screenAction.emit(
            SearchScreenAction.NavigateDetails(filmId = filmId)
        )
    }

    private fun onActiveChange(isActive: Boolean) = screenModelScope.launch {
        _screenState.emit(
            _screenState.value.copy(
                shouldShowSearch = isActive
            )
        )
    }
}
