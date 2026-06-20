package com.app.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.theme.Theme

/**
 * Marketing-surface primary CTA.
 *
 * Meta source: `button-primary` / `button-primary-pressed` / `button-primary-disabled`.
 */
@Composable
fun PrimaryButton(
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
        contentPadding = PaddingValues(horizontal = 30.dp, vertical = 14.dp),
        textStyle = Theme.typography.buttonMd,
        defaultVisuals = ButtonVisuals(
            containerColor = Colors.InkButton,
            contentColor = Colors.OnInkButton,
        ),
        pressedVisuals = ButtonVisuals(
            containerColor = Colors.Charcoal,
            contentColor = Colors.OnInkButton,
        ),
        disabledVisuals = ButtonVisuals(
            containerColor = Colors.DisabledText,
            contentColor = Colors.Canvas,
        ),
        focusedVisuals = ButtonVisuals(
            containerColor = Colors.InkButton,
            contentColor = Colors.OnInkButton,
            borderColor = Colors.FbBlue,
            borderWidth = 2.dp,
        ),
    )
}

@Preview(name = "PrimaryButton — default")
@Composable
private fun PrimaryButtonDefaultPreview() {
    Theme {
        PrimaryButton(text = "Shop", onClick = {})
    }
}

@Preview(name = "PrimaryButton — disabled")
@Composable
private fun PrimaryButtonDisabledPreview() {
    Theme {
        PrimaryButton(text = "Shop", onClick = {}, enabled = false)
    }
}
