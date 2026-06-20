package com.app.newsdigest.repo.mapper

import com.app.newsdigest.data.local.entity.ArticleEntity
import com.app.newsdigest.domain.model.Article

fun ArticleEntity.toDomain(): Article =
    Article(
        id = id,
        title = title,
        description = description,
        content = content,
        url = url,
        imageUrl = imageUrl,
        sourceName = sourceName,
        publishedAt = publishedAt,
        category = category.toCategory(),
    )
