package com.app.newsdigest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.newsdigest.data.local.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Query("SELECT * FROM bookmarks ORDER BY bookmarkedAt DESC")
    fun observeAll(): Flow<List<BookmarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(bookmark: BookmarkEntity)

    @Query("DELETE FROM bookmarks WHERE articleId = :articleId")
    suspend fun deleteByArticleId(articleId: String)

    @Query("SELECT * FROM bookmarks WHERE articleId = :articleId LIMIT 1")
    suspend fun getByArticleId(articleId: String): BookmarkEntity?
}
