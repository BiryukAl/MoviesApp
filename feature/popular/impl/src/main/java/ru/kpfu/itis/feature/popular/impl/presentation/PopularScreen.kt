package ru.kpfu.itis.feature.popular.impl.presentation

import android.annotation.SuppressLint
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
import ru.kpfu.itis.core.widget.model.FilmBrief

internal class PopularScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = getScreenModel<PopularViewModel>()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()
        val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)
        val snackbarHostState = remember { SnackbarHostState() }

        PopularScreenContent(
            screenState = screenState,
            eventHandler = viewModel::eventHandler,
            snackbarHostState = snackbarHostState
        )

        PopularScreenActions(
            screenAction = screenAction,
            snackbarHostState = snackbarHostState
        )
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun PopularScreenContent(
        screenState: PopularViewModel.PopularScreenState,
        eventHandler: (PopularViewModel.PopularScreenEvent) -> Unit,
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
                    title = PopularTab.options.title,
                    onClick = { eventHandler(PopularViewModel.PopularScreenEvent.OnSearchIconClick) }
                )
            }
        ) { contentPadding ->
            when (screenState.isLoading) {
                true -> KinopoiskProgressBar(shouldShow = true)
                false -> TopFilmsList(
                    contentPadding = contentPadding,
                    films = screenState.popularFilms,
                    onClick = {
                        eventHandler(
                            PopularViewModel.PopularScreenEvent.OnFilmCardClick(
                                filmId = it
                            )
                        )
                    },
                    onPress = {
                        eventHandler(
                            PopularViewModel.PopularScreenEvent.OnFilmCardPress(
                                filmBrief = it
                            )
                        )
                    }
                )
            }

            if (screenState.error != null) {
                KinopoiskErrorMessage(errorType = screenState.error) {
                    eventHandler(PopularViewModel.PopularScreenEvent.OnRetryButtonClick)
                }
            }

            if (screenState.shouldShowDialog) {
                screenState.selectedFilm?.let { selectedFilm ->
                    KinopoiskConfirmDialog(
                        title = "Добавить \"${selectedFilm.nameRu}\" в избранное?",
                        onDismissRequest = { eventHandler(PopularViewModel.PopularScreenEvent.OnDialogDismissRequest) },
                        onConfirmButtonClick = {
                            eventHandler(
                                PopularViewModel.PopularScreenEvent.OnConfirmDialog(
                                    selectedFilm
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun TopFilmsList(
        contentPadding: PaddingValues,
        films: PersistentList<FilmBrief>,
        onClick: (Int) -> Unit,
        onPress: (FilmBrief) -> Unit
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(space = 12.dp),
            contentPadding = PaddingValues(
                top = 16.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 56.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(MoviesAppTheme.color.background)
        ) {
            items(
                items = films,
                key = { it.filmId },
                contentType = { "Top Films" }
            ) { filmBrief ->
                KinopoiskFilmCard(
                    filmBrief = filmBrief,
                    onClick = { onClick(filmBrief.filmId) }
                ) {
                    when (filmBrief.isFavorite) {
                        true -> Unit
                        false -> onPress(filmBrief)
                    }
                }
            }
        }
    }

    @Composable
    private fun PopularScreenActions(
        screenAction: PopularViewModel.PopularScreenAction?,
        snackbarHostState: SnackbarHostState
    ) {
        val coroutineScope = rememberCoroutineScope()
        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(screenAction) {
            when (screenAction) {
                null -> Unit

                is PopularViewModel.PopularScreenAction.NavigateDetails ->
                    navigator.push(
                        item = ScreenRegistry.get(
                            provider = SharedScreen.DetailsScreen(id = screenAction.filmId)
                        )
                    )


                PopularViewModel.PopularScreenAction.NavigateSearchScreen ->
                    navigator.push(
                        item = ScreenRegistry.get(
                            provider = SharedScreen.SearchScreen
                        )
                    )

                is PopularViewModel.PopularScreenAction.ShowSnackbar -> coroutineScope.launch {
                    snackbarHostState.showSnackbar(
                        message = screenAction.message,
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }

}
