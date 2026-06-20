package com.app.newsdigest.repo.repository

import com.app.newsdigest.concurrency.AppDispatchers
import com.app.newsdigest.data.local.dao.ArticleDao
import com.app.newsdigest.data.local.dao.SeenArticleDao
import com.app.newsdigest.data.local.entity.SeenArticleEntity
import com.app.newsdigest.data.remote.api.NewsApiService
import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Category
import com.app.newsdigest.domain.model.SyncResult
import com.app.newsdigest.domain.repository.NewsRepository
import com.app.newsdigest.repo.mapper.toDomain
import com.app.newsdigest.repo.mapper.toEntity
import com.app.newsdigest.support.Result
import com.app.newsdigest.support.ResultResolver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService,
    private val articleDao: ArticleDao,
    private val seenArticleDao: SeenArticleDao,
    private val resultResolver: ResultResolver,
) : NewsRepository {

    override fun observeTopHeadlines(category: Category): Flow<List<Article>> =
        articleDao.observeByCategory(category.apiValue)
            .map { entities -> entities.map { it.toDomain() } }
            .flowOn(AppDispatchers.IO)

    override suspend fun refreshTopHeadlines(category: Category): Result<Unit> =
        resultResolver.resolve {
            withContext(AppDispatchers.IO) {
                val response = newsApiService.getTopHeadlines(category = category.apiValue)
                val cachedAt = System.currentTimeMillis()
                val entities = response.articles.map { dto ->
                    dto.toEntity(category, cachedAt = cachedAt)
                }
                articleDao.deleteByCategory(category.apiValue)
                articleDao.upsertAll(entities)
            }
        }

    override suspend fun getArticle(id: String): Result<Article> =
        resultResolver.resolve {
            withContext(AppDispatchers.IO) {
                articleDao.getById(id)?.toDomain()
                    ?: throw NoSuchElementException("Article not found: $id")
            }
        }

    override suspend fun syncAndDetectNewArticles(): Result<SyncResult> =
        resultResolver.resolve {
            withContext(AppDispatchers.IO) {
                val category = Category.GENERAL
                val response = newsApiService.getTopHeadlines(category = category.apiValue)
                val cachedAt = System.currentTimeMillis()
                val entities = response.articles.map { dto ->
                    dto.toEntity(category, cachedAt = cachedAt)
                }
                articleDao.upsertAll(entities)

                val fetchedIds = entities.map { it.id }
                val existingSeen = seenArticleDao.getAllArticleIds().toSet()
                val newIds = fetchedIds.filter { it !in existingSeen }
                val now = System.currentTimeMillis()

                seenArticleDao.insertAll(
                    newIds.map { articleId ->
                        SeenArticleEntity(articleId = articleId, seenAt = now)
                    },
                )

                SyncResult(
                    newArticleCount = newIds.size,
                    syncedAt = Instant.now(),
                )
            }
        }
}
