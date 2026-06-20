package com.app.newsdigest

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.app.newsdigest.concurrency.AppDispatchers
import com.app.newsdigest.support.notification.NewsNotificationHelper
import com.app.newsdigest.support.sync.SyncScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NewsDigestApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    @Inject
    lateinit var notificationHelper: NewsNotificationHelper

    init {
        AppDispatchers.initialize()
    }

    override fun onCreate() {
        super.onCreate()
        notificationHelper.ensureChannelCreated()
        SyncScheduler.schedule(this)
    }

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}
