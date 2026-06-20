package com.app.designsystem.foundation

import androidx.compose.ui.unit.TextUnitType
import org.junit.Assert.assertEquals
import org.junit.Test

class TypographyTest {

    @Test
    fun heroDisplay_matchesDesignMeta() {
        assertTypography(
            style = Typography.heroDisplay,
            fontSizeSp = 64f,
            fontWeight = 500,
            lineHeightSp = 74.24f,
        )
    }

    @Test
    fun displayLg_matchesDesignMeta() {
        assertTypography(
            style = Typography.displayLg,
            fontSizeSp = 48f,
            fontWeight = 500,
            lineHeightSp = 56.16f,
        )
    }

    @Test
    fun headingLg_matchesDesignMeta() {
        assertTypography(
            style = Typography.headingLg,
            fontSizeSp = 36f,
            fontWeight = 500,
            lineHeightSp = 46.08f,
        )
    }

    @Test
    fun headingMd_matchesDesignMeta() {
        assertTypography(
            style = Typography.headingMd,
            fontSizeSp = 28f,
            fontWeight = 300,
            lineHeightSp = 33.88f,
        )
    }

    @Test
    fun headingSm_matchesDesignMeta() {
        assertTypography(
            style = Typography.headingSm,
            fontSizeSp = 24f,
            fontWeight = 500,
            lineHeightSp = 30f,
        )
    }

    @Test
    fun subtitleLg_matchesDesignMeta() {
        assertTypography(
            style = Typography.subtitleLg,
            fontSizeSp = 18f,
            fontWeight = 700,
            lineHeightSp = 25.92f,
        )
    }

    @Test
    fun subtitleMd_matchesDesignMeta() {
        assertTypography(
            style = Typography.subtitleMd,
            fontSizeSp = 18f,
            fontWeight = 400,
            lineHeightSp = 25.92f,
        )
    }

    @Test
    fun bodyMdBold_matchesDesignMeta() {
        assertTypography(
            style = Typography.bodyMdBold,
            fontSizeSp = 16f,
            fontWeight = 700,
            lineHeightSp = 24f,
            letterSpacingEm = -0.01f,
        )
    }

    @Test
    fun bodyMd_matchesDesignMeta() {
        assertTypography(
            style = Typography.bodyMd,
            fontSizeSp = 16f,
            fontWeight = 400,
            lineHeightSp = 24f,
            letterSpacingEm = -0.01f,
        )
    }

    @Test
    fun bodySmBold_matchesDesignMeta() {
        assertTypography(
            style = Typography.bodySmBold,
            fontSizeSp = 14f,
            fontWeight = 700,
            lineHeightSp = 20.02f,
            letterSpacingEm = -0.01f,
        )
    }

    @Test
    fun bodySm_matchesDesignMeta() {
        assertTypography(
            style = Typography.bodySm,
            fontSizeSp = 14f,
            fontWeight = 400,
            lineHeightSp = 20.02f,
            letterSpacingEm = -0.01f,
        )
    }

    @Test
    fun captionBold_matchesDesignMeta() {
        assertTypography(
            style = Typography.captionBold,
            fontSizeSp = 12f,
            fontWeight = 700,
            lineHeightSp = 15.96f,
        )
    }

    @Test
    fun caption_matchesDesignMeta() {
        assertTypography(
            style = Typography.caption,
            fontSizeSp = 12f,
            fontWeight = 400,
            lineHeightSp = 15.96f,
        )
    }

    @Test
    fun buttonMd_matchesDesignMeta() {
        assertTypography(
            style = Typography.buttonMd,
            fontSizeSp = 14f,
            fontWeight = 700,
            lineHeightSp = 20.02f,
            letterSpacingEm = -0.01f,
        )
    }

    @Test
    fun linkMd_matchesDesignMeta() {
        assertTypography(
            style = Typography.linkMd,
            fontSizeSp = 16f,
            fontWeight = 700,
            lineHeightSp = 24f,
            letterSpacingEm = -0.01f,
        )
    }

    private fun assertTypography(
        style: androidx.compose.ui.text.TextStyle,
        fontSizeSp: Float,
        fontWeight: Int,
        lineHeightSp: Float,
        letterSpacingEm: Float? = null,
    ) {
        assertEquals(fontSizeSp, style.fontSize.value, 0.01f)
        assertEquals(fontWeight, style.fontWeight?.weight)
        assertEquals(lineHeightSp, style.lineHeight.value, 0.01f)
        if (letterSpacingEm == null) {
            assertEquals(TextUnitType.Unspecified, style.letterSpacing.type)
        } else {
            assertEquals(TextUnitType.Em, style.letterSpacing.type)
            assertEquals(letterSpacingEm, style.letterSpacing.value, 0.001f)
        }
    }
}
