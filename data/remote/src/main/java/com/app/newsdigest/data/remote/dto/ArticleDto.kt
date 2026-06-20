package com.app.newsdigest.data.remote.dto

data class ArticleDto(
    val source: SourceDto,
    val author: String? = null,
    val title: String,
    val description: String? = null,
    val url: String,
    val urlToImage: String? = null,
    val publishedAt: String? = null,
    val content: String? = null,
)
