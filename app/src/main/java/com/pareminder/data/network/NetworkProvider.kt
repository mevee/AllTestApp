package com.pareminder.data.network

import android.os.strictmode.InstanceCountViolation
import com.pareminder.common.Constants.Companion.BASEURL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class NetworkProvider {

    companion object {
        @Volatile
       private var INSTANCE: ApiService? = null


        fun newInstance(): ApiService {
            synchronized(this){
                return INSTANCE ?: Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create()).build()
                    .create(ApiService::class.java).also {
                        INSTANCE = it
                    }
            }
        }



        private fun getClient():OkHttpClient{
            return OkHttpClient()/*.callTimeoutMillis(OkHttpClient.Builder.)*/
        }



    }


}