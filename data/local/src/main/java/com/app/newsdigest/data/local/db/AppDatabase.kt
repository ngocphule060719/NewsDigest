package com.app.newsdigest.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.newsdigest.data.local.dao.ArticleDao
import com.app.newsdigest.data.local.dao.BookmarkDao
import com.app.newsdigest.data.local.dao.SeenArticleDao
import com.app.newsdigest.data.local.entity.ArticleEntity
import com.app.newsdigest.data.local.entity.BookmarkEntity
import com.app.newsdigest.data.local.entity.SeenArticleEntity

@Database(
    entities = [
        ArticleEntity::class,
        BookmarkEntity::class,
        SeenArticleEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(InstantConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun bookmarkDao(): BookmarkDao
    abstract fun seenArticleDao(): SeenArticleDao

    companion object {
        const val DATABASE_NAME = "news_digest.db"
    }
}
