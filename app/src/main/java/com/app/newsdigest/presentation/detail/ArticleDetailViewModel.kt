package com.app.newsdigest.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.newsdigest.concurrency.launchIO
import com.app.newsdigest.concurrency.toStateFlow
import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.repository.BookmarkRepository
import com.app.newsdigest.domain.repository.NewsRepository
import com.app.newsdigest.presentation.articlelist.ArticleMetadataFormatter
import com.app.newsdigest.presentation.articlelist.displayLabel
import com.app.newsdigest.presentation.common.AppUiStrings
import com.app.newsdigest.presentation.navigation.AppRoutes
import com.app.newsdigest.support.Once
import com.app.newsdigest.support.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository,
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {

    private val articleId = savedStateHandle.get<String>(AppRoutes.ARG_ARTICLE_ID)?.trim().orEmpty()
    private var loadedArticle: Article? = null

    private val loadState = MutableStateFlow(ArticleLoadState())
    private val latestBookmarkIds = MutableStateFlow<Set<String>>(emptySet())

    private val bookmarkIdsFlow = bookmarkRepository.observeBookmarks()
        .map { bookmarks -> bookmarks.map { it.articleId }.toSet() }

    val uiState: StateFlow<ArticleDetailUiState> = combine(
        loadState,
        bookmarkIdsFlow,
    ) { load, bookmarkIds ->
        latestBookmarkIds.value = bookmarkIds
        buildUiState(
            load = load,
            isBookmarked = articleId in bookmarkIds,
        )
    }.toStateFlow(
        scope = viewModelScope,
        initialValue = ArticleDetailUiState(isLoading = true),
    )

    private val _shareArticle = MutableStateFlow(Once<ShareArticlePayload>(null))
    val shareArticle: StateFlow<Once<ShareArticlePayload>> = _shareArticle.asStateFlow()

    private val _openInBrowser = MutableStateFlow(Once<OpenUrlPayload>(null))
    val openInBrowser: StateFlow<Once<OpenUrlPayload>> = _openInBrowser.asStateFlow()

    private val _flowSnackbarMessage = MutableStateFlow(Once<String>(null))
    val flowSnackbarMessage: StateFlow<Once<String>> = _flowSnackbarMessage.asStateFlow()

    init {
        if (articleId.isBlank()) {
            loadState.value = ArticleLoadState(
                isLoading = false,
                error = INVALID_ARTICLE_ID_MESSAGE,
            )
        } else {
            onIntent(ArticleDetailIntent.LoadArticle)
        }
    }

    fun onIntent(intent: ArticleDetailIntent) {
        when (intent) {
            ArticleDetailIntent.LoadArticle -> loadArticle()
            ArticleDetailIntent.ToggleBookmark -> toggleBookmark()
            ArticleDetailIntent.ShareArticle -> shareArticle()
            ArticleDetailIntent.OpenInBrowser -> openInBrowser()
        }
    }

    private fun loadArticle() {
        if (articleId.isBlank()) {
            loadState.value = ArticleLoadState(
                isLoading = false,
                error = INVALID_ARTICLE_ID_MESSAGE,
            )
            return
        }

        viewModelScope.launchIO {
            loadState.update { it.copy(isLoading = true, error = null) }

            when (val result = newsRepository.getArticle(articleId)) {
                is Result.Success -> {
                    loadedArticle = result.data
                    loadState.value = ArticleLoadState(
                        isLoading = false,
                        article = result.data,
                    )
                }

                is Result.Error -> {
                    loadedArticle = null
                    loadState.value = ArticleLoadState(
                        isLoading = false,
                        error = result.throwable.message?.takeIf { message -> message.isNotBlank() }
                            ?: LOAD_ERROR_MESSAGE,
                    )
                }
            }
        }
    }

    private fun toggleBookmark() {
        val article = loadedArticle ?: return

        viewModelScope.launchIO {
            val result = if (article.id in latestBookmarkIds.value) {
                bookmarkRepository.removeBookmark(article.id)
            } else {
                bookmarkRepository.addBookmark(article)
            }
            if (result is Result.Error) {
                _flowSnackbarMessage.value = Once(AppUiStrings.BOOKMARK_FAILURE)
            }
        }
    }

    private fun shareArticle() {
        val article = loadedArticle ?: return
        _shareArticle.value = Once(
            ShareArticlePayload(
                title = article.title,
                url = article.url,
            ),
        )
    }

    private fun openInBrowser() {
        val article = loadedArticle ?: return
        _openInBrowser.value = Once(OpenUrlPayload(url = article.url))
    }

    private fun buildUiState(
        load: ArticleLoadState,
        isBookmarked: Boolean,
    ): ArticleDetailUiState {
        val article = load.article
        if (article == null) {
            return ArticleDetailUiState(
                isLoading = load.isLoading,
                error = load.error,
                isBookmarked = isBookmarked,
            )
        }

        return ArticleDetailUiState(
            isLoading = false,
            title = article.title,
            description = article.description,
            content = article.content,
            metadata = ArticleMetadataFormatter.format(article),
            categoryLabel = article.category.displayLabel().uppercase(),
            imageUrl = article.imageUrl,
            isBookmarked = isBookmarked,
            error = null,
        )
    }

    private data class ArticleLoadState(
        val isLoading: Boolean = true,
        val error: String? = null,
        val article: Article? = null,
    )

    private companion object {
        const val INVALID_ARTICLE_ID_MESSAGE = "Invalid article id"
        const val LOAD_ERROR_MESSAGE =
            "Unable to load this article. It may have been removed from your cache."
    }
}
