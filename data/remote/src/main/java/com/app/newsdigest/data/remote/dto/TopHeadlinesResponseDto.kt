package com.app.newsdigest.data.remote.dto

data class TopHeadlinesResponseDto(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>,
)
