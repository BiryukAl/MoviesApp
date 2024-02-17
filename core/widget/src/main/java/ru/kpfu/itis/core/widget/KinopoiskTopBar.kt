package ru.kpfu.itis.core.widget

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.kpfu.itis.core.designsystem.MoviesAppTheme
import ru.kpfu.itis.core.designsystem.icon.MoviesAppIcons


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KinopoiskTopBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    title: String,
    onClick: () -> Unit
) = TopAppBar(
    modifier = modifier,
    title = {
        Text(
            text = title,
            style =  MoviesAppTheme.type.screenHeading,
            color = MoviesAppTheme.color.primaryText
        )
    },
    scrollBehavior = scrollBehavior,
    actions = {
        KinopoiskIconButton(
            icon =  MoviesAppIcons.Search,
            action = onClick
        )
    },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor =  MoviesAppTheme.color.background,
        scrolledContainerColor = MoviesAppTheme.color.background
    )
)
