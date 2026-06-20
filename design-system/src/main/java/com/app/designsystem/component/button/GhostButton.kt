package com.app.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.theme.Theme

/**
 * Quieter outlined tertiary CTA.
 *
 * Meta source: `button-ghost`.
 */
@Composable
fun GhostButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    PillTextButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = PaddingValues(horizontal = 22.dp, vertical = 10.dp),
        textStyle = Theme.typography.buttonMd,
        defaultVisuals = ButtonVisuals(
            containerColor = Colors.Canvas.copy(alpha = 0f),
            contentColor = Colors.InkDeep,
            borderColor = Colors.InkDeep12,
            borderWidth = 2.dp,
        ),
        pressedVisuals = ButtonVisuals(
            containerColor = Colors.SurfaceSoft,
            contentColor = Colors.InkDeep,
            borderColor = Colors.InkDeep12,
            borderWidth = 2.dp,
        ),
        disabledVisuals = ButtonVisuals(
            containerColor = Colors.Canvas.copy(alpha = 0f),
            contentColor = Colors.DisabledText,
            borderColor = Colors.Hairline,
            borderWidth = 2.dp,
        ),
        focusedVisuals = ButtonVisuals(
            containerColor = Colors.Canvas.copy(alpha = 0f),
            contentColor = Colors.InkDeep,
            borderColor = Colors.FbBlue,
            borderWidth = 2.dp,
        ),
    )
}

@Preview(name = "GhostButton — default")
@Composable
private fun GhostButtonDefaultPreview() {
    Theme {
        GhostButton(text = "Compare", onClick = {})
    }
}

@Preview(name = "GhostButton — disabled")
@Composable
private fun GhostButtonDisabledPreview() {
    Theme {
        GhostButton(text = "Compare", onClick = {}, enabled = false)
    }
}
