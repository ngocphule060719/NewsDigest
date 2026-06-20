package com.app.newsdigest.data.local.dao

import com.app.newsdigest.data.local.InMemoryDatabaseHelper
import com.app.newsdigest.data.local.entity.BookmarkEntity
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
class BookmarkDaoTest {
    private lateinit var bookmarkDao: BookmarkDao

    @Before
    fun setUp() {
        bookmarkDao = InMemoryDatabaseHelper.create().bookmarkDao()
    }

    @Test
    fun upsertAndObserve_returnsFullBookmark() = runTest {
        val bookmark = sampleBookmark(articleId = "b1", bookmarkedAt = 2_000L)
        bookmarkDao.upsert(bookmark)

        val observed = bookmarkDao.observeAll().first()

        assertEquals(1, observed.size)
        assertEquals(bookmark, observed.first())
    }

    @Test
    fun observeAll_sortsByBookmarkedAtDescending() = runTest {
        bookmarkDao.upsert(sampleBookmark(articleId = "older", bookmarkedAt = 1_000L))
        bookmarkDao.upsert(sampleBookmark(articleId = "newer", bookmarkedAt = 3_000L))

        val observed = bookmarkDao.observeAll().first()

        assertEquals(listOf("newer", "older"), observed.map { it.articleId })
    }

    @Test
    fun reUpsert_updatesExistingRow() = runTest {
        bookmarkDao.upsert(sampleBookmark(articleId = "b1", title = "Original"))
        bookmarkDao.upsert(sampleBookmark(articleId = "b1", title = "Updated"))

        val bookmark = bookmarkDao.getByArticleId("b1")

        assertEquals("Updated", bookmark?.title)
    }

    @Test
    fun deleteByArticleId_removesBookmark() = runTest {
        bookmarkDao.upsert(sampleBookmark(articleId = "b1"))
        bookmarkDao.deleteByArticleId("b1")

        val observed = bookmarkDao.observeAll().first()

        assertTrue(observed.isEmpty())
    }

    private fun sampleBookmark(
        articleId: String,
        title: String = "Title for $articleId",
        bookmarkedAt: Long = 1_000L,
    ): BookmarkEntity = BookmarkEntity(
        articleId = articleId,
        title = title,
        description = "Description",
        content = "Content",
        url = "https://example.com/$articleId",
        imageUrl = null,
        sourceName = "Source",
        publishedAt = Instant.parse("2026-06-20T10:00:00Z"),
        category = "technology",
        bookmarkedAt = bookmarkedAt,
    )
}
