package com.app.newsdigest.presentation.detail

sealed interface ArticleDetailIntent {
    data object LoadArticle : ArticleDetailIntent

    data object ToggleBookmark : ArticleDetailIntent

    data object ShareArticle : ArticleDetailIntent

    data object OpenInBrowser : ArticleDetailIntent
}
