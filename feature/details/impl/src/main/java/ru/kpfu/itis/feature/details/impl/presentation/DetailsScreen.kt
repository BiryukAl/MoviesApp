package ru.kpfu.itis.feature.details.impl.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.compose.getKoin
import ru.kpfu.itis.core.designsystem.MoviesAppTheme
import ru.kpfu.itis.core.designsystem.icon.MoviesAppIcons
import ru.kpfu.itis.core.widget.KinopoiskErrorMessage
import ru.kpfu.itis.core.widget.KinopoiskIconButton
import ru.kpfu.itis.core.widget.KinopoiskImage
import ru.kpfu.itis.core.widget.KinopoiskProgressBar
import ru.kpfu.itis.feature.details.api.FilmDetail
import ru.kpfu.itis.feature.details.impl.Navigation.FILM_ID_KOIN_PROPERTY
import ru.kpfu.itis.feature.details.impl.R

internal class DetailsScreen(val filmId: Int) : Screen {

    @Composable
    override fun Content() {
        getKoin().setProperty(FILM_ID_KOIN_PROPERTY, filmId)

        val viewModel = getScreenModel<DetailsViewModel>()
        val screenState by viewModel.screenState.collectAsStateWithLifecycle()
        val screenAction by viewModel.screenAction.collectAsStateWithLifecycle(initialValue = null)

        DetailsScreenContent(
            screenState = screenState,
            eventHandler = viewModel::eventHandler
        )

        DetailsScreenActions(
            screenAction = screenAction,
        )
    }
}


@Composable
private fun DetailsScreenContent(
    screenState: DetailsViewModel.DetailsScreenState,
    eventHandler: (DetailsViewModel.DetailsScreenEvent) -> Unit
) {
    Scaffold { contentPadding ->
        when (screenState.isLoading) {
            true -> KinopoiskProgressBar(shouldShow = true)
            false -> {
                FilmDetails(
                    contentPadding = contentPadding,
                    filmDetails = screenState.filmDetails,
                    onArrowBackClick = { eventHandler(DetailsViewModel.DetailsScreenEvent.OnArrowBackClick) }
                )
            }
        }

        if (screenState.error != null) {
            KinopoiskErrorMessage(errorType = screenState.error) {
                eventHandler(DetailsViewModel.DetailsScreenEvent.OnRetryButtonClick)
            }
        }
    }
}

@Composable
private fun FilmDetails(
    contentPadding: PaddingValues,
    filmDetails: FilmDetail?,
    onArrowBackClick: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            bottom = 56.dp
        ),
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
    ) {
        item {
            FilmPoster(
                imageUrl = filmDetails?.imageUrl,
                onArrowBackClick = onArrowBackClick
            )
        }
        item {
            FilmInformation(filmDetails = filmDetails)
        }
    }
}

@Composable
private fun FilmPoster(
    imageUrl: String?,
    onArrowBackClick: () -> Unit
) {
    Box {
        imageUrl?.let {
            KinopoiskImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(533.dp),
                imageUrl = imageUrl
            )
        }
        KinopoiskIconButton(
            modifier = Modifier.align(Alignment.TopStart),
            icon = MoviesAppIcons.ArrowBack,
            action = onArrowBackClick
        )
    }
}

@Composable
private fun FilmInformation(filmDetails: FilmDetail?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        filmDetails?.let {
            Text(
                text = it.name ?: "",
                style = MoviesAppTheme.type.filmTitle,
                color = MoviesAppTheme.color.primaryText
            )
            Text(
                text = it.description ?: "",
                style = MoviesAppTheme.type.filmDescriptionValue,
                color = MoviesAppTheme.color.secondaryText
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                FilmGenres(genres = it.genres?.joinToString(", ") ?: "")
                FilmCountries(countries = it.countries?.joinToString(", ") ?: "")
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilmGenres(genres: String) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = stringResource(id = R.string.genres),
            style = MoviesAppTheme.type.filmDescriptionKey,
            color = MoviesAppTheme.color.secondaryText,
        )
        Text(
            text = genres,
            style = MoviesAppTheme.type.filmDescriptionValue,
            color = MoviesAppTheme.color.secondaryText
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun FilmCountries(countries: String) {
    FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = stringResource(id = R.string.countries),
            style = MoviesAppTheme.type.filmDescriptionKey,
            color = MoviesAppTheme.color.secondaryText
        )
        Text(
            text = countries,
            style = MoviesAppTheme.type.filmDescriptionValue,
            color = MoviesAppTheme.color.secondaryText
        )
    }
}

@Composable
private fun DetailsScreenActions(
    screenAction: DetailsViewModel.DetailsScreenAction?,
) {
    val navigator = LocalNavigator.currentOrThrow
    LaunchedEffect(screenAction) {
        when (screenAction) {
            null -> Unit
            DetailsViewModel.DetailsScreenAction.NavigateUp -> navigator.pop()
        }
    }
}

