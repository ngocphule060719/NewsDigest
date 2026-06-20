package com.app.newsdigest.presentation.articlelist

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.designsystem.component.button.GhostButton
import com.app.newsdigest.presentation.navigation.PlaceholderScreen

@Composable
fun ArticleListPlaceholder(
    onOpenDetail: (articleId: String) -> Unit,
    onOpenBookmarks: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PlaceholderScreen(
        title = "Article List",
        subtitle = "Phase 5 — headlines, categories, pull-to-refresh",
        modifier = modifier,
        actions = {
            GhostButton(
                text = "Open sample article",
                onClick = { onOpenDetail("sample-article-id") },
            )
            GhostButton(text = "Bookmarks", onClick = onOpenBookmarks)
        },
    )
}
