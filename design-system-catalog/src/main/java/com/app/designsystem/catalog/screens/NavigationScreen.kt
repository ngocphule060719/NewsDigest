package com.app.designsystem.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.app.designsystem.component.navigation.Breadcrumb
import com.app.designsystem.component.navigation.BreadcrumbItem
import com.app.designsystem.component.navigation.PromoBanner
import com.app.designsystem.component.navigation.PromoBannerVariant
import com.app.designsystem.component.navigation.TopNavBar
import com.app.designsystem.component.navigation.TopNavTab
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun NavigationScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var search by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier.padding(Spacing.Base),
        verticalArrangement = Arrangement.spacedBy(Spacing.Xl),
    ) {
        item {
            CatalogSample(label = "TopNavBar") {
                TopNavBar(
                    tabs = listOf(
                        TopNavTab("Shop", selectedTab == 0),
                        TopNavTab("News", selectedTab == 1),
                        TopNavTab("Saved", selectedTab == 2),
                    ),
                    onTabClick = { selectedTab = it },
                    searchValue = search,
                    onSearchValueChange = { search = it },
                    logo = {
                        Text(
                            text = "ND",
                            style = Theme.typography.subtitleLg,
                            color = Colors.Primary,
                        )
                    },
                )
            }
        }
        item {
            CatalogSample(label = "Breadcrumb") {
                Breadcrumb(
                    items = listOf(
                        BreadcrumbItem(label = "Home", onClick = {}),
                        BreadcrumbItem(label = "Technology", onClick = {}),
                        BreadcrumbItem(label = "AI"),
                    ),
                )
            }
        }
        item {
            CatalogSample(label = "PromoBanner · dark") {
                PromoBanner(
                    message = "Breaking news alerts — enable notifications",
                    linkText = "Turn on",
                    onLinkClick = {},
                )
            }
        }
        item {
            CatalogSample(label = "PromoBanner · yellow") {
                PromoBanner(
                    message = "Limited time offer on premium sources",
                    variant = PromoBannerVariant.Yellow,
                    linkText = "Learn more",
                    onLinkClick = {},
                )
            }
        }
    }
}
