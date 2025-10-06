package com.alenniboris.personalmanager.presentation.uikit.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import com.alenniboris.personalmanager.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object NetworkMonitorUtil {
    private val _isConnected = MutableStateFlow(true)
    val isConnected = _isConnected.asStateFlow()

    fun init(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val currentNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(currentNetwork)
        val connectedNow = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        _isConnected.update { connectedNow }

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                if (!_isConnected.value) {
                    ToastUtil.show(context, R.string.internet_recovered_text)
                }
                _isConnected.value = true
            }

            override fun onLost(network: Network) {
                _isConnected.value = false
                ToastUtil.show(context, R.string.internet_exception)
            }
        }

        connectivityManager.registerDefaultNetworkCallback(callback)
    }
}