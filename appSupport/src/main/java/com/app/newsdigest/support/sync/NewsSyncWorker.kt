package com.app.newsdigest.support.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.newsdigest.domain.repository.NewsRepository
import com.app.newsdigest.support.notification.NewsNotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import androidx.work.ListenableWorker.Result as WorkResult
import com.app.newsdigest.support.Result as AppResult

@HiltWorker
class NewsSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val newsRepository: NewsRepository,
    private val notificationHelper: NewsNotificationHelper,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): WorkResult =
        when (val sync = newsRepository.syncAndDetectNewArticles()) {
            is AppResult.Success -> {
                if (sync.data.newArticleCount > 0) {
                    notificationHelper.showNewArticlesNotification(sync.data.newArticleCount)
                }
                WorkResult.success()
            }

            is AppResult.Error -> WorkResult.retry()
        }
}
