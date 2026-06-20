package com.app.newsdigest.domain.model

import java.time.Instant

data class SyncResult(
    val newArticleCount: Int,
    val syncedAt: Instant,
)
