package com.app.newsdigest.concurrency

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

object AppCoroutineScope : CoroutineScope {
    override val coroutineContext: CoroutineContext by lazy {
        AppDispatchers.MAIN + SupervisorJob()
    }
}
