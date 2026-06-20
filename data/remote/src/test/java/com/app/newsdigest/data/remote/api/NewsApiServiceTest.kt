package com.app.newsdigest.data.remote.api

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class NewsApiServiceTest {
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getTopHeadlines_deserializesResponseAndSendsApiKey() = runTest {
        val fixture = javaClass.classLoader
            ?.getResource("top_headlines_success.json")
            ?.readText()
            ?: error("Missing top_headlines_success.json fixture")

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(fixture),
        )

        val service = RemoteApiFactory.createNewsApiService(
            apiKey = "test-key",
            baseUrl = mockWebServer.url("/").toString(),
        )

        val response = service.getTopHeadlines(category = "technology")

        assertEquals("ok", response.status)
        assertEquals(2, response.articles.size)

        val first = response.articles.first()
        assertEquals("First headline title", first.title)
        assertEquals("https://example.com/article-1", first.url)
        assertEquals("TechCrunch", first.source.name)

        val second = response.articles[1]
        assertNull(second.description)
        assertNull(second.urlToImage)
        assertNull(second.publishedAt)

        val request = mockWebServer.takeRequest()
        assertEquals("test-key", request.getHeader("X-Api-Key"))
        assertEquals("us", request.requestUrl?.queryParameter("country"))
        assertEquals("technology", request.requestUrl?.queryParameter("category"))
        assertEquals("100", request.requestUrl?.queryParameter("pageSize"))
    }
}
