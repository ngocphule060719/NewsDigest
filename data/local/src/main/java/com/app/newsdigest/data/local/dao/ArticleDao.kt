package com.app.newsdigest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.newsdigest.data.local.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles WHERE category = :category")
    fun observeByCategory(category: String): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articles WHERE id = :id LIMIT 1")
    suspend fun getById(id: String): ArticleEntity?

    @Query("DELETE FROM articles WHERE category = :category")
    suspend fun deleteByCategory(category: String)
}
