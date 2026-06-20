package com.app.newsdigest.repo.mapper

import com.app.newsdigest.data.local.entity.BookmarkEntity
import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Bookmark
import java.time.Instant

fun Article.toBookmarkEntity(): BookmarkEntity =
    BookmarkEntity(
        articleId = id,
        title = title,
        description = description,
        content = content,
        url = url,
        imageUrl = imageUrl,
        sourceName = sourceName,
        publishedAt = publishedAt,
        category = category.apiValue,
        bookmarkedAt = System.currentTimeMillis(),
    )

fun BookmarkEntity.toDomain(): Bookmark =
    Bookmark(
        articleId = articleId,
        title = title,
        description = description,
        content = content,
        url = url,
        imageUrl = imageUrl,
        sourceName = sourceName,
        publishedAt = publishedAt,
        category = category.toCategory(),
        bookmarkedAt = Instant.ofEpochMilli(bookmarkedAt),
    )
