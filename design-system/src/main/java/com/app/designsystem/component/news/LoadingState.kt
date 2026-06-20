package com.app.designsystem.component.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

/**
 * Loading indicator for news screens.
 *
 * Variants:
 * - [LoadingStateVariant.FullScreen] — centered spinner for initial load.
 * - [LoadingStateVariant.Inline] — compact spinner for pull-to-refresh overlays.
 * - [LoadingStateVariant.ArticleCardSkeleton] — stacked [ArticleCard] skeleton placeholders.
 */
enum class LoadingStateVariant {
    FullScreen,
    Inline,
    ArticleCardSkeleton,
}

/**
 * @param variant Visual treatment for the loading state.
 * @param skeletonCount Number of skeleton cards when [LoadingStateVariant.ArticleCardSkeleton].
 * @param usePrimaryTint When true, spinner uses `primary` cobalt; otherwise `inkDeep`.
 */
@Composable
fun LoadingState(
    modifier: Modifier = Modifier,
    variant: LoadingStateVariant = LoadingStateVariant.FullScreen,
    skeletonCount: Int = 3,
    usePrimaryTint: Boolean = true,
) {
    when (variant) {
        LoadingStateVariant.FullScreen -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                LoadingSpinner(usePrimaryTint = usePrimaryTint)
            }
        }

        LoadingStateVariant.Inline -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = Spacing.Xl),
                contentAlignment = Alignment.Center,
            ) {
                LoadingSpinner(
                    usePrimaryTint = usePrimaryTint,
                    size = 32.dp,
                    strokeWidth = 3.dp,
                )
            }
        }

        LoadingStateVariant.ArticleCardSkeleton -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.Base, vertical = Spacing.Xs),
                verticalArrangement = Arrangement.spacedBy(Spacing.Base),
            ) {
                repeat(skeletonCount.coerceAtLeast(1)) {
                    ArticleCard(
                        title = "",
                        metadata = "",
                        isLoading = true,
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingSpinner(
    usePrimaryTint: Boolean,
    modifier: Modifier = Modifier,
    size: androidx.compose.ui.unit.Dp = 48.dp,
    strokeWidth: androidx.compose.ui.unit.Dp = 4.dp,
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        color = if (usePrimaryTint) Colors.Primary else Colors.InkDeep,
        strokeWidth = strokeWidth,
    )
}

@Preview(showBackground = true)
@Composable
private fun LoadingStateFullScreenPreview() {
    Theme {
        LoadingState(variant = LoadingStateVariant.FullScreen)
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingStateInlinePreview() {
    Theme {
        LoadingState(variant = LoadingStateVariant.Inline)
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingStateSkeletonPreview() {
    Theme {
        LoadingState(
            variant = LoadingStateVariant.ArticleCardSkeleton,
            skeletonCount = 2,
        )
    }
}
