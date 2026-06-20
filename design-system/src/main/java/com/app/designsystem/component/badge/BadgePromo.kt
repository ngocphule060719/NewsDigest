package com.app.designsystem.component.badge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.designsystem.foundation.Colors
import com.app.designsystem.theme.Theme

/**
 * Limited-time promotional status chip ("Limited time", "Sale").
 *
 * Meta source: `badge-promo-yellow`.
 *
 * @param text Badge label.
 * @param enabled When `false`, renders the disabled treatment (`{colors.disabled-text}` / `{colors.canvas}`).
 */
@Composable
fun BadgePromo(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    BadgeSurface(
        text = text,
        backgroundColor = Colors.Warning,
        contentColor = Colors.InkDeep,
        modifier = modifier,
        enabled = enabled,
    )
}

@Preview(name = "BadgePromo — default")
@Composable
private fun BadgePromoDefaultPreview() {
    Theme {
        BadgePromo(text = "Limited time")
    }
}

@Preview(name = "BadgePromo — disabled")
@Composable
private fun BadgePromoDisabledPreview() {
    Theme {
        BadgePromo(text = "Sale", enabled = false)
    }
}
