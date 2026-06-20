package com.app.newsdigest.presentation.articlelist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import com.app.designsystem.foundation.Colors

enum class ArticleThumbnailSize {
    Compact,
    Hero,
}

@Composable
fun ArticleThumbnail(
    imageUrl: String?,
    size: ArticleThumbnailSize,
    modifier: Modifier = Modifier,
    isGrayscale: Boolean = false,
    contentDescription: String? = null,
) {
    val sizeModifier = when (size) {
        ArticleThumbnailSize.Compact -> Modifier.fillMaxSize()
        ArticleThumbnailSize.Hero -> Modifier
            .fillMaxWidth()
            .aspectRatio(4f / 3f)
    }

    Box(
        modifier = modifier
            .then(sizeModifier)
            .background(Colors.SurfaceSoft),
    ) {
        if (!imageUrl.isNullOrBlank()) {
            AsyncImage(
                model = imageUrl,
                contentDescription = contentDescription,
                modifier = Modifier
                    .fillMaxSize()
                    .then(if (isGrayscale) Modifier.alpha(0.9f) else Modifier),
                contentScale = ContentScale.Crop,
                colorFilter = if (isGrayscale) offlineGrayscaleFilter else null,
            )
        }
    }
}

private val offlineGrayscaleFilter = ColorFilter.colorMatrix(
    ColorMatrix().apply { setToSaturation(0f) },
)
