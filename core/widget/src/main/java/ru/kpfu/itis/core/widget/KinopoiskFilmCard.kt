package ru.kpfu.itis.core.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.kpfu.itis.core.designsystem.MoviesAppTheme
import ru.kpfu.itis.core.designsystem.icon.MoviesAppIcons
import ru.kpfu.itis.core.widget.model.FilmBrief

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun KinopoiskFilmCard(
    modifier: Modifier = Modifier,
    filmBrief: FilmBrief,
    onClick: () -> Unit,
    onPress: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(size = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MoviesAppTheme.color.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .combinedClickable(
                    onClick = onClick,
                    onLongClick = onPress
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            KinopoiskImage(
                modifier = Modifier
                    .width(40.dp)
                    .height(63.dp)
                    .clip(RoundedCornerShape(5.dp)),
                imageUrl = filmBrief.posterUrlPreview
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                KinopoiskCardTitle(
                    text = filmBrief.nameRu,
                    isFavorite = filmBrief.isFavorite
                )
                KinopoiskCardSupportingText(
                    genre = filmBrief.genre,
                    year = filmBrief.year
                )
            }
        }
    }
}

@Composable
private fun KinopoiskCardTitle(
    text: String,
    isFavorite: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MoviesAppTheme.type.cardTitle,
            color = MoviesAppTheme.color.primaryText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (isFavorite) {
            Icon(
                imageVector = MoviesAppIcons.Star ,
                contentDescription = null,
                tint = MoviesAppTheme.color.primary,
                modifier = Modifier.align(Alignment.Top)
            )
        }
    }
}

@Composable
private fun KinopoiskCardSupportingText(
    genre: String,
    year: String
) {
    Text(
        text = "$genre ($year)",
        style = MoviesAppTheme.type.cardSupportingText,
        color = MoviesAppTheme.color.secondaryText,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}
