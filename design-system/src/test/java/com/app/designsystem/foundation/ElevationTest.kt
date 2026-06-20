package com.app.designsystem.foundation

import org.junit.Assert.assertEquals
import org.junit.Test

class ElevationTest {

    @Test
    fun elevationLevels_matchDesignMeta() {
        assertDp(0f, Elevation.Level0)
        assertDp(1f, Elevation.Level1)
        assertDp(4f, Elevation.Level2)
    }

    private fun assertDp(expected: Float, actual: androidx.compose.ui.unit.Dp) {
        assertEquals(expected, actual.value, 0f)
    }
}
