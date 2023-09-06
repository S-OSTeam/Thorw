package com.example.throw_fornt.data.model.request

import com.example.throw_fornt.data.model.response.MypageResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MypageRequest{
    //회원조회 request
    @GET("user")
    fun memberRequest(): Call<MypageResponse>
}