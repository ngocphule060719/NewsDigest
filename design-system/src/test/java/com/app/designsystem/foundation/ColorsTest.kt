package com.app.designsystem.foundation

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.junit.Assert.assertEquals
import org.junit.Test

class ColorsTest {

    @Test
    fun brandAndAccentColors_matchDesignMeta() {
        assertHex(0xFF0064E0.toInt(), Colors.Primary)
        assertHex(0xFF0457CB.toInt(), Colors.PrimaryDeep)
        assertHex(0xFF0091FF.toInt(), Colors.PrimarySoft)
        assertHex(0xFFFFFFFF.toInt(), Colors.OnPrimary)
        assertHex(0xFF000000.toInt(), Colors.InkButton)
        assertHex(0xFFFFFFFF.toInt(), Colors.OnInkButton)
        assertHex(0xFF1876F2.toInt(), Colors.FbBlue)
        assertHex(0xFF385898.toInt(), Colors.MetaLink)
        assertHex(0xFFA121CE.toInt(), Colors.OculusPurple)
    }

    @Test
    fun semanticColors_matchDesignMeta() {
        assertHex(0xFF31A24C.toInt(), Colors.Success)
        assertHex(0xFF24E400.toInt(), Colors.SuccessBg)
        assertHex(0xFFF2A918.toInt(), Colors.Attention)
        assertHex(0xFFF7B928.toInt(), Colors.Warning)
        assertHex(0xFFFFE200.toInt(), Colors.WarningBg)
        assertHex(0xFFE41E3F.toInt(), Colors.Critical)
        assertHex(0xFFF0284A.toInt(), Colors.CriticalStrong)
    }

    @Test
    fun surfaceColors_matchDesignMeta() {
        assertHex(0xFFFFFFFF.toInt(), Colors.Canvas)
        assertHex(0xFFF1F4F7.toInt(), Colors.SurfaceSoft)
        assertHex(0xFFCED0D4.toInt(), Colors.Hairline)
        assertHex(0xFFDEE3E9.toInt(), Colors.HairlineSoft)
    }

    @Test
    fun textColors_matchDesignMeta() {
        assertHex(0xFF0A1317.toInt(), Colors.InkDeep)
        assertHex(0xFF1C1E21.toInt(), Colors.Ink)
        assertHex(0xFF444950.toInt(), Colors.Charcoal)
        assertHex(0xFF4B4C4F.toInt(), Colors.Slate)
        assertHex(0xFF5D6C7B.toInt(), Colors.Steel)
        assertHex(0xFF8595A4.toInt(), Colors.Stone)
        assertHex(0xFFBCC0C4.toInt(), Colors.DisabledText)
    }

    private fun assertHex(expectedArgb: Int, actual: Color) {
        assertEquals(expectedArgb, actual.toArgb())
    }
}
