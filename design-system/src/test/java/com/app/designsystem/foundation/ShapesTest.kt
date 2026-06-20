package com.app.designsystem.foundation

import org.junit.Assert.assertEquals
import org.junit.Test

class ShapesTest {

    @Test
    fun shapeTokens_matchDesignMeta() {
        assertDp(2f, Shapes.Xs)
        assertDp(4f, Shapes.Sm)
        assertDp(6f, Shapes.Md)
        assertDp(8f, Shapes.Lg)
        assertDp(16f, Shapes.Xl)
        assertDp(24f, Shapes.Xxl)
        assertDp(32f, Shapes.Xxxl)
        assertDp(40f, Shapes.Feature)
        assertDp(100f, Shapes.Full)
        assertDp(9999f, Shapes.CircleRadius)
    }

    private fun assertDp(expected: Float, actual: androidx.compose.ui.unit.Dp) {
        assertEquals(expected, actual.value, 0f)
    }
}
