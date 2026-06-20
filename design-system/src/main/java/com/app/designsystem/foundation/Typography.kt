package com.app.designsystem.foundation

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.app.designsystem.R

/**
 * Design-system typography tokens from `sample/DESIGN-meta.md`.
 *
 * Source spec uses **Optimistic VF**; this module implements the documented fallback stack:
 * Montserrat → Helvetica → Arial → Noto Sans via [DesignFontFamily].
 *
 * Heading roles in the source enable OpenType `ss01` and `ss02`; those features are not applied
 * here because they are unavailable on Montserrat and system fallbacks.
 */
object Typography {
    val heroDisplay: TextStyle = designTextStyle(
        fontSizeSp = 64,
        fontWeight = FontWeight.Medium,
        lineHeightRatio = 1.16f,
    )

    val displayLg: TextStyle = designTextStyle(
        fontSizeSp = 48,
        fontWeight = FontWeight.Medium,
        lineHeightRatio = 1.17f,
    )

    val headingLg: TextStyle = designTextStyle(
        fontSizeSp = 36,
        fontWeight = FontWeight.Medium,
        lineHeightRatio = 1.28f,
    )

    val headingMd: TextStyle = designTextStyle(
        fontSizeSp = 28,
        fontWeight = FontWeight.Light,
        lineHeightRatio = 1.21f,
    )

    val headingSm: TextStyle = designTextStyle(
        fontSizeSp = 24,
        fontWeight = FontWeight.Medium,
        lineHeightRatio = 1.25f,
    )

    val subtitleLg: TextStyle = designTextStyle(
        fontSizeSp = 18,
        fontWeight = FontWeight.Bold,
        lineHeightRatio = 1.44f,
    )

    val subtitleMd: TextStyle = designTextStyle(
        fontSizeSp = 18,
        fontWeight = FontWeight.Normal,
        lineHeightRatio = 1.44f,
    )

    val bodyMdBold: TextStyle = designTextStyle(
        fontSizeSp = 16,
        fontWeight = FontWeight.Bold,
        lineHeightRatio = 1.50f,
        letterSpacingPx = -0.16f,
    )

    val bodyMd: TextStyle = designTextStyle(
        fontSizeSp = 16,
        fontWeight = FontWeight.Normal,
        lineHeightRatio = 1.50f,
        letterSpacingPx = -0.16f,
    )

    val bodySmBold: TextStyle = designTextStyle(
        fontSizeSp = 14,
        fontWeight = FontWeight.Bold,
        lineHeightRatio = 1.43f,
        letterSpacingPx = -0.14f,
    )

    val bodySm: TextStyle = designTextStyle(
        fontSizeSp = 14,
        fontWeight = FontWeight.Normal,
        lineHeightRatio = 1.43f,
        letterSpacingPx = -0.14f,
    )

    val captionBold: TextStyle = designTextStyle(
        fontSizeSp = 12,
        fontWeight = FontWeight.Bold,
        lineHeightRatio = 1.33f,
    )

    val caption: TextStyle = designTextStyle(
        fontSizeSp = 12,
        fontWeight = FontWeight.Normal,
        lineHeightRatio = 1.33f,
    )

    val buttonMd: TextStyle = designTextStyle(
        fontSizeSp = 14,
        fontWeight = FontWeight.Bold,
        lineHeightRatio = 1.43f,
        letterSpacingPx = -0.14f,
    )

    val linkMd: TextStyle = designTextStyle(
        fontSizeSp = 16,
        fontWeight = FontWeight.Bold,
        lineHeightRatio = 1.50f,
        letterSpacingPx = -0.16f,
    )
}

/**
 * Font stack for design-system typography.
 *
 * Primary: bundled Montserrat variable font ([R.font.montserrat]).
 * Platform sans-serif ([FontFamily.SansSerif]) covers the design-spec fallbacks
 * Helvetica → Arial → Noto Sans when Montserrat lacks a glyph.
 */
val DesignFontFamily: FontFamily = FontFamily(
    Font(R.font.montserrat, FontWeight.Light),
    Font(R.font.montserrat, FontWeight.Normal),
    Font(R.font.montserrat, FontWeight.Medium),
    Font(R.font.montserrat, FontWeight.Bold),
)

/** System sans-serif fallback chain used alongside [DesignFontFamily] in theme surfaces. */
val DesignFontFamilyFallback: FontFamily = FontFamily.SansSerif

private fun designTextStyle(
    fontSizeSp: Int,
    fontWeight: FontWeight,
    lineHeightRatio: Float,
    letterSpacingPx: Float? = null,
): TextStyle {
    return TextStyle(
        fontFamily = DesignFontFamily,
        fontSize = fontSizeSp.sp,
        fontWeight = fontWeight,
        lineHeight = lineHeight(fontSizeSp, lineHeightRatio),
        letterSpacing = letterSpacingPx?.let { letterSpacingEm(it, fontSizeSp.toFloat()) }
            ?: TextUnit.Unspecified,
    )
}

private fun lineHeight(fontSizeSp: Int, ratio: Float): TextUnit = (fontSizeSp * ratio).sp

private fun letterSpacingEm(letterSpacingPx: Float, fontSizePx: Float): TextUnit =
    (letterSpacingPx / fontSizePx).em
