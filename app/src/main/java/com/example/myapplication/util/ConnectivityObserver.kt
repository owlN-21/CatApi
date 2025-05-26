package com.dots.restapp.util

import android.content.Context
import android.net.*
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*

enum class NetworkStatus { Available, Unavailable }

fun observeNetwork(context: Context): Flow<NetworkStatus> = callbackFlow {
    val cm = context.getSystemService<ConnectivityManager>()!!
    val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            // we ignore the ChannelResult here
            trySend(NetworkStatus.Available)
        }
        override fun onLost(network: Network) {
            trySend(NetworkStatus.Unavailable)
        }
    }
    cm.registerDefaultNetworkCallback(callback)
    awaitClose { cm.unregisterNetworkCallback(callback) }
}.distinctUntilChanged()
