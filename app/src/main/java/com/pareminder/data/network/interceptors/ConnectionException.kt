package com.pareminder.data.network.interceptors

import java.io.IOException
class NoConnectivityException : IOException() {
    override val message= "No network available, please check your WiFi or Data connection"
}

class NoInternetException() : IOException() {
    override val message= "No internet available, please check your connected WIFi or Data"
}
