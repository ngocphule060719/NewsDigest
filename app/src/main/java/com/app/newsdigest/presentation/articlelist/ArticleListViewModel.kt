package com.app.newsdigest.presentation.articlelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.newsdigest.concurrency.launchIO
import com.app.newsdigest.concurrency.toStateFlow
import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Category
import com.app.newsdigest.domain.platform.ConnectivityMonitor
import com.app.newsdigest.domain.repository.BookmarkRepository
import com.app.newsdigest.domain.repository.NewsRepository
import com.app.newsdigest.presentation.common.AppUiStrings
import com.app.newsdigest.presentation.common.resolveRefreshErrorMessage
import com.app.newsdigest.support.Once
import com.app.newsdigest.support.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val connectivityMonitor: ConnectivityMonitor,
) : ViewModel() {

    private val selectedCategory = MutableStateFlow(Category.GENERAL)
    private val refreshFlags = MutableStateFlow(RefreshFlags(isLoading = true))
    private val latestArticles = MutableStateFlow<List<Article>>(emptyList())
    private val latestBookmarkIds = MutableStateFlow<Set<String>>(emptySet())
    private val isConnected = MutableStateFlow(true)

    private val _flowSnackbarMessage = MutableStateFlow(Once<String>(null))
    val flowSnackbarMessage: StateFlow<Once<String>> = _flowSnackbarMessage.asStateFlow()

    private val headlinesFlow = selectedCategory.flatMapLatest { category ->
        newsRepository.observeTopHeadlines(category)
    }

    private val bookmarkIdsFlow = bookmarkRepository.observeBookmarks()
        .map { bookmarks -> bookmarks.map { it.articleId }.toSet() }

    val uiState: StateFlow<ArticleListUiState> = combine(
        selectedCategory,
        refreshFlags,
        headlinesFlow,
        bookmarkIdsFlow,
        isConnected,
    ) { category, flags, articles, bookmarkIds, connected ->
        latestArticles.value = articles
        latestBookmarkIds.value = bookmarkIds
        buildUiState(
            selectedCategory = category,
            flags = flags,
            articles = articles,
            bookmarkIds = bookmarkIds,
            isConnected = connected,
        )
    }.toStateFlow(
        scope = viewModelScope,
        initialValue = ArticleListUiState(isLoading = true),
    )

    init {
        onIntent(ArticleListIntent.LoadArticles)
        viewModelScope.launchIO {
            connectivityMonitor.isConnected.collect { connected ->
                isConnected.value = connected
            }
        }
    }

    fun onIntent(intent: ArticleListIntent) {
        when (intent) {
            ArticleListIntent.LoadArticles -> refresh()
            ArticleListIntent.Refresh -> refresh(isPullToRefresh = true)
            is ArticleListIntent.SelectCategory -> selectCategory(intent.category)
            is ArticleListIntent.ToggleBookmark -> toggleBookmark(intent.articleId)
            is ArticleListIntent.OpenArticle -> Unit
        }
    }

    private fun selectCategory(category: Category) {
        if (category == selectedCategory.value) {
            return
        }
        selectedCategory.value = category
        refresh(isCategoryChange = true)
    }

    private fun refresh(
        isPullToRefresh: Boolean = false,
        isCategoryChange: Boolean = false,
    ) {
        viewModelScope.launchIO {
            refreshFlags.update { flags ->
                when {
                    isPullToRefresh -> flags.copy(isRefreshing = true)
                    isCategoryChange -> flags.copy(
                        isLoadingArticles = true,
                        refreshFailed = false,
                        lastRefreshSucceeded = false,
                    )

                    else -> flags.copy(isLoading = true, refreshFailed = false)
                }
            }

            val category = selectedCategory.value
            when (val result = newsRepository.refreshTopHeadlines(category)) {
                is Result.Success -> {
                    refreshFlags.update {
                        it.copy(
                            isLoading = false,
                            isLoadingArticles = false,
                            isRefreshing = false,
                            refreshFailed = false,
                            lastRefreshSucceeded = true,
                        )
                    }
                }

                is Result.Error -> {
                    refreshFlags.update {
                        it.copy(
                            isLoading = false,
                            isLoadingArticles = false,
                            isRefreshing = false,
                            refreshFailed = true,
                            lastRefreshSucceeded = false,
                        )
                    }
                }
            }
        }
    }

    private fun toggleBookmark(articleId: String) {
        viewModelScope.launchIO {
            val article = latestArticles.value.find { it.id == articleId } ?: return@launchIO
            val result = if (articleId in latestBookmarkIds.value) {
                bookmarkRepository.removeBookmark(articleId)
            } else {
                bookmarkRepository.addBookmark(article)
            }
            if (result is Result.Error) {
                _flowSnackbarMessage.value = Once(AppUiStrings.BOOKMARK_FAILURE)
            }
        }
    }

    private fun buildUiState(
        selectedCategory: Category,
        flags: RefreshFlags,
        articles: List<Article>,
        bookmarkIds: Set<String>,
        isConnected: Boolean,
    ): ArticleListUiState {
        val isOfflineStale = !isConnected && articles.isNotEmpty()
        val error =
            if (flags.refreshFailed && articles.isEmpty()) {
                resolveRefreshErrorMessage(isConnected)
            } else {
                null
            }
        val isEmptyCategory = articles.isEmpty() &&
                !flags.isLoading &&
                !flags.isLoadingArticles &&
                !flags.isRefreshing &&
                flags.lastRefreshSucceeded &&
                !flags.refreshFailed

        return ArticleListUiState(
            isLoading = flags.isLoading && articles.isEmpty(),
            isLoadingArticles = flags.isLoadingArticles && articles.isEmpty(),
            isRefreshing = flags.isRefreshing,
            isOfflineStale = isOfflineStale,
            useHeroLayout = isOfflineStale,
            articles = articles.map { article -> article.toUiState(isBookmarked = article.id in bookmarkIds) },
            selectedCategory = selectedCategory,
            categoryTabs = defaultTabs(selectedCategory),
            error = error,
            isEmptyCategory = isEmptyCategory,
        )
    }

    private fun Article.toUiState(isBookmarked: Boolean): ArticleUiState =
        ArticleUiState(
            id = id,
            title = title,
            summary = description,
            metadata = ArticleMetadataFormatter.format(this),
            categoryLabel = category.displayLabel().uppercase(),
            imageUrl = imageUrl,
            isBookmarked = isBookmarked,
        )

    private data class RefreshFlags(
        val isLoading: Boolean = false,
        val isLoadingArticles: Boolean = false,
        val isRefreshing: Boolean = false,
        val refreshFailed: Boolean = false,
        val lastRefreshSucceeded: Boolean = false,
    )
}
