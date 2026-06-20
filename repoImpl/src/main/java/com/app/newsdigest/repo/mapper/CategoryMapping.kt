package com.app.newsdigest.repo.mapper

import com.app.newsdigest.domain.model.Category

fun String.toCategory(): Category =
    Category.entries.find { it.apiValue == this } ?: Category.GENERAL
