package com.example.throw_fornt.data.remote.retrofit

import com.example.throw_fornt.data.model.request.AddressRequest
import com.example.throw_fornt.data.model.request.StoreRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.URL

class AddressRetrofit {
    companion object{
        const val url = "https://dapi.kakao.com/"
        const val key = "KakaoAK c5ed2fbd6ebc9ee453bd9378c14b4f8d"
        lateinit var requestService: AddressRequest
    }

    fun searchAddress(){
        try {
            val urls = URL(url)
            val retrofit = Retrofit.Builder()
                .baseUrl(urls)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            requestService = retrofit.create(AddressRequest::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}