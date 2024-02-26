package ru.kpfu.itis.feature.favorite.impl.presentation


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.launch
import ru.kpfu.itis.core.designsystem.MoviesAppTheme
import ru.kpfu.itis.core.navigation.SharedScreen
import ru.kpfu.itis.core.widget.KinopoiskConfirmDialog
import ru.kpfu.itis.core.widget.KinopoiskErrorMessage
import ru.kpfu.itis.core.widget.KinopoiskFilmCard
import ru.kpfu.itis.core.widget.KinopoiskProgressBar
import ru.kpfu.itis.core.widget.KinopoiskSnackbar
import ru.kpfu.itis.core.widget.KinopoiskTopBar
import ru.kpfu.itis.core.widget.model.ErrorType
import ru.kpfu.itis.core.widget.model.FilmBrief
import ru.kpfu.itis.feature.favorite.impl.R


internal class FavoritesScreen() : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<FavoritesViewModel>()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()
        val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)
        val snackbarHostState = remember { SnackbarHostState() }

        FavoritesScreenContent(
            screenState = screenState,
            eventHandler = viewModel::eventHandler,
            snackbarHostState = snackbarHostState
        )

        FavoritesScreenActions(
            screenAction = screenAction,
            snackbarHostState = snackbarHostState
        )
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesScreenContent(
    screenState: FavoritesViewModel.FavoritesScreenState,
    eventHandler: (FavoritesViewModel.FavoritesScreenEvent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    val scrollBehavior = when (screenState.error) {
        null -> TopAppBarDefaults.enterAlwaysScrollBehavior(state = rememberTopAppBarState())
        else -> TopAppBarDefaults.pinnedScrollBehavior(state = rememberTopAppBarState())
    }

    Scaffold(
        snackbarHost = {
            KinopoiskSnackbar(snackbarHostState = snackbarHostState)
        },
        modifier = Modifier.nestedScroll(connection = scrollBehavior.nestedScrollConnection),
        topBar = {
            KinopoiskTopBar(
                scrollBehavior = scrollBehavior,
                title = stringResource(id = R.string.favorite),
                onClick = { eventHandler(FavoritesViewModel.FavoritesScreenEvent.OnSearchIconClick) }
            )
        }
    ) { contentPadding ->
        when (screenState.isLoading) {
            true -> KinopoiskProgressBar(shouldShow = true)
            false -> {
                if (screenState.favoriteFilms.isEmpty()) {
                    KinopoiskErrorMessage(errorType = ErrorType.NO_DATA_IN_DB)
                } else {
                    FavoriteFilms(
                        contentPadding = contentPadding,
                        favoriteFilms = screenState.favoriteFilms,
                        onClick = {
                            eventHandler(
                                FavoritesViewModel.FavoritesScreenEvent.OnFilmCardClick(
                                    filmId = it
                                )
                            )
                        },
                        onPress = {
                            eventHandler(
                                FavoritesViewModel.FavoritesScreenEvent.OnFilmCardPress(
                                    filmBrief = it
                                )
                            )
                        }
                    )
                }
            }
        }

        if (screenState.error != null) {
            KinopoiskErrorMessage(errorType = screenState.error) {
                eventHandler(FavoritesViewModel.FavoritesScreenEvent.OnRetryButtonClick)
            }
        }

        if (screenState.shouldShowDialog) {
            screenState.selectedFilm?.let { selectedFilm ->
                KinopoiskConfirmDialog(
                    title = "Удалить \"${selectedFilm.nameRu}\" из избранного?",
                    onDismissRequest = { eventHandler(FavoritesViewModel.FavoritesScreenEvent.OnDialogDismissRequest) },
                    onConfirmButtonClick = {
                        eventHandler(
                            FavoritesViewModel.FavoritesScreenEvent.OnConfirmDialog(
                                selectedFilm
                            )
                        )
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FavoriteFilms(
    contentPadding: PaddingValues,
    favoriteFilms: PersistentList<FilmBrief>,
    onClick: (Int) -> Unit,
    onPress: (FilmBrief) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(space = 12.dp),
        contentPadding = PaddingValues(all = 16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .background(MoviesAppTheme.color.background)
    ) {
        items(
            items = favoriteFilms,
            key = { it.filmId },
            contentType = { "Top100Films" }
        ) { filmBrief ->
            KinopoiskFilmCard(
                modifier = Modifier.animateItemPlacement(),
                isFavorite = true,
                filmBrief = filmBrief,
                onClick = { onClick(filmBrief.filmId) },
                onPress = { onPress(filmBrief) }
            )
        }
    }
}

@Composable
private fun FavoritesScreenActions(
    screenAction: FavoritesViewModel.FavoritesScreenAction?,
    snackbarHostState: SnackbarHostState
) {
    val coroutineScope = rememberCoroutineScope()
    val navigator = LocalNavigator.currentOrThrow


    LaunchedEffect(screenAction) {
        when (screenAction) {
            null -> Unit

            is FavoritesViewModel.FavoritesScreenAction.NavigateDetails -> {
                navigator.push(
                    item = ScreenRegistry.get(
                        provider = SharedScreen.DetailsScreen(
                            screenAction.kinopoiskId
                        )
                    )
                )
            }

            is FavoritesViewModel.FavoritesScreenAction.ShowSnackbar -> coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = screenAction.message,
                    duration = SnackbarDuration.Short
                )
            }

            FavoritesViewModel.FavoritesScreenAction.NavigateSearchScreen ->
                navigator.push(
                    item = ScreenRegistry.get(
                        provider = SharedScreen.SearchScreen
                    )
                )

        }
    }
}
