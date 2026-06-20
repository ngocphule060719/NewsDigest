package com.app.designsystem.component.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.foundation.Typography
import com.app.designsystem.theme.Theme

/**
 * White feature card with product photography and copy.
 *
 * Meta source: `card-product-feature` — `{rounded.xxxl}`, `{spacing.xxl}` padding,
 * `1px solid {colors.hairline-soft}` border, flat elevation (level 0).
 *
 * @param title Card headline (`{typography.heading-sm}`).
 * @param description Optional supporting copy (`{typography.body-md}`).
 * @param image Optional leading image slot (e.g. product photography).
 * @param onClick Optional tap handler; when set, pressed state uses `{colors.surface-soft}` fill.
 * @param enabled When `false`, renders the disabled border/background treatment.
 */
@Composable
fun ProductFeatureCard(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    image: (@Composable () -> Unit)? = null,
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
        shape = RoundedCornerShape(Shapes.Xxxl),
        onClick = onClick,
        defaultVisuals = defaultVisuals,
        pressedVisuals = pressedVisuals,
        disabledVisuals = disabledVisuals,
    ) {
        Column(
            modifier = Modifier.padding(Spacing.Xxl),
            verticalArrangement = Arrangement.spacedBy(Spacing.Base),
        ) {
            image?.invoke()
            Text(
                text = title,
                style = Typography.headingSm,
                color = if (enabled) Colors.InkDeep else Colors.Stone,
            )
            if (description != null) {
                Text(
                    text = description,
                    style = Typography.bodyMd,
                    color = if (enabled) Colors.Charcoal else Colors.Stone,
                )
            }
        }
    }
}

@Preview(name = "ProductFeatureCard — default")
@Composable
private fun ProductFeatureCardDefaultPreview() {
    Theme {
        ProductFeatureCard(
            modifier = Modifier.fillMaxWidth(),
            title = "Designed for sport",
            description = "Advanced engineering inside and out.",
        )
    }
}

@Preview(name = "ProductFeatureCard — disabled")
@Composable
private fun ProductFeatureCardDisabledPreview() {
    Theme {
        ProductFeatureCard(
            modifier = Modifier.fillMaxWidth(),
            title = "Designed for sport",
            description = "Advanced engineering inside and out.",
            enabled = false,
        )
    }
}

@Preview(name = "ProductFeatureCard — clickable")
@Composable
private fun ProductFeatureCardClickablePreview() {
    Theme {
        ProductFeatureCard(
            modifier = Modifier.fillMaxWidth(),
            title = "Built for prescriptions",
            description = "Comfort-first frames with smart audio.",
            onClick = {},
        )
    }
}
