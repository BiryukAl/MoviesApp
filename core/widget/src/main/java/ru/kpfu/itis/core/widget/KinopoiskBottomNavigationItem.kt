package ru.kpfu.itis.core.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.core.designsystem.MoviesAppTheme

@Composable
fun KinopoiskBottomNavigationItem(
    modifier: Modifier = Modifier,
    text: String,
    containerColor: Color,
    contentColor: Color,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Icon(
            modifier = Modifier.padding(end = 10.dp),
            imageVector = icon,
            contentDescription = null,
            tint = when (isSelected) {
                true -> MoviesAppTheme.color.secondary
                false -> MoviesAppTheme.color.primary
            }
        )
        Text(
            text = text,
            style = MoviesAppTheme.type.buttonText
        )
    }
}
