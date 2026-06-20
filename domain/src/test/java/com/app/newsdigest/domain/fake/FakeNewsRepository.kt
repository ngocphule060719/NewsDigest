package com.app.newsdigest.domain.fake

import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Category
import com.app.newsdigest.domain.model.SyncResult
import com.app.newsdigest.domain.repository.NewsRepository
import com.app.newsdigest.support.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.time.Instant

class FakeNewsRepository : NewsRepository {
    private val articles = MutableStateFlow<List<Article>>(emptyList())
    private var refreshShouldFail = false
    private var syncNewCount = 0

    fun seed(articles: List<Article>) {
        this.articles.value = articles
    }

    fun setRefreshShouldFail(shouldFail: Boolean) {
        refreshShouldFail = shouldFail
    }

    fun setSyncNewCount(count: Int) {
        syncNewCount = count
    }

    override fun observeTopHeadlines(category: Category): Flow<List<Article>> {
        return articles.map { list -> list.filter { it.category == category } }
    }

    override suspend fun refreshTopHeadlines(category: Category): Result<Unit> {
        if (refreshShouldFail) {
            return Result.error(IllegalStateException("Refresh failed"))
        }
        return Result.success(Unit)
    }

    override suspend fun getArticle(id: String): Result<Article> {
        val article = articles.value.find { it.id == id }
            ?: return Result.error(NoSuchElementException("Article not found: $id"))
        return Result.success(article)
    }

    override suspend fun syncAndDetectNewArticles(): Result<SyncResult> {
        return Result.success(
            SyncResult(
                newArticleCount = syncNewCount,
                syncedAt = Instant.now(),
            ),
        )
    }
}
