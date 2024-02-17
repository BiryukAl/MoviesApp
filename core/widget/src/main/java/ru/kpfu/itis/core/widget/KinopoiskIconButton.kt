package ru.kpfu.itis.core.widget

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import ru.kpfu.itis.core.designsystem.MoviesAppTheme

@Composable
fun KinopoiskIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    action: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = action
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MoviesAppTheme.color.primary
        )
    }
}
