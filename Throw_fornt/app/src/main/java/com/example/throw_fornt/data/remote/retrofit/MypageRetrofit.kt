package com.example.throw_fornt.data.remote.retrofit

import android.util.Log
import com.example.throw_fornt.data.model.request.MypageRequest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class MypageRetrofit {
    companion object {
        private const val HTTP_LOG_TAG = "HTTP_LOG"

        //가게등록, 내 가게조회, 사업자등록번호 조회를 위한 공용url
        const val url = "https://moviethree.synology.me/api/"
        const val apiKey ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0YWtlZmxhbWUiLCJleHAiOjE2OTQwMTIwNDgsImtpbmQiOiJhY2Nlc3NUb2tlbiJ9.Fu3iqYJ89T1i1Vzf2oHGtdRFdHgOYkQm1p1HwvlcfnWqfFqc3YYdGLJwIBqkb60Zg_tu2a6nGPYXcRQeSmlwOA"
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
            android.util.Log.e(HTTP_LOG_TAG, message)
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

    val mypageService = retrofit.create(MypageRequest::class.java)
}