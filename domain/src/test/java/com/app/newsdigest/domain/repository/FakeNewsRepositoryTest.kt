package com.app.newsdigest.domain.repository

import com.app.newsdigest.domain.fake.FakeNewsRepository
import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Category
import com.app.newsdigest.support.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Instant

class FakeNewsRepositoryTest {

    private val repository = FakeNewsRepository()

    private fun sampleArticle(id: String, category: Category = Category.GENERAL) = Article(
        id = id,
        title = "Title $id",
        description = "Description $id",
        content = "Content $id",
        url = "https://example.com/$id",
        imageUrl = "https://example.com/$id.jpg",
        sourceName = "Source",
        publishedAt = Instant.parse("2026-01-01T00:00:00Z"),
        category = category,
    )

    @Test
    fun seedAndObserve_returnsSeededArticles() = runTest {
        val articles = listOf(
            sampleArticle("1"),
            sampleArticle("2", Category.TECHNOLOGY),
        )
        repository.seed(articles)

        val general = repository.observeTopHeadlines(Category.GENERAL).first()
        assertEquals(1, general.size)
        assertEquals("1", general.first().id)

        val tech = repository.observeTopHeadlines(Category.TECHNOLOGY).first()
        assertEquals(1, tech.size)
        assertEquals("2", tech.first().id)
    }

    @Test
    fun refreshWhenConfiguredToFail_returnsError() = runTest {
        repository.seed(listOf(sampleArticle("1")))
        repository.setRefreshShouldFail(true)

        val result = repository.refreshTopHeadlines(Category.GENERAL)

        assertTrue(result is Result.Error)
        assertEquals(1, repository.observeTopHeadlines(Category.GENERAL).first().size)
    }

    @Test
    fun refreshWhenSuccessful_returnsSuccess() = runTest {
        repository.setRefreshShouldFail(false)

        val result = repository.refreshTopHeadlines(Category.GENERAL)

        assertTrue(result is Result.Success)
    }

    @Test
    fun getArticle_whenKnown_returnsSuccess() = runTest {
        repository.seed(listOf(sampleArticle("42")))

        val result = repository.getArticle("42")

        assertTrue(result is Result.Success)
        assertEquals("42", (result as Result.Success).data.id)
    }

    @Test
    fun getArticle_whenMissing_returnsError() = runTest {
        val result = repository.getArticle("missing")

        assertTrue(result is Result.Error)
    }

    @Test
    fun syncAndDetectNewArticles_returnsConfiguredCount() = runTest {
        repository.setSyncNewCount(3)

        val result = repository.syncAndDetectNewArticles()

        assertTrue(result is Result.Success)
        assertEquals(3, (result as Result.Success).data.newArticleCount)
    }
}
