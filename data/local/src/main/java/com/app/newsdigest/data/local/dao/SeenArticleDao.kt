package com.app.newsdigest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.newsdigest.data.local.entity.SeenArticleEntity

@Dao
interface SeenArticleDao {
    @Query("SELECT articleId FROM seen_articles")
    suspend fun getAllArticleIds(): List<String>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(entities: List<SeenArticleEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: SeenArticleEntity)

    @Query("SELECT COUNT(*) FROM seen_articles")
    suspend fun count(): Int
}
