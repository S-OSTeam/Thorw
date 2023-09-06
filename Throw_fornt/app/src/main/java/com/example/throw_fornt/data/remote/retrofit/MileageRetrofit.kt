package com.example.throw_fornt.data.remote.retrofit

import android.util.Log
import com.example.throw_fornt.data.model.request.MileageRequest
import com.example.throw_fornt.data.model.request.MypageRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class MileageRetrofit {
    companion object {
        private const val HTTP_LOG_TAG = "HTTP_LOG"
    }

    //client 객체
    private val client = OkHttpClient.Builder().addInterceptor(getHttpLoggingInterceptor()).addInterceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${StoreRetrofit.apiKey}")
            .build()
        chain.proceed(newRequest)
    }.build()

    private fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message ->
            Log.e(HTTP_LOG_TAG, message)
        }
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    //url 객체
    private val urls = URL(StoreRetrofit.url)

    //retrofit 객체
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(urls)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val mileageService = retrofit.create(MileageRequest::class.java)
}