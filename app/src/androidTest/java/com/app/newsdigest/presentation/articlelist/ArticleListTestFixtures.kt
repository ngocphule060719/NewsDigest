package com.app.newsdigest.presentation.articlelist

import com.app.newsdigest.domain.model.Category

object ArticleListTestFixtures {

    fun loadingState(): ArticleListUiState = ArticleListUiState(
        isLoading = true,
    )

    fun errorState(): ArticleListUiState = ArticleListUiState(
        error = "Unable to load articles.",
    )

    fun emptyCategoryState(
        category: Category = Category.GENERAL,
    ): ArticleListUiState = ArticleListUiState(
        isEmptyCategory = true,
        selectedCategory = category,
        categoryTabs = defaultTabs(category),
    )

    fun offlineStaleState(): ArticleListUiState = ArticleListUiState(
        isOfflineStale = true,
        useHeroLayout = true,
        articles = listOf(sampleArticle("article-1", "Cached headline")),
    )

    fun loadedState(): ArticleListUiState = ArticleListUiState(
        articles = listOf(
            sampleArticle("article-1", "Breaking news headline"),
        ),
    )

    private fun sampleArticle(
        id: String,
        title: String,
    ): ArticleUiState = ArticleUiState(
        id = id,
        title = title,
        summary = "Summary for $title",
        metadata = "News Digest · 1h ago",
        categoryLabel = Category.GENERAL.displayLabel(),
        imageUrl = null,
        isBookmarked = false,
    )
}
