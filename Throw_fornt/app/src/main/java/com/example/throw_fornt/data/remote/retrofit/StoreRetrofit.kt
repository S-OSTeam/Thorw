package com.example.throw_fornt.data.remote.retrofit

import com.example.throw_fornt.data.model.request.StoreRequest
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.data.model.response.StoreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.URL

class StoreRetrofit {

    companion object {
        const val url = ""          //가게등록, 내 가게조회, 사업자등록번호 조회를 위한 공용url
    }

    /*
    가게 등록을 위한 api호출
     */
    fun registerResponse(store: StoreModel){
        try {
            val urls = URL(url)
            val retrofit = Retrofit.Builder()
                .baseUrl(urls)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val request: StoreRequest = retrofit.create(StoreRequest::class.java)
            request.registerRequest(
                store.address_id, store.location_id, store.name,
                store.company_registration_number, store.type, store.second_password
            ).enqueue(object: Callback<StoreResponse>{
                override fun onResponse(
                    call: Call<StoreResponse>,
                    response: Response<StoreResponse>
                ) {
                    if(response.isSuccessful && response.body()?.code=="200"){
                        //성공 토스트 넣기
                    }
                    else{
                        //실패 토스트 넣기
                    }
                }

                override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                    //실패 토스트 넣기
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
    내 가게 조회를 위한 api호출
     */
    fun storeResponse(): Boolean {
        return false
    }

    /*
    사업자등록번호 조회 api 호출
     */
    fun bnoResponse(companyNum: String): Boolean = runBlocking {
        var check = false               //사업자등록번호가 조회가 되는지 확인하는 변수
        if (companyNum == "") return@runBlocking check

        try {
            val urls = URL(url)
            val retrofit = Retrofit.Builder()
                .baseUrl(urls)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val request: StoreRequest = retrofit.create(StoreRequest::class.java)
            val response: Response<StoreResponse> = withContext(Dispatchers.IO) {
                try {
                    request.bnoRequest(companyNum).execute()
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }!!
            }

            if (response != null && response.isSuccessful && response.body()?.code == "200") {
                check = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@runBlocking check
    }
}