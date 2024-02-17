package ru.kpfu.itis.core.widget

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun KinopoiskImage(
    modifier: Modifier = Modifier,
    imageUrl: String
) {
    val showShimmer = remember { mutableStateOf(true) }

    AsyncImage(
        modifier = modifier
            .background(
                shimmerEffect(
                    targetValue = 1300f,
                    showShimmer = showShimmer.value
                )
            ),
        contentScale = ContentScale.Crop,
        contentDescription = null,
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build(),
        onSuccess = { showShimmer.value = false }
    )
}
