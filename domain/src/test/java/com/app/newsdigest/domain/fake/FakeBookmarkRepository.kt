package com.app.newsdigest.domain.fake

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

    fun currentBookmarks(): List<Bookmark> = bookmarks.value.values.toList()

    override fun observeBookmarks(): Flow<List<Bookmark>> {
        return bookmarks.map { map ->
            map.values.sortedByDescending { it.bookmarkedAt }
        }
    }

    override suspend fun addBookmark(article: Article): Result<Unit> {
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
        bookmarks.value = bookmarks.value - articleId
        return Result.success(Unit)
    }
}
