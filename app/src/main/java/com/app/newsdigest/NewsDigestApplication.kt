package com.app.newsdigest

import android.app.Application
import com.app.newsdigest.concurrency.AppDispatchers
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsDigestApplication : Application() {

    init {
        AppDispatchers.initialize()
    }

    override fun onCreate() {
        super.onCreate()
        // SyncScheduler.schedule(this) — deferred to Phase 7 (:appSupport)
    }
}
