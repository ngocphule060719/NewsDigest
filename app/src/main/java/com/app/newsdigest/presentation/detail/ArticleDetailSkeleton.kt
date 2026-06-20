package com.app.newsdigest.presentation.detail

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing

@Composable
fun ArticleDetailSkeleton(
    modifier: Modifier = Modifier,
) {
    val barShape = RoundedCornerShape(Shapes.Sm)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = Spacing.Xxl)
            .testTag("detail_skeleton"),
        verticalArrangement = Arrangement.spacedBy(Spacing.Base),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = Shapes.Xxxl,
                        topEnd = Shapes.Xxxl,
                    ),
                )
                .shimmerBackground(barShape),
        )

        Column(
            modifier = Modifier.padding(horizontal = Spacing.Base),
            verticalArrangement = Arrangement.spacedBy(Spacing.Sm),
        ) {
            ShimmerBar(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(14.dp),
                shape = barShape,
            )
            ShimmerBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp),
                shape = barShape,
            )
            ShimmerBar(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(28.dp),
                shape = barShape,
            )
            ShimmerBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
                    .padding(top = Spacing.Sm),
                shape = barShape,
            )
            ShimmerBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp),
                shape = barShape,
            )
            ShimmerBar(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(16.dp),
                shape = barShape,
            )
        }
    }
}

@Composable
internal fun ShimmerBar(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(Shapes.Sm),
) {
    Box(
        modifier = modifier
            .clip(shape)
            .shimmerBackground(shape),
    )
}

@Composable
internal fun Modifier.shimmerBackground(shape: Shape = RoundedCornerShape(Shapes.Sm)): Modifier {
    val transition = rememberInfiniteTransition(label = "articleDetailShimmer")
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

    return clip(shape).background(brush)
}
