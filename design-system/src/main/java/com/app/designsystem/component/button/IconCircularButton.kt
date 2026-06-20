package com.app.designsystem.component.button

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.theme.Theme

/**
 * Circular utility button for carousel arrows, share, favorite, etc.
 *
 * Meta source: `button-icon-circular`.
 */
@Composable
fun IconCircularButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 40.dp,
    iconTint: Color? = null,
) {
    val resolvedTint = iconTint ?: Colors.Ink

    PillSurfaceButton(
        onClick = onClick,
        modifier = modifier.size(size),
        enabled = enabled,
        shape = CircleShape,
        defaultVisuals = ButtonVisuals(
            containerColor = Colors.Canvas,
            contentColor = resolvedTint,
            borderColor = Colors.HairlineSoft,
            borderWidth = 1.dp,
        ),
        pressedVisuals = ButtonVisuals(
            containerColor = Colors.SurfaceSoft,
            contentColor = resolvedTint,
            borderColor = Colors.Hairline,
            borderWidth = 1.dp,
        ),
        disabledVisuals = ButtonVisuals(
            containerColor = Colors.Canvas,
            contentColor = Colors.DisabledText,
            borderColor = Colors.HairlineSoft,
            borderWidth = 1.dp,
        ),
        focusedVisuals = ButtonVisuals(
            containerColor = Colors.Canvas,
            contentColor = resolvedTint,
            borderColor = Colors.FbBlue,
            borderWidth = 2.dp,
        ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(20.dp),
            tint = resolvedTint,
        )
    }
}

@Preview(name = "IconCircularButton — default")
@Composable
private fun IconCircularButtonDefaultPreview() {
    Theme {
        IconCircularButton(
            icon = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Next",
            onClick = {},
        )
    }
}

@Preview(name = "IconCircularButton — disabled")
@Composable
private fun IconCircularButtonDisabledPreview() {
    Theme {
        IconCircularButton(
            icon = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Next",
            onClick = {},
            enabled = false,
        )
    }
}
