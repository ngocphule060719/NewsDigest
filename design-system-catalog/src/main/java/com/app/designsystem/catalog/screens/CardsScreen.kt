package com.app.designsystem.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.designsystem.component.card.IconFeatureCard
import com.app.designsystem.component.card.ProductFeatureCard
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun CardsScreen() {
    LazyColumn(
        modifier = Modifier.padding(Spacing.Base),
        verticalArrangement = Arrangement.spacedBy(Spacing.Xl),
    ) {
        item {
            CatalogSample(label = "ProductFeatureCard · card-product-feature") {
                ProductFeatureCard(
                    title = "Stay informed",
                    description = "Curated headlines from trusted sources, refreshed throughout the day.",
                )
            }
        }
        item {
            CatalogSample(label = "IconFeatureCard · card-icon-feature") {
                IconFeatureCard(
                    headline = "Offline reading",
                    body = "Bookmark articles to read later without a connection.",
                    icon = {
                        Text(
                            text = "📰",
                            style = Theme.typography.subtitleLg,
                            color = Colors.Primary,
                        )
                    },
                )
            }
        }
    }
}
