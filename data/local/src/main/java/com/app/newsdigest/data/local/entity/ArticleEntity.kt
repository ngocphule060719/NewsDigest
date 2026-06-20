package com.app.newsdigest.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "articles",
    indices = [Index(value = ["category"])],
)
data class ArticleEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val content: String?,
    val url: String,
    val imageUrl: String?,
    val sourceName: String,
    val publishedAt: Instant?,
    val category: String,
    val cachedAt: Long,
)
