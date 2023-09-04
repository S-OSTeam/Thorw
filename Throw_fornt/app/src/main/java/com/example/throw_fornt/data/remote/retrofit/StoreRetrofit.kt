package com.example.throw_fornt.data.remote.retrofit

import android.util.Log
import com.example.throw_fornt.data.model.request.Register
import com.example.throw_fornt.data.model.request.StoreRequest
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.data.model.response.StoreResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.URL

class StoreRetrofit {
    companion object {
        //사업자등록번호 조회 테스트 api
        //const val url = "https://bizno.net/api/"
        //const val apiKey = "ZG1sdG4zNDI2QGdtYWlsLmNvbSAg"

        //가게등록, 내 가게조회, 사업자등록번호 조회를 위한 공용url
        const val url = "https://moviethree.synology.me/api/"
        const val apiKey = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0aW5wdXRpZCIsImV4cCI6MTY5MzgzMTQ3NX0.mTmIGZaUPwOww4-l8YTV2UHH0iEjH9RctsTh-HV8D24bJ68P8wE_VEKZxwcSlmcB-GQZ9sIEywkQH6X0eG1cpg"
        lateinit var requestService: StoreRequest
    }


    //가게 등록을 위한 api호출
    fun registerResponse(){
        try {
            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $apiKey")
                    .build()
                chain.proceed(newRequest)
            }.build()

            val urls = URL(url)
            //가게 등록하기 위한 데이터 값을 log로 표시
            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(urls)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            requestService = retrofit.create(StoreRequest::class.java)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //가게 수정을 위한 api호출
    fun modifyResponse(store: StoreModel){
        try {
            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $apiKey")
                    .build()
                chain.proceed(newRequest)
            }.build()

            val urls = URL(url)
            //가게 등록하기 위한 데이터 값을 log로 표시
            val retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(urls)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val request: StoreRequest = retrofit.create(StoreRequest::class.java)
            request.mondifyRequest(store.uuid, store.storePhone, store.latitudes, store.longitude,
                store.bno, store.zipCode, store.fullAddress+"(${store.subAddress})", store.trashType,
            ).enqueue(object: Callback<StoreModel>{
                override fun onResponse(
                    call: Call<StoreModel>,
                    response: Response<StoreModel>
                ) {
                    if(response.isSuccessful && response.body()?.code=="200"){

                    }
                    else{

                    }
                }

                override fun onFailure(call: Call<StoreModel>, t: Throwable) {
                    //TODO 실패 토스트 넣기
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    //내 가게 조회를 위한 api호출\
    fun storeResponse(): Boolean {
        return false
    }


    //사업자등록번호 조회 api 호출
    fun bnoResponse(){
        try {
            val urls = URL(url)
            val retrofit = Retrofit.Builder()
                .baseUrl(urls)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            requestService = retrofit.create(StoreRequest::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}