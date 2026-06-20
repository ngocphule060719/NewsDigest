package com.app.newsdigest.data.local.dao

import app.cash.turbine.test
import com.app.newsdigest.data.local.InMemoryDatabaseHelper
import com.app.newsdigest.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.Instant

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class ArticleDaoTest {
    private lateinit var articleDao: ArticleDao

    @Before
    fun setUp() {
        articleDao = InMemoryDatabaseHelper.create().articleDao()
    }

    @After
    fun tearDown() {
        // In-memory DB is discarded when references go out of scope.
    }

    @Test
    fun upsertAndObserve_returnsMatchingArticles() = runTest {
        val articles = listOf(
            sampleArticle(id = "a1", category = "technology"),
            sampleArticle(id = "a2", category = "technology"),
        )
        articleDao.upsertAll(articles)

        val observed = articleDao.observeByCategory("technology").first()

        assertEquals(2, observed.size)
        assertEquals(setOf("a1", "a2"), observed.map { it.id }.toSet())
    }

    @Test
    fun upsertReplace_updatesExistingRow() = runTest {
        articleDao.upsertAll(listOf(sampleArticle(id = "a1", title = "Original")))
        articleDao.upsertAll(listOf(sampleArticle(id = "a1", title = "Updated")))

        val article = articleDao.getById("a1")

        assertEquals("Updated", article?.title)
    }

    @Test
    fun observeByCategory_filtersByCategory() = runTest {
        articleDao.upsertAll(
            listOf(
                sampleArticle(id = "g1", category = "general"),
                sampleArticle(id = "s1", category = "sports"),
            ),
        )

        val general = articleDao.observeByCategory("general").first()
        val sports = articleDao.observeByCategory("sports").first()

        assertEquals(1, general.size)
        assertEquals("g1", general.first().id)
        assertEquals(1, sports.size)
        assertEquals("s1", sports.first().id)
    }

    @Test
    fun getById_unknownId_returnsNull() = runTest {
        assertNull(articleDao.getById("missing"))
    }

    @Test
    fun deleteByCategory_removesOnlyMatchingRows() = runTest {
        articleDao.upsertAll(
            listOf(
                sampleArticle(id = "g1", category = "general"),
                sampleArticle(id = "t1", category = "technology"),
            ),
        )

        articleDao.deleteByCategory("technology")

        assertNull(articleDao.getById("t1"))
        assertNotNull(articleDao.getById("g1"))
    }

    @Test
    fun observeByCategory_emitsUpdatesOnUpsert() = runTest {
        articleDao.observeByCategory("technology").test {
            assertEquals(0, awaitItem().size)

            articleDao.upsertAll(listOf(sampleArticle(id = "t1", category = "technology")))

            val updated = awaitItem()
            assertEquals(1, updated.size)
            assertEquals("t1", updated.first().id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun sampleArticle(
        id: String,
        title: String = "Title for $id",
        category: String = "technology",
    ): ArticleEntity = ArticleEntity(
        id = id,
        title = title,
        description = "Description",
        content = "Content",
        url = "https://example.com/$id",
        imageUrl = null,
        sourceName = "Source",
        publishedAt = Instant.parse("2026-06-20T10:00:00Z"),
        category = category,
        cachedAt = 1_000L,
    )
}
