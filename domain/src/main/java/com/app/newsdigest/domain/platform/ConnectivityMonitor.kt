package com.app.newsdigest.domain.platform

import kotlinx.coroutines.flow.Flow

interface ConnectivityMonitor {
    val isConnected: Flow<Boolean>
}
