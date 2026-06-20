package com.app.newsdigest.presentation.articlelist

import com.app.newsdigest.domain.model.Category

data class ArticleUiState(
    val id: String,
    val title: String,
    val summary: String?,
    val metadata: String,
    val categoryLabel: String?,
    val imageUrl: String?,
    val isBookmarked: Boolean,
)

data class CategoryTabUiState(
    val category: Category,
    val label: String,
    val isSelected: Boolean,
)

data class ArticleListUiState(
    val isLoading: Boolean = false,
    val isLoadingArticles: Boolean = false,
    val isRefreshing: Boolean = false,
    val isOfflineStale: Boolean = false,
    val useHeroLayout: Boolean = false,
    val articles: List<ArticleUiState> = emptyList(),
    val selectedCategory: Category = Category.GENERAL,
    val categoryTabs: List<CategoryTabUiState> = defaultTabs(),
    val error: String? = null,
    val isEmptyCategory: Boolean = false,
)

fun defaultTabs(selectedCategory: Category = Category.GENERAL): List<CategoryTabUiState> =
    Category.entries.map { category ->
        CategoryTabUiState(
            category = category,
            label = category.displayLabel(),
            isSelected = category == selectedCategory,
        )
    }
