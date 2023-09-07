package com.example.throw_fornt.data.remote.retrofit

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
import java.net.URL

class StoreRetrofit {
    companion object {
        private const val HTTP_LOG_TAG = "HTTP_LOG"

        const val url = "https://moviethree.synology.me/api/"
        const val apiKey = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0aW5wdXRpZCIsImV4cCI6MTY5NDEyMTM5OCwia2luZCI6ImFjY2Vzc1Rva2VuIn0.emNvGxGVoloBYCuRL9zNh9VzP-zUIhrHFOoROfc13M2MuuyDBJ5DNV5J8xFa2O0LinfCdsSF4rM3N6flDUoufg"
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

    // retrofit 객체
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(urls)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val storeService = retrofit.create(StoreRequest::class.java)
}
