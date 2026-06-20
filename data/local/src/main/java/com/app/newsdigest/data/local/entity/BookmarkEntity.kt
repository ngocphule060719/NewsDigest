package com.app.newsdigest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "bookmarks")
data class BookmarkEntity(
    @PrimaryKey val articleId: String,
    val title: String,
    val description: String?,
    val content: String?,
    val url: String,
    val imageUrl: String?,
    val sourceName: String,
    val publishedAt: Instant?,
    val category: String,
    val bookmarkedAt: Long,
)
