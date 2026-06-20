package com.app.newsdigest.domain.repository

import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Category
import com.app.newsdigest.domain.model.SyncResult
import com.app.newsdigest.support.Result
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun observeTopHeadlines(category: Category): Flow<List<Article>>

    suspend fun refreshTopHeadlines(category: Category): Result<Unit>

    suspend fun getArticle(id: String): Result<Article>

    suspend fun syncAndDetectNewArticles(): Result<SyncResult>
}
