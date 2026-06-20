package com.app.designsystem.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.designsystem.component.badge.BadgeAttention
import com.app.designsystem.component.badge.BadgeCritical
import com.app.designsystem.component.badge.BadgePromo
import com.app.designsystem.component.badge.BadgeSuccess
import com.app.designsystem.foundation.Spacing

@Composable
fun BadgesScreen() {
    LazyColumn(
        modifier = Modifier.padding(Spacing.Base),
        verticalArrangement = Arrangement.spacedBy(Spacing.Xl),
    ) {
        item {
            CatalogSample(label = "BadgePromo") {
                BadgePromo(text = "New")
            }
        }
        item {
            CatalogSample(label = "BadgeAttention") {
                BadgeAttention(text = "Limited")
            }
        }
        item {
            CatalogSample(label = "BadgeSuccess") {
                BadgeSuccess(text = "Synced")
            }
        }
        item {
            CatalogSample(label = "BadgeCritical") {
                BadgeCritical(text = "Offline")
            }
        }
        item {
            CatalogSample(label = "All variants") {
                Row(horizontalArrangement = Arrangement.spacedBy(Spacing.Sm)) {
                    BadgePromo(text = "Promo")
                    BadgeAttention(text = "Alert")
                    BadgeSuccess(text = "OK")
                    BadgeCritical(text = "Error")
                }
            }
        }
    }
}
