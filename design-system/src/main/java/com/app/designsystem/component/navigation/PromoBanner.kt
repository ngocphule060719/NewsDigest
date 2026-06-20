package com.app.designsystem.component.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.foundation.Typography
import com.app.designsystem.theme.Theme

/**
 * Full-width promotional strip above the top navigation for time-bound offers.
 *
 * Meta source: `promo-banner` — background `{colors.ink-deep}` or `{colors.warning}`,
 * typography `{typography.body-sm-bold}`, padding `{spacing.md} {spacing.xl}`.
 */
enum class PromoBannerVariant {
    /** Dark strip: `{colors.ink-deep}` background, `{colors.canvas}` text. */
    Dark,

    /** Yellow promo: `{colors.warning}` background, `{colors.ink-deep}` text. */
    Yellow,
}

@Composable
fun PromoBanner(
    message: String,
    modifier: Modifier = Modifier,
    variant: PromoBannerVariant = PromoBannerVariant.Dark,
    linkText: String? = null,
    onLinkClick: (() -> Unit)? = null,
) {
    val backgroundColor = when (variant) {
        PromoBannerVariant.Dark -> Colors.InkDeep
        PromoBannerVariant.Yellow -> Colors.Warning
    }
    val contentColor = when (variant) {
        PromoBannerVariant.Dark -> Colors.Canvas
        PromoBannerVariant.Yellow -> Colors.InkDeep
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(
                horizontal = Spacing.Xl,
                vertical = Spacing.Md,
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = message,
            modifier = Modifier.weight(1f, fill = false),
            style = Typography.bodySmBold,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        if (linkText != null && onLinkClick != null) {
            Text(
                text = linkText,
                modifier = Modifier
                    .padding(start = Spacing.Xs)
                    .clickable(onClick = onLinkClick),
                style = Typography.bodySmBold,
                color = contentColor,
                textDecoration = TextDecoration.Underline,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview(name = "PromoBanner — dark")
@Composable
private fun PromoBannerDarkPreview() {
    Theme {
        PromoBanner(
            message = "Get 25% off the #1 selling AI glasses",
            linkText = "Shop now",
            onLinkClick = {},
        )
    }
}

@Preview(name = "PromoBanner — yellow")
@Composable
private fun PromoBannerYellowPreview() {
    Theme {
        PromoBanner(
            message = "Limited time — save on Ray-Ban Meta",
            variant = PromoBannerVariant.Yellow,
            linkText = "Learn more",
            onLinkClick = {},
        )
    }
}
