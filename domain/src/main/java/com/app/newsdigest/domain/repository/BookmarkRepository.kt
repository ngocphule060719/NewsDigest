package com.app.newsdigest.domain.repository

import com.app.newsdigest.domain.model.Article
import com.app.newsdigest.domain.model.Bookmark
import com.app.newsdigest.support.Result
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun observeBookmarks(): Flow<List<Bookmark>>

    suspend fun addBookmark(article: Article): Result<Unit>

    suspend fun removeBookmark(articleId: String): Result<Unit>
}
