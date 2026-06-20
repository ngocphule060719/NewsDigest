package com.app.newsdigest.presentation.articlelist

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing

@Composable
fun ArticleListHeroSkeleton(
    modifier: Modifier = Modifier,
    skeletonCount: Int = 3,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.Base)
            .testTag("hero_skeleton"),
        verticalArrangement = Arrangement.spacedBy(Spacing.Xxl),
    ) {
        repeat(skeletonCount.coerceAtLeast(1)) {
            HeroCardSkeletonItem()
        }
    }
}

@Composable
private fun HeroCardSkeletonItem(
    modifier: Modifier = Modifier,
) {
    val cardShape = RoundedCornerShape(Shapes.Xxxl)
    val barShape = RoundedCornerShape(Shapes.Sm)

    Column(
        modifier = modifier
            .testTag("hero_skeleton")
            .fillMaxWidth()
            .clip(cardShape)
            .border(width = 1.dp, color = Colors.HairlineSoft, shape = cardShape)
            .background(Colors.Canvas),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .shimmerBackground(),
        )

        Column(
            modifier = Modifier.padding(Spacing.Xxl),
            verticalArrangement = Arrangement.spacedBy(Spacing.Base),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.Sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ShimmerBar(
                    modifier = Modifier.size(width = 64.dp, height = 16.dp),
                    shape = barShape
                )
                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(Colors.HairlineSoft),
                )
                ShimmerBar(
                    modifier = Modifier.size(width = 96.dp, height = 16.dp),
                    shape = barShape
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(Spacing.Sm)) {
                ShimmerBar(modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp), shape = barShape)
                ShimmerBar(modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .height(32.dp), shape = barShape)
            }

            Column(
                modifier = Modifier.padding(top = Spacing.Sm),
                verticalArrangement = Arrangement.spacedBy(Spacing.Xs),
            ) {
                ShimmerBar(modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp), shape = barShape)
                ShimmerBar(modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp), shape = barShape)
                ShimmerBar(modifier = Modifier
                    .fillMaxWidth(0.66f)
                    .height(16.dp), shape = barShape)
            }

            HorizontalDivider(
                modifier = Modifier.padding(top = Spacing.Md),
                color = Colors.HairlineSoft,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Spacing.Base),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ShimmerBar(
                    modifier = Modifier.size(width = 128.dp, height = 24.dp),
                    shape = barShape
                )
                ShimmerBar(modifier = Modifier.size(32.dp), shape = CircleShape)
            }
        }
    }
}

@Composable
private fun ShimmerBar(
    modifier: Modifier = Modifier,
    shape: androidx.compose.ui.graphics.Shape = RoundedCornerShape(Shapes.Sm),
) {
    Box(
        modifier = modifier
            .clip(shape)
            .shimmerBackground(),
    )
}

@Composable
private fun Modifier.shimmerBackground(): Modifier {
    val transition = rememberInfiniteTransition(label = "articleListShimmer")
    val translate by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmerTranslate",
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Colors.SurfaceSoft,
            Colors.HairlineSoft,
            Colors.SurfaceSoft,
        ),
        start = Offset(translate - 500f, 0f),
        end = Offset(translate, 0f),
    )

    return background(brush)
}
