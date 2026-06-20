package com.app.newsdigest.concurrency

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

class TimeLock(
    private val lockTime: Long,
) {
    private val lockCounter = AtomicLong(0)
    private val lockJobRef = AtomicReference<Job?>(null)

    fun lock() {
        lockCounter.set(0)

        lockJobRef.updateAndGet { currentJob ->
            if (currentJob?.isActive == true) {
                currentJob
            } else {
                AppCoroutineScope.launchIO {
                    while (lockCounter.getAndAdd(10) < lockTime) {
                        delay(10)
                    }
                    lockJobRef.set(null)
                }
            }
        }
    }

    suspend fun await() {
        lockJobRef.get()?.let { job ->
            if (job.isActive) job.join()
        }
    }
}
