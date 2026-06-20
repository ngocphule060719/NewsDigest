package com.app.newsdigest.domain.repository

import com.app.newsdigest.domain.fake.FakeBookmarkRepository
import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Category
import com.app.newsdigest.support.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Instant

class FakeBookmarkRepositoryTest {

    private val repository = FakeBookmarkRepository()

    private fun sampleArticle(id: String, title: String = "Title $id") = Article(
        id = id,
        title = title,
        description = "Description",
        content = "Content",
        url = "https://example.com/$id",
        imageUrl = null,
        sourceName = "Source",
        publishedAt = Instant.parse("2026-01-01T00:00:00Z"),
        category = Category.GENERAL,
    )

    @Test
    fun addBookmark_observesMatchingFields() = runTest {
        val article = sampleArticle("1")
        repository.addBookmark(article)

        val bookmarks = repository.observeBookmarks().first()
        assertEquals(1, bookmarks.size)
        assertEquals("1", bookmarks.first().articleId)
        assertEquals("Title 1", bookmarks.first().title)
        assertEquals(article.url, bookmarks.first().url)
    }

    @Test
    fun multipleBookmarks_sortedNewestFirst() = runTest {
        repository.addBookmark(sampleArticle("1"))
        Thread.sleep(5)
        repository.addBookmark(sampleArticle("2"))

        val bookmarks = repository.observeBookmarks().first()
        assertEquals(2, bookmarks.size)
        assertEquals("2", bookmarks.first().articleId)
    }

    @Test
    fun reAddSameId_updatesTitleAndTimestamp() = runTest {
        repository.addBookmark(sampleArticle("1", title = "Original"))
        val firstBookmarkedAt = repository.currentBookmarks().first().bookmarkedAt
        Thread.sleep(5)
        repository.addBookmark(sampleArticle("1", title = "Updated"))

        val bookmarks = repository.observeBookmarks().first()
        assertEquals(1, bookmarks.size)
        assertEquals("Updated", bookmarks.first().title)
        assertTrue(bookmarks.first().bookmarkedAt >= firstBookmarkedAt)
    }

    @Test
    fun removeBookmark_removesFromList() = runTest {
        repository.addBookmark(sampleArticle("1"))
        repository.removeBookmark("1")

        assertTrue(repository.observeBookmarks().first().isEmpty())
    }

    @Test
    fun removeUnknownBookmark_returnsSuccess() = runTest {
        val result = repository.removeBookmark("unknown")

        assertTrue(result is Result.Success)
    }
}
