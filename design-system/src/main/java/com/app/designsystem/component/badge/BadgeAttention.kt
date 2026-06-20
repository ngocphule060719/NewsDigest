package com.app.designsystem.component.badge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.designsystem.foundation.Colors
import com.app.designsystem.theme.Theme

/**
 * Mid-priority status indicator ("Almost gone", "Selling fast").
 *
 * Meta source: `badge-attention`.
 *
 * @param text Badge label.
 * @param enabled When `false`, renders the disabled treatment.
 */
@Composable
fun BadgeAttention(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    BadgeSurface(
        text = text,
        backgroundColor = Colors.Attention,
        contentColor = Colors.Canvas,
        modifier = modifier,
        enabled = enabled,
    )
}

@Preview(name = "BadgeAttention — default")
@Composable
private fun BadgeAttentionDefaultPreview() {
    Theme {
        BadgeAttention(text = "Selling fast")
    }
}

@Preview(name = "BadgeAttention — disabled")
@Composable
private fun BadgeAttentionDisabledPreview() {
    Theme {
        BadgeAttention(text = "Almost gone", enabled = false)
    }
}
