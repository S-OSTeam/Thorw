package com.example.throw_fornt.data.remote.retrofit

import com.example.throw_fornt.data.model.request.StoreRequest
import com.example.throw_fornt.data.model.response.StoreModel
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
        // 사업자등록번호 조회 테스트 api
        // const val url = "https://bizno.net/api/"
        // const val apiKey = "ZG1sdG4zNDI2QGdtYWlsLmNvbSAg"

        // 가게등록, 내 가게조회, 사업자등록번호 조회를 위한 공용url
        const val url = "https://moviethree.synology.me/api/"
        const val apiKey = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0aW5wdXRpZCIsImV4cCI6MTY5NDAwODY2NSwia2luZCI6ImFjY2Vzc1Rva2VuIn0.71olw61Doff8h0VCx8Ie8eyKPQShvfEHIfAvQG3VKq8iQcNvhWfE8LludLKtV7JAvuKeE9Bvjkd1A4_EiDUAEA"
    }

    // client 객체
    private val client =
        OkHttpClient.Builder().addInterceptor(getHttpLoggingInterceptor()).addInterceptor { chain ->
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

    // url 객체
    private val urls = URL(url)

    // retrofit 객체
    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(urls)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val storeService = retrofit.create(StoreRequest::class.java)

    // 가게 수정을 위한 api호출
    fun modifyResponse(store: StoreModel) {
        try {
            storeService.mondifyRequest(
                store.uuid,
                store.storePhone,
                store.latitudes,
                store.longitude,
                store.bno,
                store.zipCode,
                store.fullAddress + "(${store.subAddress})",
                store.trashType,
            ).enqueue(object : Callback<StoreModel> {
                override fun onResponse(
                    call: Call<StoreModel>,
                    response: Response<StoreModel>,
                ) {
                    if (response.isSuccessful && response.body()?.code == "200") {
                    } else {
                    }
                }

                override fun onFailure(call: Call<StoreModel>, t: Throwable) {
                    // TODO 실패 토스트 넣기
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // 내 가게 조회를 위한 api호출\
    fun storeResponse(): Boolean {
        return false
    }
}
