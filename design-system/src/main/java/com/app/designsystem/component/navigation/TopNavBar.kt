package com.app.designsystem.component.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Shapes
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.foundation.Typography
import com.app.designsystem.theme.Theme

/**
 * Category pill for the top navigation bar.
 *
 * Meta source: `button-pill-tab` / `button-pill-tab-active`.
 */
data class TopNavTab(
    val label: String,
    val selected: Boolean,
)

/**
 * Sticky desktop-style top navigation bar with logo, category pills, search, and utility icons.
 *
 * Meta source: Top Navigation (Desktop) — `{colors.canvas}` background, ~64px height,
 * bottom `1px solid {colors.hairline-soft}`, elevation level 2 when sticky.
 * Composes `button-pill-tab`, `search-pill`, and `button-icon-circular` patterns.
 */
@Composable
fun TopNavBar(
    tabs: List<TopNavTab>,
    onTabClick: (index: Int) -> Unit,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    logo: @Composable () -> Unit = {},
    searchPlaceholder: String = "Search",
    onAccountClick: () -> Unit = {},
    onCartClick: () -> Unit = {},
    accountContentDescription: String = "Account",
    cartContentDescription: String = "Cart",
    showSearch: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .stickyPanelShadow()
            .height(Spacing.Section)
            .background(Colors.Canvas)
            .border(width = 1.dp, color = Colors.HairlineSoft)
            .padding(horizontal = Spacing.Xl),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Spacing.Md),
    ) {
        Box(modifier = Modifier.padding(end = Spacing.Sm)) {
            logo()
        }

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Xs, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.forEachIndexed { index, tab ->
                TopNavPillTab(
                    text = tab.label,
                    selected = tab.selected,
                    onClick = { onTabClick(index) },
                )
            }
        }

        if (showSearch) {
            TopNavSearchPill(
                value = searchValue,
                onValueChange = onSearchValueChange,
                placeholder = searchPlaceholder,
                modifier = Modifier.widthIn(min = 120.dp, max = 220.dp),
            )
        }

        TopNavIconButton(
            icon = Icons.Outlined.Person,
            contentDescription = accountContentDescription,
            onClick = onAccountClick,
        )
        TopNavIconButton(
            icon = Icons.Outlined.ShoppingCart,
            contentDescription = cartContentDescription,
            onClick = onCartClick,
        )
    }
}

@Composable
private fun TopNavPillTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val isFocused by interactionSource.collectIsFocusedAsState()

    val containerColor = when {
        selected && isPressed -> Colors.Charcoal
        selected -> Colors.InkDeep
        isPressed -> Colors.SurfaceSoft
        else -> Colors.Canvas
    }
    val contentColor = if (selected) Colors.Canvas else Colors.Ink
    val border = when {
        isFocused -> BorderStroke(2.dp, Colors.FbBlue)
        selected -> null
        else -> BorderStroke(1.dp, Colors.Hairline)
    }

    Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 36.dp),
        interactionSource = interactionSource,
        shape = RoundedCornerShape(Shapes.Full),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        border = border,
        contentPadding = PaddingValues(horizontal = Spacing.Base, vertical = Spacing.Xs),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
    ) {
        Text(text = text, style = Typography.bodySmBold)
    }
}

@Composable
private fun TopNavSearchPill(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(Shapes.Full))
            .background(Colors.SurfaceSoft)
            .padding(horizontal = Spacing.Lg, vertical = Spacing.Md),
        textStyle = Typography.bodySm.copy(color = Colors.Ink),
        cursorBrush = SolidColor(Colors.InkDeep),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier.fillMaxHeight(),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = Typography.bodySm,
                        color = Colors.Steel,
                    )
                }
                innerTextField()
            }
        },
    )
}

@Composable
private fun TopNavIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Colors.Canvas)
            .semantics { this.contentDescription = contentDescription },
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Colors.Ink,
            modifier = Modifier.size(20.dp),
        )
    }
}

@Preview(name = "TopNavBar — desktop")
@Composable
private fun TopNavBarPreview() {
    Theme {
        TopNavBar(
            tabs = listOf(
                TopNavTab(label = "Glasses", selected = true),
                TopNavTab(label = "Quest", selected = false),
                TopNavTab(label = "Apps", selected = false),
            ),
            onTabClick = {},
            searchValue = "",
            onSearchValueChange = {},
            logo = {
                Text(
                    text = "Meta",
                    style = Typography.bodySmBold,
                    color = Colors.InkDeep,
                )
            },
        )
    }
}

@Preview(name = "TopNavBar — with search query")
@Composable
private fun TopNavBarSearchPreview() {
    Theme {
        TopNavBar(
            tabs = listOf(
                TopNavTab(label = "Glasses", selected = false),
                TopNavTab(label = "Quest", selected = true),
            ),
            onTabClick = {},
            searchValue = "Ray-Ban",
            onSearchValueChange = {},
            logo = {
                Text(
                    text = "Meta",
                    style = Typography.bodySmBold,
                    color = Colors.InkDeep,
                )
            },
        )
    }
}
