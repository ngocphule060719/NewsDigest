package com.app.newsdigest.presentation.articlelist

import com.app.newsdigest.domain.model.Category

fun Category.displayLabel(): String = when (this) {
    Category.GENERAL -> "General"
    Category.BUSINESS -> "Business"
    Category.ENTERTAINMENT -> "Entertainment"
    Category.HEALTH -> "Health"
    Category.SCIENCE -> "Science"
    Category.SPORTS -> "Sports"
    Category.TECHNOLOGY -> "Technology"
}
