package ru.kpfu.itis.core.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.kpfu.itis.core.designsystem.MoviesAppTheme

@Composable
fun KinopoiskProgressBar(
    modifier: Modifier = Modifier,
    shouldShow: Boolean
) {
    AnimatedVisibility(visible = shouldShow) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MoviesAppTheme.color.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = MoviesAppTheme.color.primary)
        }
    }
}
