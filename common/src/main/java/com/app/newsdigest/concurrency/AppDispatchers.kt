package com.app.newsdigest.concurrency

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newFixedThreadPoolContext

class AppDispatchers(
    private val ioDispatcher: CoroutineDispatcher,
    private val mainDispatcher: CoroutineDispatcher,
) {

    companion object {
        private val AVAILABLE_PROCESSOR = Runtime.getRuntime().availableProcessors() * 2

        private lateinit var INSTANCE: AppDispatchers

        fun initialize() {
            if (Companion::INSTANCE.isInitialized) return

            INSTANCE = AppDispatchers(
                newFixedThreadPoolContext(AVAILABLE_PROCESSOR, "NewsDigest-IoDispatcher"),
                Dispatchers.Main,
            )
        }

        val IO: CoroutineDispatcher get() = INSTANCE.ioDispatcher
        val MAIN: CoroutineDispatcher get() = INSTANCE.mainDispatcher
    }
}
