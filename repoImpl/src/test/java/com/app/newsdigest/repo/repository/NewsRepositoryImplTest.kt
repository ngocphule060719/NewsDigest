package com.app.newsdigest.repo.repository

import com.app.newsdigest.concurrency.AppDispatchers
import com.app.newsdigest.data.local.db.AppDatabase
import com.app.newsdigest.data.remote.dto.ArticleDto
import com.app.newsdigest.data.remote.dto.SourceDto
import com.app.newsdigest.data.remote.dto.TopHeadlinesResponseDto
import com.app.newsdigest.domain.model.Category
import com.app.newsdigest.repo.FakeNewsApiService
import com.app.newsdigest.repo.InMemoryDatabaseHelper
import com.app.newsdigest.repo.mapper.toArticleId
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

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class NewsRepositoryImplTest {

    private lateinit var db: AppDatabase
    private lateinit var fakeApi: FakeNewsApiService
    private lateinit var repository: NewsRepositoryImpl

    @Before
    fun setUp() {
        AppDispatchers.initialize()
        db = InMemoryDatabaseHelper.create()
        fakeApi = FakeNewsApiService()
        repository = NewsRepositoryImpl(
            newsApiService = fakeApi,
            articleDao = db.articleDao(),
            seenArticleDao = db.seenArticleDao(),
            resultResolver = ResultResolver(),
        )
    }

    @Test
    fun refreshTopHeadlines_upsertsArticlesIntoRoom() = runTest {
        fakeApi.response = twoArticleResponse()

        val result = repository.refreshTopHeadlines(Category.TECHNOLOGY)

        assertTrue(result is Result.Success)
        val id1 = "https://example.com/article-1".toArticleId()
        val id2 = "https://example.com/article-2".toArticleId()
        assertEquals(id1, db.articleDao().getById(id1)?.id)
        assertEquals(id2, db.articleDao().getById(id2)?.id)
        assertEquals("technology", db.articleDao().getById(id1)?.category)
    }

    @Test
    fun observeTopHeadlines_emitsDomainModels() = runTest {
        fakeApi.response = twoArticleResponse()
        repository.refreshTopHeadlines(Category.TECHNOLOGY)

        val headlines = repository.observeTopHeadlines(Category.TECHNOLOGY).first()

        assertEquals(2, headlines.size)
        assertEquals("First headline title", headlines[0].title)
        assertEquals(Category.TECHNOLOGY, headlines[0].category)
    }

    @Test
    fun refreshTopHeadlines_replacesCategoryCache() = runTest {
        fakeApi.response = twoArticleResponse()
        repository.refreshTopHeadlines(Category.TECHNOLOGY)

        fakeApi.response = TopHeadlinesResponseDto(
            status = "ok",
            totalResults = 1,
            articles = listOf(
                sampleDto(
                    title = "Only article",
                    url = "https://example.com/only",
                ),
            ),
        )
        repository.refreshTopHeadlines(Category.TECHNOLOGY)

        val headlines = repository.observeTopHeadlines(Category.TECHNOLOGY).first()

        assertEquals(1, headlines.size)
        assertEquals("Only article", headlines.first().title)
    }

    @Test
    fun getArticle_knownId_returnsSuccess() = runTest {
        fakeApi.response = twoArticleResponse()
        repository.refreshTopHeadlines(Category.TECHNOLOGY)
        val id = "https://example.com/article-1".toArticleId()

        val result = repository.getArticle(id)

        assertTrue(result is Result.Success)
        assertEquals(id, (result as Result.Success).data.id)
    }

    @Test
    fun getArticle_unknownId_returnsError() = runTest {
        val result = repository.getArticle("missing-id")

        assertTrue(result is Result.Error)
    }

    @Test
    fun syncAndDetectNewArticles_countsNewArticlesAndIsIdempotent() = runTest {
        fakeApi.response = twoArticleResponse()

        val first = repository.syncAndDetectNewArticles()
        val second = repository.syncAndDetectNewArticles()

        assertTrue(first is Result.Success)
        assertEquals(2, (first as Result.Success).data.newArticleCount)
        assertTrue(second is Result.Success)
        assertEquals(0, (second as Result.Success).data.newArticleCount)
        assertEquals(2, db.seenArticleDao().count())
    }

    @Test
    fun refreshTopHeadlines_whenApiFails_preservesExistingCache() = runTest {
        fakeApi.response = twoArticleResponse()
        repository.refreshTopHeadlines(Category.TECHNOLOGY)
        fakeApi.shouldThrow = true

        val result = repository.refreshTopHeadlines(Category.TECHNOLOGY)

        assertTrue(result is Result.Error)
        assertEquals(2, repository.observeTopHeadlines(Category.TECHNOLOGY).first().size)
    }

    private fun twoArticleResponse() = TopHeadlinesResponseDto(
        status = "ok",
        totalResults = 2,
        articles = listOf(
            sampleDto(
                title = "First headline title",
                url = "https://example.com/article-1",
                description = "First article description",
                urlToImage = "https://example.com/image-1.jpg",
                publishedAt = "2026-06-20T10:00:00Z",
                content = "First article content",
                sourceName = "TechCrunch",
            ),
            sampleDto(
                title = "Second headline title",
                url = "https://example.com/article-2",
                sourceName = "BBC News",
            ),
        ),
    )

    private fun sampleDto(
        title: String,
        url: String,
        description: String? = null,
        urlToImage: String? = null,
        publishedAt: String? = null,
        content: String? = null,
        sourceName: String = "Source",
    ) = ArticleDto(
        source = SourceDto(name = sourceName),
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
    )
}
