package com.app.designsystem.component.navigation

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.app.designsystem.foundation.Elevation

/** Shadow color for elevation level 2: `rgba(20, 22, 26, 0.3)`. */
internal val StickyPanelShadowColor = Color(0x4D14161A)

/**
 * Applies Meta elevation level 2 for sticky panels (`rgba(20, 22, 26, 0.3) 0 1 4 0`).
 *
 * Meta source: sticky top navigation and checkout rails per DESIGN-meta elevation table.
 */
internal fun Modifier.stickyPanelShadow(): Modifier = shadow(
    elevation = Elevation.Level2,
    shape = RectangleShape,
    ambientColor = StickyPanelShadowColor,
    spotColor = StickyPanelShadowColor,
    clip = false,
)
