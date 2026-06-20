package com.app.designsystem.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.theme.Theme

/**
 * Top-of-page category navigation pill.
 *
 * Meta source: `button-pill-tab` / `button-pill-tab-active`.
 */
@Composable
fun PillTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val inactiveDefault = ButtonVisuals(
        containerColor = Colors.Canvas,
        contentColor = Colors.Ink,
        borderColor = Colors.Hairline,
        borderWidth = 1.dp,
    )
    val activeDefault = ButtonVisuals(
        containerColor = Colors.InkDeep,
        contentColor = Colors.Canvas,
    )

    PillTextButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        textStyle = Theme.typography.bodySmBold,
        defaultVisuals = if (selected) activeDefault else inactiveDefault,
        pressedVisuals = if (selected) {
            ButtonVisuals(
                containerColor = Colors.Charcoal,
                contentColor = Colors.Canvas,
            )
        } else {
            ButtonVisuals(
                containerColor = Colors.SurfaceSoft,
                contentColor = Colors.Ink,
                borderColor = Colors.Hairline,
                borderWidth = 1.dp,
            )
        },
        disabledVisuals = ButtonVisuals(
            containerColor = Colors.Canvas,
            contentColor = Colors.DisabledText,
            borderColor = Colors.Hairline,
            borderWidth = 1.dp,
        ),
        focusedVisuals = if (selected) {
            ButtonVisuals(
                containerColor = Colors.InkDeep,
                contentColor = Colors.Canvas,
                borderColor = Colors.FbBlue,
                borderWidth = 2.dp,
            )
        } else {
            ButtonVisuals(
                containerColor = Colors.Canvas,
                contentColor = Colors.Ink,
                borderColor = Colors.FbBlue,
                borderWidth = 2.dp,
            )
        },
    )
}

@Preview(name = "PillTab — inactive")
@Composable
private fun PillTabInactivePreview() {
    Theme {
        PillTab(text = "Glasses", selected = false, onClick = {})
    }
}

@Preview(name = "PillTab — active")
@Composable
private fun PillTabActivePreview() {
    Theme {
        PillTab(text = "Quest", selected = true, onClick = {})
    }
}

@Preview(name = "PillTab — disabled")
@Composable
private fun PillTabDisabledPreview() {
    Theme {
        PillTab(text = "Apps", selected = false, onClick = {}, enabled = false)
    }
}
