package com.app.newsdigest.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.newsdigest.concurrency.launchIO
import com.app.newsdigest.concurrency.toStateFlow
import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Bookmark
import com.app.newsdigest.domain.repository.BookmarkRepository
import com.app.newsdigest.presentation.articlelist.ArticleMetadataFormatter
import com.app.newsdigest.presentation.common.AppUiStrings
import com.app.newsdigest.support.Once
import com.app.newsdigest.support.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {

    private val _flowSnackbarMessage = MutableStateFlow(Once<String>(null))
    val flowSnackbarMessage: StateFlow<Once<String>> = _flowSnackbarMessage.asStateFlow()

    val uiState = bookmarkRepository.observeBookmarks()
        .map { bookmarks ->
            BookmarksUiState(
                isLoading = false,
                bookmarks = bookmarks.map { it.toUiState() },
            )
        }
        .toStateFlow(
            scope = viewModelScope,
            initialValue = BookmarksUiState(isLoading = true),
        )

    fun onIntent(intent: BookmarksIntent) {
        when (intent) {
            BookmarksIntent.LoadBookmarks -> Unit
            is BookmarksIntent.ToggleBookmark -> removeBookmark(intent.articleId)
            is BookmarksIntent.OpenArticle -> Unit
        }
    }

    private fun removeBookmark(articleId: String) {
        viewModelScope.launchIO {
            when (val result = bookmarkRepository.removeBookmark(articleId)) {
                is Result.Error -> {
                    _flowSnackbarMessage.value = Once(AppUiStrings.BOOKMARK_FAILURE)
                }

                is Result.Success -> Unit
            }
        }
    }

    private fun Bookmark.toUiState(): BookmarkUiState =
        BookmarkUiState(
            articleId = articleId,
            title = title,
            metadata = ArticleMetadataFormatter.format(sourceName, publishedAt),
            imageUrl = imageUrl,
            isBookmarked = true,
        )

    private fun Bookmark.toArticle(): Article =
        Article(
            id = articleId,
            title = title,
            description = description,
            content = content,
            url = url,
            imageUrl = imageUrl,
            sourceName = sourceName,
            publishedAt = publishedAt,
            category = category,
        )
}
