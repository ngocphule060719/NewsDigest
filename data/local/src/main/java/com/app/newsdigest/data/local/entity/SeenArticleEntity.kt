package com.app.newsdigest.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seen_articles")
data class SeenArticleEntity(
    @PrimaryKey val articleId: String,
    val seenAt: Long,
)
