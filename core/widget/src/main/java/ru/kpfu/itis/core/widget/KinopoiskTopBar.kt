package com.example.kinopoisk.core.widget

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.kpfu.itis.core.designsystem.icon.KinopoiskIcons
import com.example.kinopoisk.core.designsystem.theme.KinopoiskTheme
import ru.kpfu.itis.core.widget.KinopoiskIconButton

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
            style = KinopoiskTheme.kinopoiskTypography.screenHeading,
            color = KinopoiskTheme.kinopoiskColor.primaryText
        )
    },
    scrollBehavior = scrollBehavior,
    actions = {
        KinopoiskIconButton(
            icon = KinopoiskIcons.Search,
            action = onClick
        )
    },
    colors = TopAppBarDefaults.topAppBarColors(
        containerColor = KinopoiskTheme.kinopoiskColor.background,
        scrolledContainerColor = KinopoiskTheme.kinopoiskColor.background
    )
)
