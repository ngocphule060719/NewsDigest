package com.app.newsdigest.repo.mapper

import com.app.newsdigest.data.local.entity.ArticleEntity
import com.app.newsdigest.data.remote.dto.ArticleDto
import com.app.newsdigest.domain.model.Category

fun ArticleDto.toEntity(category: Category, cachedAt: Long): ArticleEntity =
    ArticleEntity(
        id = url.toArticleId(),
        title = title,
        description = description,
        content = content,
        url = url,
        imageUrl = urlToImage,
        sourceName = source.name,
        publishedAt = parseInstantOrNull(publishedAt),
        category = category.apiValue,
        cachedAt = cachedAt,
    )
