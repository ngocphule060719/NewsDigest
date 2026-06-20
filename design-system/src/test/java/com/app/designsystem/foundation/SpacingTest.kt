package com.app.designsystem.foundation

import org.junit.Assert.assertEquals
import org.junit.Test

class SpacingTest {

    @Test
    fun spacingTokens_matchDesignMeta() {
        assertDp(4f, Spacing.Xxs)
        assertDp(8f, Spacing.Xs)
        assertDp(10f, Spacing.Sm)
        assertDp(12f, Spacing.Md)
        assertDp(16f, Spacing.Base)
        assertDp(20f, Spacing.Lg)
        assertDp(24f, Spacing.Xl)
        assertDp(32f, Spacing.Xxl)
        assertDp(40f, Spacing.Xxxl)
        assertDp(48f, Spacing.SectionSm)
        assertDp(64f, Spacing.Section)
        assertDp(80f, Spacing.SectionLg)
        assertDp(120f, Spacing.Hero)
    }

    private fun assertDp(expected: Float, actual: androidx.compose.ui.unit.Dp) {
        assertEquals(expected, actual.value, 0f)
    }
}
