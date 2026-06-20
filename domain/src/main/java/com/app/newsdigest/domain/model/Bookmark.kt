package com.app.newsdigest.domain.model

import java.time.Instant

data class Bookmark(
    val articleId: String,
    val title: String,
    val description: String?,
    val content: String?,
    val url: String,
    val imageUrl: String?,
    val sourceName: String,
    val publishedAt: Instant?,
    val category: Category,
    val bookmarkedAt: Instant,
)
