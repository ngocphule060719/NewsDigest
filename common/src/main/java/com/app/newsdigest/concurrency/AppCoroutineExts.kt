package com.app.newsdigest.concurrency

import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun AppCoroutineScope.createSubAppScope(): CoroutineScope {
    return CoroutineScope(coroutineContext + SupervisorJob())
}

fun CoroutineScope.launchIO(block: suspend CoroutineScope.() -> Unit): Job {
    return launch(AppDispatchers.IO, block = block)
}

fun CoroutineScope.launchMain(block: suspend CoroutineScope.() -> Unit): Job {
    return launch(AppDispatchers.MAIN, block = block)
}

suspend fun <T> switchIO(block: suspend CoroutineScope.() -> T): T {
    return withContext(AppDispatchers.IO, block = block)
}

suspend fun <T> switchMain(block: suspend CoroutineScope.() -> T): T {
    return withContext(AppDispatchers.MAIN, block = block)
}

fun <T> Flow<T>.toAppStateFlow(
    initialValue: T,
    sharingStarted: SharingStarted = SharingStarted.Eagerly,
): StateFlow<T> {
    return stateIn(AppCoroutineScope, sharingStarted, initialValue)
}

fun <T> Flow<T>.toStateFlow(
    scope: CoroutineScope,
    initialValue: T,
    sharingStarted: SharingStarted = SharingStarted.Eagerly,
): StateFlow<T> {
    return stateIn(scope, sharingStarted, initialValue)
}

fun isMainThread(): Boolean {
    return Looper.myLooper() == Looper.getMainLooper()
}

fun <T> syncOnMain(block: () -> T): T {
    return if (!isMainThread()) {
        runBlocking(AppDispatchers.MAIN) { block() }
    } else {
        block()
    }
}
