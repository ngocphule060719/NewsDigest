package com.app.newsdigest.di

import android.content.Context
import com.app.newsdigest.domain.platform.ConnectivityMonitor
import com.app.newsdigest.support.ResultResolver
import com.app.newsdigest.support.network.ConnectivityMonitorImpl
import com.app.newsdigest.support.notification.NewsNotificationHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SupportBindingsModule {

    @Binds
    @Singleton
    abstract fun bindConnectivityMonitor(impl: ConnectivityMonitorImpl): ConnectivityMonitor

    companion object {
        @Provides
        @Singleton
        fun provideResultResolver(): ResultResolver = ResultResolver()

        @Provides
        @Singleton
        fun provideNewsNotificationHelper(
            @ApplicationContext context: Context,
        ): NewsNotificationHelper = NewsNotificationHelper(context)
    }
}
