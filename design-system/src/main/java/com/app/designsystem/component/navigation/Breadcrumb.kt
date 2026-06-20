package com.app.designsystem.component.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.foundation.Typography
import com.app.designsystem.theme.Theme

/**
 * Inline breadcrumb path above product content (e.g. "Glasses › Ray-Ban Meta › Skyler (Gen 2)").
 *
 * Meta source: PDP breadcrumb — typography `{typography.body-sm}`, separator in `{colors.stone}`,
 * active leaf in `{colors.ink}`, parent links in `{colors.steel}`.
 */
data class BreadcrumbItem(
    val label: String,
    /** When null, the item is the active leaf and is not clickable. */
    val onClick: (() -> Unit)? = null,
)

@Composable
fun Breadcrumb(
    items: List<BreadcrumbItem>,
    modifier: Modifier = Modifier,
    separator: String = "›",
) {
    require(items.isNotEmpty()) { "Breadcrumb requires at least one item" }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEachIndexed { index, item ->
            if (index > 0) {
                Text(
                    text = separator,
                    modifier = Modifier.padding(horizontal = Spacing.Xxs),
                    style = Typography.bodySm,
                    color = Colors.Stone,
                )
            }

            val isActiveLeaf = item.onClick == null
            val textColor = if (isActiveLeaf) Colors.Ink else Colors.Steel
            val itemModifier = if (isActiveLeaf) {
                Modifier
            } else {
                Modifier.clickable(onClick = item.onClick!!)
            }

            Text(
                text = item.label,
                modifier = itemModifier,
                style = Typography.bodySm,
                color = textColor,
            )
        }
    }
}

@Preview(name = "Breadcrumb — PDP path")
@Composable
private fun BreadcrumbPreview() {
    Theme {
        Breadcrumb(
            items = listOf(
                BreadcrumbItem(label = "Glasses", onClick = {}),
                BreadcrumbItem(label = "Ray-Ban Meta", onClick = {}),
                BreadcrumbItem(label = "Skyler (Gen 2)"),
            ),
        )
    }
}
