package com.app.newsdigest.di

import com.app.newsdigest.support.ResultResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SupportBindingsModule {

    @Provides
    @Singleton
    fun provideResultResolver(): ResultResolver = ResultResolver()
}
