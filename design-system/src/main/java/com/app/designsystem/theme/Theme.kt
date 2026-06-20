package com.app.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Typography

/**
 * Design-system theme wrapper. Light mode only in this phase.
 *
 * Apply once at the app NavHost root.
 */
@Composable
fun Theme(
    content: @Composable () -> Unit,
) {
    val colorScheme = lightColorScheme(
        primary = Colors.Primary,
        onPrimary = Colors.OnPrimary,
        background = Colors.Canvas,
        onBackground = Colors.InkDeep,
        surface = Colors.Canvas,
        onSurface = Colors.InkDeep,
        error = Colors.Critical,
        onError = Colors.OnPrimary,
    )

    CompositionLocalProvider(
        LocalTypography provides TypographyDefaults,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            content = content,
        )
    }
}

data class TypographyTokens(
    val heroDisplay: androidx.compose.ui.text.TextStyle = Typography.heroDisplay,
    val displayLg: androidx.compose.ui.text.TextStyle = Typography.displayLg,
    val headingLg: androidx.compose.ui.text.TextStyle = Typography.headingLg,
    val headingMd: androidx.compose.ui.text.TextStyle = Typography.headingMd,
    val headingSm: androidx.compose.ui.text.TextStyle = Typography.headingSm,
    val subtitleLg: androidx.compose.ui.text.TextStyle = Typography.subtitleLg,
    val subtitleMd: androidx.compose.ui.text.TextStyle = Typography.subtitleMd,
    val bodyMdBold: androidx.compose.ui.text.TextStyle = Typography.bodyMdBold,
    val bodyMd: androidx.compose.ui.text.TextStyle = Typography.bodyMd,
    val bodySmBold: androidx.compose.ui.text.TextStyle = Typography.bodySmBold,
    val bodySm: androidx.compose.ui.text.TextStyle = Typography.bodySm,
    val captionBold: androidx.compose.ui.text.TextStyle = Typography.captionBold,
    val caption: androidx.compose.ui.text.TextStyle = Typography.caption,
    val buttonMd: androidx.compose.ui.text.TextStyle = Typography.buttonMd,
    val linkMd: androidx.compose.ui.text.TextStyle = Typography.linkMd,
)

val TypographyDefaults = TypographyTokens()

val LocalTypography = staticCompositionLocalOf { TypographyDefaults }

object Theme {
    val typography: TypographyTokens
        @Composable
        get() = LocalTypography.current
}
