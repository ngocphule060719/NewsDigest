package com.app.newsdigest.presentation.articlelist

import com.app.newsdigest.domain.platform.ConnectivityMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeConnectivityMonitor(
    initialConnected: Boolean = true,
) : ConnectivityMonitor {
    private val connected = MutableStateFlow(initialConnected)

    override val isConnected: Flow<Boolean> = connected

    fun setConnected(value: Boolean) {
        connected.value = value
    }
}
