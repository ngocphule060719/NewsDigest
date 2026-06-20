package com.app.newsdigest.presentation.bookmarks

sealed interface BookmarksIntent {
    data object LoadBookmarks : BookmarksIntent
    data class ToggleBookmark(val articleId: String) : BookmarksIntent
    data class OpenArticle(val articleId: String) : BookmarksIntent
}
