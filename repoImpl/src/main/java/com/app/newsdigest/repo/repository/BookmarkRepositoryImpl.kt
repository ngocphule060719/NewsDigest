package com.app.newsdigest.repo.repository

import com.app.newsdigest.concurrency.AppDispatchers
import com.app.newsdigest.data.local.dao.BookmarkDao
import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Bookmark
import com.app.newsdigest.domain.repository.BookmarkRepository
import com.app.newsdigest.repo.mapper.toBookmarkEntity
import com.app.newsdigest.repo.mapper.toDomain
import com.app.newsdigest.support.Result
import com.app.newsdigest.support.ResultResolver
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    private val bookmarkDao: BookmarkDao,
    private val resultResolver: ResultResolver,
) : BookmarkRepository {

    override fun observeBookmarks(): Flow<List<Bookmark>> =
        bookmarkDao.observeAll()
            .map { entities -> entities.map { it.toDomain() } }
            .flowOn(AppDispatchers.IO)

    override suspend fun addBookmark(article: Article): Result<Unit> =
        resultResolver.resolve {
            withContext(AppDispatchers.IO) {
                bookmarkDao.upsert(article.toBookmarkEntity())
            }
        }

    override suspend fun removeBookmark(articleId: String): Result<Unit> =
        resultResolver.resolve {
            withContext(AppDispatchers.IO) {
                bookmarkDao.deleteByArticleId(articleId)
            }
        }
}
