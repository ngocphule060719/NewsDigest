package com.app.newsdigest.presentation.articlelist

import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Category
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.time.Instant

class ArticleMetadataFormatterTest {

    private val now = Instant.parse("2026-06-20T12:00:00Z")

    @Test
    fun formatRelativeTime_justNow() {
        val publishedAt = now.minusSeconds(30)

        val result = ArticleMetadataFormatter.formatRelativeTime(publishedAt, now)

        assertEquals("just now", result)
    }

    @Test
    fun formatRelativeTime_minutes() {
        val publishedAt = now.minusSeconds(5 * 60)

        val result = ArticleMetadataFormatter.formatRelativeTime(publishedAt, now)

        assertEquals("5m ago", result)
    }

    @Test
    fun formatRelativeTime_hours() {
        val publishedAt = now.minusSeconds(3 * 3_600)

        val result = ArticleMetadataFormatter.formatRelativeTime(publishedAt, now)

        assertEquals("3h ago", result)
    }

    @Test
    fun formatRelativeTime_days() {
        val publishedAt = now.minusSeconds(2 * 86_400)

        val result = ArticleMetadataFormatter.formatRelativeTime(publishedAt, now)

        assertEquals("2d ago", result)
    }

    @Test
    fun format_includesSource() {
        val article = sampleArticle(publishedAt = now.minusSeconds(60))

        val result = ArticleMetadataFormatter.format(article, now)

        assertTrue(result.startsWith("Financial Times · "))
        assertTrue(result.contains("1m ago"))
    }

    @Test
    fun format_missingPublishedAt() {
        val result = ArticleMetadataFormatter.format(
            sourceName = "Reuters",
            publishedAt = null,
            now = now,
        )

        assertEquals("Reuters", result)
    }

    private fun sampleArticle(publishedAt: Instant?) = Article(
        id = "article-1",
        title = "Title",
        description = "Description",
        content = "Content",
        url = "https://example.com/article-1",
        imageUrl = null,
        sourceName = "Financial Times",
        publishedAt = publishedAt,
        category = Category.GENERAL,
    )
}
