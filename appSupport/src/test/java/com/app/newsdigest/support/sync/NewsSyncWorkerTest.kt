package com.app.newsdigest.support.sync

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import com.app.newsdigest.concurrency.AppDispatchers
import com.app.newsdigest.support.Result
import com.app.newsdigest.support.fake.FakeNewsNotificationHelper
import com.app.newsdigest.support.fake.FakeNewsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [26])
class NewsSyncWorkerTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        AppDispatchers.initialize()
        context = ApplicationProvider.getApplicationContext()
        WorkManagerTestInitHelper.initializeTestWorkManager(context)
    }

    @Test
    fun doWork_successWithNewArticles_notifiesAndSucceeds() = runTest {
        val repository = FakeNewsRepository().apply { setSyncNewCount(3) }
        val notifier = FakeNewsNotificationHelper(context)

        val result = runWorker(repository, notifier)

        assertTrue(result is ListenableWorker.Result.Success)
        assertTrue(notifier.notifyCalled)
        assertEquals(3, notifier.lastPostedCount)
    }

    @Test
    fun doWork_successWithZeroNewArticles_doesNotNotify() = runTest {
        val repository = FakeNewsRepository().apply { setSyncNewCount(0) }
        val notifier = FakeNewsNotificationHelper(context)

        val result = runWorker(repository, notifier)

        assertTrue(result is ListenableWorker.Result.Success)
        assertFalse(notifier.notifyCalled)
        assertNull(notifier.lastPostedCount)
    }

    @Test
    fun doWork_syncError_retries() = runTest {
        val repository = FakeNewsRepository().apply {
            setSyncResult(Result.error(IllegalStateException("Sync failed")))
        }
        val notifier = FakeNewsNotificationHelper(context)

        val result = runWorker(repository, notifier)

        assertTrue(result is ListenableWorker.Result.Retry)
        assertFalse(notifier.notifyCalled)
    }

    private suspend fun runWorker(
        repository: FakeNewsRepository,
        notifier: FakeNewsNotificationHelper,
    ): ListenableWorker.Result {
        val worker = TestListenableWorkerBuilder.from(context, NewsSyncWorker::class.java)
            .setWorkerFactory(
                object : androidx.work.WorkerFactory() {
                    override fun createWorker(
                        appContext: Context,
                        workerClassName: String,
                        workerParameters: androidx.work.WorkerParameters,
                    ): ListenableWorker? {
                        if (workerClassName != NewsSyncWorker::class.java.name) {
                            return null
                        }
                        return NewsSyncWorker(appContext, workerParameters, repository, notifier)
                    }
                },
            )
            .build() as NewsSyncWorker
        return worker.doWork()
    }
}
