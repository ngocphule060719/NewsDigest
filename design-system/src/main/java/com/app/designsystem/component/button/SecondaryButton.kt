package com.app.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.theme.Theme

/**
 * Outlined secondary CTA paired with marketing primary in dual-CTA hero patterns.
 *
 * Meta source: `button-secondary`.
 */
@Composable
fun SecondaryButton(
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
        contentPadding = PaddingValues(horizontal = 28.dp, vertical = 12.dp),
        textStyle = Theme.typography.buttonMd,
        defaultVisuals = ButtonVisuals(
            containerColor = Colors.Canvas.copy(alpha = 0f),
            contentColor = Colors.InkDeep,
            borderColor = Colors.InkDeep,
            borderWidth = 2.dp,
        ),
        pressedVisuals = ButtonVisuals(
            containerColor = Colors.SurfaceSoft,
            contentColor = Colors.InkDeep,
            borderColor = Colors.InkDeep,
            borderWidth = 2.dp,
        ),
        disabledVisuals = ButtonVisuals(
            containerColor = Colors.Canvas.copy(alpha = 0f),
            contentColor = Colors.DisabledText,
            borderColor = Colors.DisabledText,
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

@Preview(name = "SecondaryButton — default")
@Composable
private fun SecondaryButtonDefaultPreview() {
    Theme {
        SecondaryButton(text = "Learn more", onClick = {})
    }
}

@Preview(name = "SecondaryButton — disabled")
@Composable
private fun SecondaryButtonDisabledPreview() {
    Theme {
        SecondaryButton(text = "Learn more", onClick = {}, enabled = false)
    }
}
