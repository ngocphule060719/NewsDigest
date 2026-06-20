package com.app.newsdigest.support.fake

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.app.newsdigest.support.notification.NewsNotificationHelper

class FakeNewsNotificationHelper(
    context: Context = ApplicationProvider.getApplicationContext(),
) : NewsNotificationHelper(context) {

    var lastPostedCount: Int? = null
        private set

    var notifyCalled: Boolean = false
        private set

    override fun showNewArticlesNotification(newArticleCount: Int) {
        notifyCalled = true
        lastPostedCount = newArticleCount
    }
}
