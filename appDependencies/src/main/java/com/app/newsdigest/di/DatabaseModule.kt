package com.app.newsdigest.di

import android.content.Context
import androidx.room.Room
import com.app.newsdigest.data.local.dao.ArticleDao
import com.app.newsdigest.data.local.dao.BookmarkDao
import com.app.newsdigest.data.local.dao.SeenArticleDao
import com.app.newsdigest.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME,
        ).build()

    @Provides
    fun provideArticleDao(db: AppDatabase): ArticleDao = db.articleDao()

    @Provides
    fun provideBookmarkDao(db: AppDatabase): BookmarkDao = db.bookmarkDao()

    @Provides
    fun provideSeenArticleDao(db: AppDatabase): SeenArticleDao = db.seenArticleDao()
}
