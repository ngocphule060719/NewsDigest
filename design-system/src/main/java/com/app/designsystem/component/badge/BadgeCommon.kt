package com.app.designsystem.component.badge

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.foundation.Typography

/**
 * Shared pill badge chrome for Meta status chips.
 *
 * Meta tokens: `{rounded.full}`, `{typography.caption-bold}`, padding `4px 10px`.
 */
@Composable
internal fun BadgeSurface(
    text: String,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val resolvedBackground = if (enabled) backgroundColor else Colors.DisabledText
    val resolvedContent = if (enabled) contentColor else Colors.Canvas

    Surface(
        modifier = modifier,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(Shapes.Full),
        color = resolvedBackground,
        shadowElevation = 0.dp,
        tonalElevation = 0.dp,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = Spacing.Sm,
                vertical = Spacing.Xxs,
            ),
            style = Typography.captionBold,
            color = resolvedContent,
        )
    }
}
