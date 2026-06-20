package com.app.newsdigest.repo.mapper

import com.app.newsdigest.log.Logger
import java.time.Instant

fun parseInstantOrNull(value: String?): Instant? {
    if (value == null) return null
    return try {
        Instant.parse(value)
    } catch (e: Exception) {
        Logger.w("InstantParsing", "Failed to parse publishedAt: $value", e)
        null
    }
}
