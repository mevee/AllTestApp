package com.pareminder.data.network

import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.lang.StringBuilder

abstract class SafeApiRequest {
    suspend fun<T:Any> apiRequest(call:suspend () -> Response<T>):T{
        val  response = call.invoke()
        if (response.isSuccessful){
            return response.body()!!
        }else{
            val errorCode = response.errorBody()?.toString()
            val message = StringBuilder()

            errorCode?.let {
                try {
                    val jsonObject=  JSONObject(it)
                    message.append("${jsonObject.get("msg")}")
                } catch (e: JSONException) {
                    message.append("${e.message}")
                }
            }
            message.append("Error Code :${response.code()}")
            throw ApiException(message.toString())
        }
    }
}