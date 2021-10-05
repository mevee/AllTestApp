package com.pareminder.data.network

import android.content.Context
import android.os.strictmode.InstanceCountViolation
import com.pareminder.common.Constants.Companion.BASEURL
import com.pareminder.data.network.interceptors.AppConnectivityInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class NetworkProvider {

    companion object {
        @Volatile
        private var INSTANCE: ApiService? = null

        fun newInstance(context: Context): ApiService {
            synchronized(this) {
                return INSTANCE ?: Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .client(getClient(context))
                    .addConverterFactory(GsonConverterFactory.create()).build()
                    .create(ApiService::class.java).also {
                        INSTANCE = it
                    }
            }
        }

        private fun getClient(context: Context): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(AppConnectivityInterceptor(context))
                .build()
        }
    }


}