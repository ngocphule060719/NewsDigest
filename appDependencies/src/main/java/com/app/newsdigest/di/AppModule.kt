package com.app.newsdigest.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // Phase 3: NetworkModule, DatabaseModule, RepositoryModule, SupportModule, WorkerModule
}
