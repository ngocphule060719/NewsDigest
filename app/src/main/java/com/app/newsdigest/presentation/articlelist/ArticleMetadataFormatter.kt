package com.app.newsdigest.presentation.articlelist

import com.app.newsdigest.domain.model.Article
import java.time.Duration
import java.time.Instant

private const val METADATA_SEPARATOR = " · "

object ArticleMetadataFormatter {

    fun format(article: Article, now: Instant = Instant.now()): String =
        format(sourceName = article.sourceName, publishedAt = article.publishedAt, now = now)

    fun format(sourceName: String, publishedAt: Instant?, now: Instant = Instant.now()): String {
        val source = sourceName.trim()
        val relativeTime = publishedAt?.let { formatRelativeTime(it, now) }

        return when {
            source.isNotEmpty() && relativeTime != null -> source + METADATA_SEPARATOR + relativeTime
            source.isNotEmpty() -> source
            relativeTime != null -> relativeTime
            else -> ""
        }
    }

    fun formatRelativeTime(publishedAt: Instant, now: Instant = Instant.now()): String {
        val elapsedSeconds = Duration.between(publishedAt, now).seconds.coerceAtLeast(0)

        return when {
            elapsedSeconds < 60 -> "just now"
            elapsedSeconds < 3_600 -> "${elapsedSeconds / 60}m ago"
            elapsedSeconds < 86_400 -> "${elapsedSeconds / 3_600}h ago"
            elapsedSeconds < 604_800 -> "${elapsedSeconds / 86_400}d ago"
            else -> "${elapsedSeconds / 604_800}w ago"
        }
    }
}
