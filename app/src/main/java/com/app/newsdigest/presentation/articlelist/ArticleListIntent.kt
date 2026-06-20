package com.app.newsdigest.presentation.articlelist

import com.app.newsdigest.domain.model.Category

sealed interface ArticleListIntent {
    data object LoadArticles : ArticleListIntent

    data object Refresh : ArticleListIntent

    data class SelectCategory(val category: Category) : ArticleListIntent

    data class ToggleBookmark(val articleId: String) : ArticleListIntent

    data class OpenArticle(val articleId: String) : ArticleListIntent
}
