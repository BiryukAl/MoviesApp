package ru.kpfu.itis.core.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.core.designsystem.MoviesAppTheme
import ru.kpfu.itis.core.widget.model.ErrorType

@Composable
fun KinopoiskErrorMessage(
    errorType: ErrorType,
    onClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (errorType != ErrorType.NO_DATA_IN_DB) {
                    Image(
                        painter = painterResource(id = R.drawable.error),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                }
                Text(
                    text = when (errorType) {
                        ErrorType.UNKNOWN_HOST_EXCEPTION -> stringResource(id = R.string.no_internet_connection_error)
                        ErrorType.OTHER -> stringResource(id = R.string.other_error)
                        ErrorType.NO_DATA_IN_DB -> stringResource(id = R.string.no_data_in_db)
                    },
                    style = MoviesAppTheme.type.errorText,
                    color = MoviesAppTheme.color.primary,
                    textAlign = TextAlign.Center
                )
            }
            if (errorType != ErrorType.NO_DATA_IN_DB) {
                KinopoiskButton(
                    text = stringResource(id = R.string.retry),
                    containerColor = MoviesAppTheme.color.primary,
                    contentColor = MoviesAppTheme.color.onPrimary,
                    onClick = onClick
                )
            }
        }
    }
}
