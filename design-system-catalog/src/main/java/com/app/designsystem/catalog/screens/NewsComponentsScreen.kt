package com.app.designsystem.catalog.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.designsystem.component.news.ArticleCard
import com.app.designsystem.component.news.ErrorState
import com.app.designsystem.component.news.LoadingState
import com.app.designsystem.component.news.LoadingStateVariant
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun NewsComponentsScreen() {
    var bookmarked by remember { mutableStateOf(false) }
    var showSkeleton by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.padding(Spacing.Base),
        verticalArrangement = Arrangement.spacedBy(Spacing.Xl),
    ) {
        item {
            Column {
                Text(
                    text = "Toggle skeleton loading",
                    style = Theme.typography.bodySm,
                    color = Colors.Charcoal,
                )
                Switch(checked = showSkeleton, onCheckedChange = { showSkeleton = it })
            }
        }
        item {
            CatalogSample(label = "ArticleCard") {
                ArticleCard(
                    title = "Markets rally as tech earnings beat expectations",
                    metadata = "Reuters · 2h ago",
                    isBookmarked = bookmarked,
                    isLoading = showSkeleton,
                    onClick = {},
                    onBookmarkToggle = { bookmarked = !bookmarked },
                )
            }
        }
        item {
            CatalogSample(label = "ErrorState") {
                ErrorState(
                    message = "Unable to load articles. Check your connection and try again.",
                    onRetry = {},
                )
            }
        }
        item {
            CatalogSample(label = "LoadingState · FullScreen") {
                LoadingState(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    variant = LoadingStateVariant.FullScreen,
                )
            }
        }
        item {
            CatalogSample(label = "LoadingState · Inline") {
                LoadingState(variant = LoadingStateVariant.Inline)
            }
        }
        item {
            CatalogSample(label = "LoadingState · ArticleCardSkeleton") {
                LoadingState(
                    variant = LoadingStateVariant.ArticleCardSkeleton,
                    skeletonCount = 2,
                )
            }
        }
    }
}
