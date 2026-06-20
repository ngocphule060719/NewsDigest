package com.app.newsdigest.presentation.bookmarks

class BookmarkUiState(
    val articleId: String,
    val title: String,
    val metadata: String,
    val imageUrl: String?,
    val isBookmarked: Boolean = true,
)

class BookmarksUiState(
    val isLoading: Boolean = true,
    val bookmarks: List<BookmarkUiState> = emptyList(),
)
