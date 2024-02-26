package ru.kpfu.itis.feature.search.impl.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.collections.immutable.persistentListOf
import ru.kpfu.itis.core.designsystem.MoviesAppTheme
import ru.kpfu.itis.core.designsystem.icon.MoviesAppIcons
import ru.kpfu.itis.core.navigation.SharedScreen
import ru.kpfu.itis.core.widget.KinopoiskButton
import ru.kpfu.itis.core.widget.KinopoiskErrorMessage
import ru.kpfu.itis.core.widget.KinopoiskFilmCard
import ru.kpfu.itis.core.widget.KinopoiskIconButton
import ru.kpfu.itis.core.widget.KinopoiskProgressBar
import ru.kpfu.itis.core.widget.model.ErrorType
import ru.kpfu.itis.core.widget.model.FilmBrief
import ru.kpfu.itis.feature.search.impl.R
import timber.log.Timber


internal class SearchScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<SearchViewModel>()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()
        val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)
        val searchInput by viewModel.searchInput.collectAsStateWithLifecycle()
        val searchResult by viewModel.searchResult.collectAsStateWithLifecycle(initialValue = persistentListOf())

        SearchScreenContent(
            screenState = screenState,
            searchInput = searchInput,
            searchResult = searchResult,
            eventHandler = viewModel::eventHandler
        )

        SearchScreenActions(
            screenAction = screenAction
        )
    }


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun SearchScreenContent(
        screenState: SearchViewModel.SearchScreenState,
        searchInput: String,
        searchResult: List<FilmBrief>,
        eventHandler: (SearchViewModel.SearchScreenEvent) -> Unit
    ) {
        Timber.d("SearchScreenContent searchResult.size:$searchResult.size")
        val keyboardController = LocalSoftwareKeyboardController.current
        val focusManager = LocalFocusManager.current

        Scaffold { _ ->
            FilmSearchBar(
                errorType = screenState.error,
                isLoading = screenState.isLoading,
                searchResult = searchResult,
                searchInput = searchInput,
                onQueryChange = { eventHandler(SearchViewModel.SearchScreenEvent.OnQueryChange(query = it)) },
                onSearch = {
                    keyboardController?.hide()
                    focusManager.clearFocus(true)
                },
                active = screenState.shouldShowSearch,
                onActiveChange = {
                    eventHandler(
                        SearchViewModel.SearchScreenEvent.OnActiveChange(
                            isActive = it
                        )
                    )
                },
                isSearchSuccessful = screenState.isSearchSuccessful,
                onArrowBackClick = { eventHandler(SearchViewModel.SearchScreenEvent.OnArrowBackClick) },
                onSearchResultClick = {
                    eventHandler(
                        SearchViewModel.SearchScreenEvent.OnSearchResultClick(
                            filmId = it
                        )
                    )
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun FilmSearchBar(
        errorType: ErrorType?,
        isLoading: Boolean,
        searchResult: List<FilmBrief>,
        searchInput: String,
        onQueryChange: (String) -> Unit,
        onSearch: (String) -> Unit,
        active: Boolean,
        onActiveChange: (Boolean) -> Unit,
        isSearchSuccessful: Boolean,
        onArrowBackClick: () -> Unit,
        onSearchResultClick: (Int) -> Unit
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = searchInput,
            onQueryChange = onQueryChange,
            onSearch = onSearch,
            active = active,
            onActiveChange = onActiveChange,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search),
                    style = MoviesAppTheme.type.searchPlaceholder,
                    color = MoviesAppTheme.color.secondaryText
                )
            },
            leadingIcon = {
                KinopoiskIconButton(
                    icon = MoviesAppIcons.ArrowBack,
                    action = onArrowBackClick
                )
            },
            colors = SearchBarDefaults.colors(
                containerColor = MoviesAppTheme.color.background,
                dividerColor = MoviesAppTheme.color.background
            )
        ) {
            if (isLoading) {
                KinopoiskProgressBar(shouldShow = true)
            } else {
                when {
                    errorType != null -> KinopoiskErrorMessage(
                        errorType = errorType,
                        onClick = {}
                    )

                    isSearchSuccessful -> SearchResultList(
                        searchResultList = searchResult,
                        onClick = onSearchResultClick
                    )

                    !isSearchSuccessful -> EmptySearchResult(onClick = onArrowBackClick)
                }
            }
        }
    }

    @Composable
    private fun EmptySearchResult(onClick: () -> Unit) {
        Box(modifier = Modifier.fillMaxSize()) {
            KinopoiskButton(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(id = R.string.not_found),
                containerColor = MoviesAppTheme.color.primary,
                contentColor = MoviesAppTheme.color.onPrimary,
                onClick = onClick
            )
        }
    }

    @Composable
    private fun SearchResultList(
        searchResultList: List<FilmBrief>,
        onClick: (Int) -> Unit
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 48.dp),
            verticalArrangement = Arrangement.spacedBy(space = 12.dp),
            contentPadding = PaddingValues(all = 16.dp)
        ) {
            items(
                items = searchResultList,
                key = { it.filmId },
                contentType = { "SearchResult" }
            ) { filmBrief ->
                KinopoiskFilmCard(
                    filmBrief = filmBrief,
                    onClick = { onClick(filmBrief.filmId) }
                ) {}
            }
        }
    }

    @Composable
    private fun SearchScreenActions(
        screenAction: SearchViewModel.SearchScreenAction?
    ) {
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(screenAction) {
            when (screenAction) {
                null -> Unit
                is SearchViewModel.SearchScreenAction.NavigateDetails ->
                    navigator.push(
                        item = ScreenRegistry.get(
                            provider = SharedScreen.DetailsScreen(screenAction.filmId)
                        )
                    )

                is SearchViewModel.SearchScreenAction.NavigateUp -> navigator.pop()
            }
        }
    }
}
