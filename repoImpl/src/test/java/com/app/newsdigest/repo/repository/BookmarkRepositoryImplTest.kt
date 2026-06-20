package com.app.newsdigest.repo.repository

import com.app.newsdigest.concurrency.AppDispatchers
import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Category
import com.app.newsdigest.repo.InMemoryDatabaseHelper
import com.app.newsdigest.support.Result
import com.app.newsdigest.support.ResultResolver
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.Instant

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class BookmarkRepositoryImplTest {
    private lateinit var repository: BookmarkRepositoryImpl

    @Before
    fun setUp() {
        AppDispatchers.initialize()
        val database = InMemoryDatabaseHelper.create()
        repository = BookmarkRepositoryImpl(
            bookmarkDao = database.bookmarkDao(),
            resultResolver = ResultResolver(),
        )
    }

    @Test
    fun addBookmark_observeBookmarks_emitsMatchingBookmark() = runTest {
        val article = sampleArticle(id = "article-1")

        val addResult = repository.addBookmark(article)
        assertTrue(addResult is Result.Success)

        val bookmarks = repository.observeBookmarks().first()

        assertEquals(1, bookmarks.size)
        assertEquals(article.id, bookmarks.first().articleId)
        assertEquals(article.title, bookmarks.first().title)
        assertEquals(article.url, bookmarks.first().url)
        assertEquals(article.category, bookmarks.first().category)
    }

    @Test
    fun removeBookmark_observeBookmarks_emitsEmpty() = runTest {
        val article = sampleArticle(id = "article-1")
        repository.addBookmark(article)

        val removeResult = repository.removeBookmark(article.id)
        assertTrue(removeResult is Result.Success)

        val bookmarks = repository.observeBookmarks().first()

        assertTrue(bookmarks.isEmpty())
    }

    @Test
    fun observeBookmarks_sortsNewestFirst() = runTest {
        repository.addBookmark(sampleArticle(id = "older"))
        Thread.sleep(5)
        repository.addBookmark(sampleArticle(id = "newer"))

        val bookmarks = repository.observeBookmarks().first()

        assertEquals(2, bookmarks.size)
        assertEquals("newer", bookmarks.first().articleId)
        assertEquals("older", bookmarks.last().articleId)
    }

    private fun sampleArticle(id: String, title: String = "Title for $id"): Article = Article(
        id = id,
        title = title,
        description = "Description",
        content = "Content",
        url = "https://example.com/$id",
        imageUrl = null,
        sourceName = "Source",
        publishedAt = Instant.parse("2026-06-20T10:00:00Z"),
        category = Category.TECHNOLOGY,
    )
}
