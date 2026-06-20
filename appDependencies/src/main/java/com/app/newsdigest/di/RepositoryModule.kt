package com.app.newsdigest.di

import com.app.newsdigest.domain.repository.BookmarkRepository
import com.app.newsdigest.domain.repository.NewsRepository
import com.app.newsdigest.repo.repository.BookmarkRepositoryImpl
import com.app.newsdigest.repo.repository.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository

    @Binds
    @Singleton
    abstract fun bindBookmarkRepository(impl: BookmarkRepositoryImpl): BookmarkRepository
}
