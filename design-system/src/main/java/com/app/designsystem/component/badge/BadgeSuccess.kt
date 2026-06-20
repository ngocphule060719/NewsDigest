package com.app.designsystem.component.badge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.designsystem.foundation.Colors
import com.app.designsystem.theme.Theme

/**
 * Affirmative status chip ("In stock", "Verified", "Free shipping").
 *
 * Meta source: `badge-success`.
 *
 * @param text Badge label.
 * @param enabled When `false`, renders the disabled treatment.
 */
@Composable
fun BadgeSuccess(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    BadgeSurface(
        text = text,
        backgroundColor = Colors.Success,
        contentColor = Colors.Canvas,
        modifier = modifier,
        enabled = enabled,
    )
}

@Preview(name = "BadgeSuccess — default")
@Composable
private fun BadgeSuccessDefaultPreview() {
    Theme {
        BadgeSuccess(text = "In stock")
    }
}

@Preview(name = "BadgeSuccess — disabled")
@Composable
private fun BadgeSuccessDisabledPreview() {
    Theme {
        BadgeSuccess(text = "Free shipping", enabled = false)
    }
}
