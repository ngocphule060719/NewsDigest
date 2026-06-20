package com.app.newsdigest.presentation.articlelist

import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Bookmark
import com.app.newsdigest.domain.repository.BookmarkRepository
import com.app.newsdigest.support.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.time.Instant

class FakeBookmarkRepository : BookmarkRepository {
    private val bookmarks = MutableStateFlow<Map<String, Bookmark>>(emptyMap())
    private var addShouldFail = false
    private var removeShouldFail = false

    fun currentBookmarks(): List<Bookmark> = bookmarks.value.values.toList()

    fun setAddShouldFail(shouldFail: Boolean) {
        addShouldFail = shouldFail
    }

    fun setRemoveShouldFail(shouldFail: Boolean) {
        removeShouldFail = shouldFail
    }

    override fun observeBookmarks(): Flow<List<Bookmark>> {
        return bookmarks.map { map ->
            map.values.sortedByDescending { it.bookmarkedAt }
        }
    }

    override suspend fun addBookmark(article: Article): Result<Unit> {
        if (addShouldFail) {
            return Result.error(IllegalStateException("Add bookmark failed"))
        }
        val bookmark = Bookmark(
            articleId = article.id,
            title = article.title,
            description = article.description,
            content = article.content,
            url = article.url,
            imageUrl = article.imageUrl,
            sourceName = article.sourceName,
            publishedAt = article.publishedAt,
            category = article.category,
            bookmarkedAt = Instant.now(),
        )
        bookmarks.value = bookmarks.value + (article.id to bookmark)
        return Result.success(Unit)
    }

    override suspend fun removeBookmark(articleId: String): Result<Unit> {
        if (removeShouldFail) {
            return Result.error(IllegalStateException("Remove bookmark failed"))
        }
        bookmarks.value = bookmarks.value - articleId
        return Result.success(Unit)
    }
}
