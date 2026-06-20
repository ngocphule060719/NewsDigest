package com.app.newsdigest.data.remote.api

import com.app.newsdigest.data.remote.dto.TopHeadlinesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("category") category: String,
        @Query("pageSize") pageSize: Int = 100,
    ): TopHeadlinesResponseDto
}
