package com.app.designsystem.foundation

import androidx.compose.ui.graphics.Color

/**
 * Color tokens from `DESIGN-meta.md` YAML frontmatter (`colors.*`).
 */
object Colors {
    // Brand & accent
    val Primary = Color(0xFF0064E0)
    val PrimaryDeep = Color(0xFF0457CB)
    val PrimarySoft = Color(0xFF0091FF)
    val OnPrimary = Color(0xFFFFFFFF)
    val InkButton = Color(0xFF000000)
    val OnInkButton = Color(0xFFFFFFFF)
    val FbBlue = Color(0xFF1876F2)
    val MetaLink = Color(0xFF385898)
    val OculusPurple = Color(0xFFA121CE)

    // Semantic
    val Success = Color(0xFF31A24C)
    val SuccessBg = Color(0xFF24E400)
    val Attention = Color(0xFFF2A918)
    val Warning = Color(0xFFF7B928)
    val WarningBg = Color(0xFFFFE200)
    val Critical = Color(0xFFE41E3F)
    val CriticalStrong = Color(0xFFF0284A)

    // Surface
    val Canvas = Color(0xFFFFFFFF)
    val SurfaceSoft = Color(0xFFF1F4F7)
    val Hairline = Color(0xFFCED0D4)
    val HairlineSoft = Color(0xFFDEE3E9)

    // Text
    val InkDeep = Color(0xFF0A1317)
    val Ink = Color(0xFF1C1E21)
    val Charcoal = Color(0xFF444950)
    val Slate = Color(0xFF4B4C4F)
    val Steel = Color(0xFF5D6C7B)
    val Stone = Color(0xFF8595A4)
    val DisabledText = Color(0xFFBCC0C4)

    /** `radio-option-selected` border — not listed under YAML `colors:` */
    val RadioSelectedBorder = Color(0xFF0143B5)

    /** `button-ghost` / `radio-option` border — rgba(10, 19, 23, 0.12) */
    val InkDeep12 = Color(0x1F0A1317)
}
