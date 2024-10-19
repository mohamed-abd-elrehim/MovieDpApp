package com.example.banquemisrchallenge05movieapp.utils.shared_methods

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager.CONNECTIVITY_ACTION
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class InternetChecker(private val context: Context) {

    private val _networkStateFlow = MutableSharedFlow<Boolean>(replay = 1)
    val networkStateFlow: SharedFlow<Boolean> = _networkStateFlow

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            checkInternetConnection()
        }
    }

    fun startMonitoring() {
        val intentFilter = IntentFilter(CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver, intentFilter)
        checkInternetConnection()
    }

    fun stopMonitoring() {
        context.unregisterReceiver(networkReceiver)
    }

    private fun checkInternetConnection() {
        val isConnected = isInternetAvailable()
        CoroutineScope(Dispatchers.Default).launch {
            _networkStateFlow.emit(isConnected)
        }
        // Show a Toast message for online or offline status
        showNetworkStatusToast(isConnected)
    }

    private fun showNetworkStatusToast(isConnected: Boolean) {
        val message = if (isConnected) {
            "Online Mode"
        } else {
            "Offline Mode"
        }
        // Use the main thread to show the Toast
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    fun isInternetAvailable(): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}
