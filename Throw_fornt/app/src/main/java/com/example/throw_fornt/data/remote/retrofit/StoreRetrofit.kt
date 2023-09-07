package com.example.throw_fornt.data.remote.retrofit

import android.util.Log
import com.example.throw_fornt.data.model.request.Register
import com.example.throw_fornt.data.model.request.StoreRequest
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.data.model.response.StoreResponse
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Exception
import java.net.URL

class StoreRetrofit {
    companion object {
        private const val HTTP_LOG_TAG = "HTTP_LOG"

        //const val apiKey = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0aW5wdXRpZCIsImV4cCI6MTY5NDAyNDUwOSwia2luZCI6ImFjY2Vzc1Rva2VuIn0.48-aB6f5-wbPeAmExjzKOpFxTYbooZ1inVfP2Vt3kqHaXuJUTvW51Fu1WiRSVsqd3yiZTa_VVMi1eyf8OCHYYg"
        const val apiKey = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0YWtlZmxhbWUiLCJleHAiOjE2OTQxMDg3OTgsImtpbmQiOiJhY2Nlc3NUb2tlbiJ9.hvu5_tJ7Cf9irmGybtQpsXMh3Og8GLYB_UfgUkC9zuYG8Wi6GSAL_VGZvKHs3_OptfvuJZJKy8ntv4A48-wZjw"
        //가게등록, 내 가게조회, 사업자등록번호 조회를 위한 공용url
        const val url = "https://moviethree.synology.me/api/"
    }

    //client 객체
    private val client = OkHttpClient.Builder().addInterceptor(getHttpLoggingInterceptor()).addInterceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $apiKey")
            .build()
        chain.proceed(newRequest)
    }.build()

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message ->
            android.util.Log.e(HTTP_LOG_TAG, message)
        }
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    //url 객체
    private val urls = URL(url)

    //retrofit 객체
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(urls)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val storeService = retrofit.create(StoreRequest::class.java)
}