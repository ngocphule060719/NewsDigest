package com.app.newsdigest.presentation.detail

data class ShareArticlePayload(
    val title: String,
    val url: String,
)

data class OpenUrlPayload(
    val url: String,
)

data class ArticleDetailUiState(
    val isLoading: Boolean = true,
    val title: String = "",
    val description: String? = null,
    val content: String? = null,
    val metadata: String = "",
    val categoryLabel: String? = null,
    val imageUrl: String? = null,
    val isBookmarked: Boolean = false,
    val error: String? = null,
)

fun ArticleDetailUiState.hasLoadedArticle(): Boolean = title.isNotBlank()
