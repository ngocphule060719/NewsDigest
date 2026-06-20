package com.app.newsdigest.presentation.bookmarks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.app.designsystem.component.news.ArticleCard
import com.app.designsystem.foundation.Spacing
import com.app.newsdigest.presentation.articlelist.ArticleThumbnail
import com.app.newsdigest.presentation.articlelist.ArticleThumbnailSize

private const val LOADING_SKELETON_COUNT = 3

@Composable
fun BookmarksContent(
    state: BookmarksUiState,
    onIntent: (BookmarksIntent) -> Unit,
    onOpenDetail: (String) -> Unit,
    onOpenNews: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        state.isLoading && state.bookmarks.isEmpty() -> {
            LoadingContent(modifier = modifier)
        }

        state.bookmarks.isEmpty() -> {
            EmptyContent(
                onBrowseNews = onOpenNews,
                modifier = modifier,
            )
        }

        else -> {
            LoadedContent(
                state = state,
                onIntent = onIntent,
                onOpenDetail = onOpenDetail,
                modifier = modifier,
            )
        }
    }
}

@Composable
private fun LoadingContent(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("bookmarks_loading"),
        contentPadding = PaddingValues(
            horizontal = Spacing.Base,
            vertical = Spacing.Sm,
        ),
        verticalArrangement = Arrangement.spacedBy(Spacing.Base),
    ) {
        items(LOADING_SKELETON_COUNT) {
            ArticleCard(
                title = "",
                metadata = "",
                isLoading = true,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun EmptyContent(
    onBrowseNews: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        BookmarksEmptyState(onBrowseNews = onBrowseNews)
    }
}

@Composable
private fun LoadedContent(
    state: BookmarksUiState,
    onIntent: (BookmarksIntent) -> Unit,
    onOpenDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = Spacing.Base,
            vertical = Spacing.Sm,
        ),
        verticalArrangement = Arrangement.spacedBy(Spacing.Base),
    ) {
        items(
            items = state.bookmarks,
            key = { it.articleId },
        ) { bookmark ->
            ArticleCard(
                title = bookmark.title,
                metadata = bookmark.metadata,
                isBookmarked = bookmark.isBookmarked,
                thumbnail = { thumbnailModifier ->
                    ArticleThumbnail(
                        imageUrl = bookmark.imageUrl,
                        size = ArticleThumbnailSize.Compact,
                        modifier = thumbnailModifier,
                        contentDescription = bookmark.title,
                    )
                },
                onClick = { onOpenDetail(bookmark.articleId) },
                onBookmarkToggle = {
                    onIntent(BookmarksIntent.ToggleBookmark(bookmark.articleId))
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
