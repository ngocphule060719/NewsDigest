package com.app.newsdigest.support.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.app.newsdigest.concurrency.switchMain
import com.app.newsdigest.concurrency.syncOnMain
import com.app.newsdigest.domain.platform.ConnectivityMonitor
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectivityMonitorImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ConnectivityMonitor {

    override val isConnected: Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService(ConnectivityManager::class.java)
            ?: run {
                trySend(false)
                close()
                return@callbackFlow
            }
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(connectivityManager.isCurrentlyConnected())
            }

            override fun onLost(network: Network) {
                trySend(connectivityManager.isCurrentlyConnected())
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities,
            ) {
                trySend(connectivityManager.isCurrentlyConnected())
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        switchMain {
            connectivityManager.registerNetworkCallback(request, callback)
            trySend(connectivityManager.isCurrentlyConnected())
        }

        awaitClose {
            syncOnMain {
                runCatching { connectivityManager.unregisterNetworkCallback(callback) }
            }
        }
    }.distinctUntilChanged()
}

private fun ConnectivityManager.isCurrentlyConnected(): Boolean {
    val network = activeNetwork ?: return false
    val capabilities = getNetworkCapabilities(network) ?: return false
    // INTERNET + NOT_SUSPENDED is enough for offline-banner UX. Requiring
    // NET_CAPABILITY_VALIDATED keeps the feed in offline (grey/hero) mode on
    // emulator and right after Wi‑Fi join before validation completes.
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_SUSPENDED)
}
