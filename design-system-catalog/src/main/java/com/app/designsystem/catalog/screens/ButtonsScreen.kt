package com.app.designsystem.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.designsystem.component.button.BuyCtaButton
import com.app.designsystem.component.button.GhostButton
import com.app.designsystem.component.button.IconCircularButton
import com.app.designsystem.component.button.PillTab
import com.app.designsystem.component.button.PrimaryButton
import com.app.designsystem.component.button.SecondaryButton
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun ButtonsScreen() {
    var disabled by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableIntStateOf(0) }

    LazyColumn(
        modifier = Modifier.padding(Spacing.Base),
        verticalArrangement = Arrangement.spacedBy(Spacing.Xl),
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Toggle disabled (primary)",
                    style = Theme.typography.bodySm,
                    color = Colors.Charcoal,
                )
                Switch(checked = disabled, onCheckedChange = { disabled = it })
            }
        }
        item {
            CatalogSample(label = "PrimaryButton · button-primary") {
                PrimaryButton(
                    text = "Continue",
                    onClick = {},
                    enabled = !disabled,
                )
            }
        }
        item {
            CatalogSample(label = "BuyCtaButton · button-buy-cta (#0064E0)") {
                BuyCtaButton(text = "Buy now", onClick = {})
            }
        }
        item {
            CatalogSample(label = "SecondaryButton · button-secondary") {
                SecondaryButton(text = "Learn more", onClick = {})
            }
        }
        item {
            CatalogSample(label = "GhostButton · button-ghost") {
                GhostButton(text = "Skip", onClick = {})
            }
        }
        item {
            CatalogSample(label = "PillTab · button-pill-tab") {
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Xs)) {
                    PillTab(
                        text = "For you",
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 },
                    )
                    PillTab(
                        text = "Trending",
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 },
                    )
                    PillTab(
                        text = "Saved",
                        selected = selectedTab == 2,
                        onClick = { selectedTab = 2 },
                    )
                }
            }
        }
        item {
            CatalogSample(label = "IconCircularButton · button-icon-circular") {
                IconCircularButton(
                    icon = Icons.Filled.Favorite,
                    contentDescription = "Favorite",
                    onClick = {},
                )
            }
        }
    }
}
