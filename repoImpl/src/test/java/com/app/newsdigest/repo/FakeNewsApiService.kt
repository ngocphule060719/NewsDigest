package com.app.newsdigest.repo

import com.app.newsdigest.data.remote.api.NewsApiService
import com.app.newsdigest.data.remote.dto.TopHeadlinesResponseDto

class FakeNewsApiService : NewsApiService {
    var response: TopHeadlinesResponseDto = TopHeadlinesResponseDto(
        status = "ok",
        totalResults = 0,
        articles = emptyList(),
    )
    var shouldThrow: Boolean = false
    var throwException: Exception = RuntimeException("API error")

    override suspend fun getTopHeadlines(
        country: String,
        category: String,
        pageSize: Int,
    ): TopHeadlinesResponseDto {
        if (shouldThrow) throw throwException
        return response
    }
}
