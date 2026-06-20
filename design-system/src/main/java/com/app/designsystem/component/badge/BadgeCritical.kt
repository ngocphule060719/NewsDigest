package com.app.designsystem.component.badge

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.designsystem.foundation.Colors
import com.app.designsystem.theme.Theme

/**
 * Urgent or destructive status chip ("Out of stock", "Discontinued").
 *
 * Meta source: `badge-critical`.
 *
 * @param text Badge label.
 * @param enabled When `false`, renders the disabled treatment.
 */
@Composable
fun BadgeCritical(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    BadgeSurface(
        text = text,
        backgroundColor = Colors.Critical,
        contentColor = Colors.Canvas,
        modifier = modifier,
        enabled = enabled,
    )
}

@Preview(name = "BadgeCritical — default")
@Composable
private fun BadgeCriticalDefaultPreview() {
    Theme {
        BadgeCritical(text = "Out of stock")
    }
}

@Preview(name = "BadgeCritical — disabled")
@Composable
private fun BadgeCriticalDisabledPreview() {
    Theme {
        BadgeCritical(text = "Discontinued", enabled = false)
    }
}
