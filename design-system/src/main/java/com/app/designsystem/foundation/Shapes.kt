package com.app.designsystem.foundation

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Border-radius tokens from `DESIGN-meta.md` (`rounded.*`).
 */
object Shapes {
    val Xs: Dp = 2.dp
    val Sm: Dp = 4.dp
    val Md: Dp = 6.dp
    val Lg: Dp = 8.dp
    val Xl: Dp = 16.dp
    val Xxl: Dp = 24.dp
    val Xxxl: Dp = 32.dp
    val Feature: Dp = 40.dp
    val Full: Dp = 100.dp

    /** Large radius used to approximate a circle in token tables. */
    val CircleRadius: Dp = 9999.dp

    fun rounded(radius: Dp): Shape = RoundedCornerShape(radius)

    val roundedXs: Shape get() = rounded(Xs)
    val roundedSm: Shape get() = rounded(Sm)
    val roundedMd: Shape get() = rounded(Md)
    val roundedLg: Shape get() = rounded(Lg)
    val roundedXl: Shape get() = rounded(Xl)
    val roundedXxl: Shape get() = rounded(Xxl)
    val roundedXxxl: Shape get() = rounded(Xxxl)
    val roundedFeature: Shape get() = rounded(Feature)
    val roundedFull: Shape get() = rounded(Full)
    val circle: Shape get() = CircleShape
}
