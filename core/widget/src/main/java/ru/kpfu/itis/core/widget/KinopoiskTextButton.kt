package ru.kpfu.itis.core.widget

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.kpfu.itis.core.designsystem.MoviesAppTheme

@Composable
fun KinopoiskTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MoviesAppTheme.color.background,
            contentColor = MoviesAppTheme.color.primary
        )
    ) {
        Text(
            text = text,
            style = MoviesAppTheme.type.buttonText
        )
    }
}
