package ru.kpfu.itis.core.widget

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ru.kpfu.itis.core.designsystem.MoviesAppTheme

@Composable
fun KinopoiskConfirmDialog(
    title: String,
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            KinopoiskTextButton(
                text = stringResource(id = R.string.yes),
                onClick = onConfirmButtonClick
            )
        },
        dismissButton = {
            KinopoiskTextButton(
                text = stringResource(id = R.string.no),
                onClick = onDismissRequest
            )
        },
        title = {
            Text(
                text = title,
                style = MoviesAppTheme.type.cardTitle,
                color = MoviesAppTheme.color.primaryText,
                textAlign = TextAlign.Center
            )
        },
        containerColor = MoviesAppTheme.color.background
    )
}
