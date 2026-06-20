package com.app.newsdigest.support.fake

import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Category
import com.app.newsdigest.domain.model.SyncResult
import com.app.newsdigest.domain.repository.NewsRepository
import com.app.newsdigest.support.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.Instant

class FakeNewsRepository : NewsRepository {
    private var syncResult: Result<SyncResult> = Result.success(
        SyncResult(newArticleCount = 0, syncedAt = Instant.EPOCH),
    )

    fun setSyncResult(result: Result<SyncResult>) {
        syncResult = result
    }

    fun setSyncNewCount(count: Int) {
        syncResult = Result.success(
            SyncResult(newArticleCount = count, syncedAt = Instant.EPOCH),
        )
    }

    override fun observeTopHeadlines(category: Category): Flow<List<Article>> = flowOf(emptyList())

    override suspend fun refreshTopHeadlines(category: Category): Result<Unit> =
        Result.success(Unit)

    override suspend fun getArticle(id: String): Result<Article> =
        Result.error(NoSuchElementException("Article not found: $id"))

    override suspend fun syncAndDetectNewArticles(): Result<SyncResult> = syncResult
}
