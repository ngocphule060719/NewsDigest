package com.app.designsystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.foundation.Typography
import com.app.designsystem.theme.Theme

/**
 * Three-up feature tile with line icon, headline, and short copy.
 *
 * Meta source: `card-icon-feature` — `{rounded.xl}`, `{spacing.xl}` padding,
 * `1px solid {colors.hairline-soft}` border, flat elevation (level 0).
 *
 * @param headline Feature title (`{typography.subtitle-lg}`).
 * @param body Supporting copy (`{typography.body-sm}`).
 * @param icon Leading icon slot (32dp line icon per `feature-icon-row` spec).
 * @param onClick Optional tap handler; when set, pressed state uses `{colors.surface-soft}` fill.
 * @param enabled When `false`, renders the disabled border/background treatment.
 */
@Composable
fun IconFeatureCard(
    headline: String,
    body: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    val defaultVisuals = CardVisuals(
        backgroundColor = Colors.Canvas,
        borderColor = Colors.HairlineSoft,
    )
    val pressedVisuals = CardVisuals(
        backgroundColor = Colors.SurfaceSoft,
        borderColor = Colors.HairlineSoft,
    )
    val disabledVisuals = CardVisuals(
        backgroundColor = Colors.Canvas,
        borderColor = Colors.Hairline,
    )

    FlatCard(
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(Shapes.Xl),
        onClick = onClick,
        defaultVisuals = defaultVisuals,
        pressedVisuals = pressedVisuals,
        disabledVisuals = disabledVisuals,
    ) {
        Column(
            modifier = Modifier.padding(Spacing.Xl),
            verticalArrangement = Arrangement.spacedBy(Spacing.Base),
        ) {
            icon()
            Text(
                text = headline,
                style = Typography.subtitleLg,
                color = if (enabled) Colors.InkDeep else Colors.Stone,
            )
            Text(
                text = body,
                style = Typography.bodySm,
                color = if (enabled) Colors.Charcoal else Colors.Stone,
            )
        }
    }
}

@Preview(name = "IconFeatureCard — default")
@Composable
private fun IconFeatureCardDefaultPreview() {
    Theme {
        IconFeatureCard(
            modifier = Modifier.fillMaxWidth(),
            headline = "Free 2-day delivery",
            body = "Fast shipping on eligible orders.",
            icon = { PreviewFeatureIcon() },
        )
    }
}

@Preview(name = "IconFeatureCard — disabled")
@Composable
private fun IconFeatureCardDisabledPreview() {
    Theme {
        IconFeatureCard(
            modifier = Modifier.fillMaxWidth(),
            headline = "Worry-free warranty",
            body = "Coverage included with every purchase.",
            enabled = false,
            icon = { PreviewFeatureIcon(color = Colors.Stone) },
        )
    }
}

@Preview(name = "IconFeatureCard — clickable")
@Composable
private fun IconFeatureCardClickablePreview() {
    Theme {
        IconFeatureCard(
            modifier = Modifier.fillMaxWidth(),
            headline = "Buy now, pay later",
            body = "Flexible payment options at checkout.",
            onClick = {},
            icon = { PreviewFeatureIcon() },
        )
    }
}

@Composable
private fun PreviewFeatureIcon(
    color: androidx.compose.ui.graphics.Color = Colors.InkDeep,
) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(color, RoundedCornerShape(Shapes.Md)),
    )
}
