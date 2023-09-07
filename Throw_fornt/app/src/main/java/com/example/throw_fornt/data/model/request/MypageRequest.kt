package com.example.throw_fornt.data.model.request

import com.example.throw_fornt.data.model.response.MypageResponse
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MypageRequest{
    //회원조회 request
    @GET("user")
    fun memberRequest(): Call<MypageResponse>

    //회원정보 수정
    @POST("user/cnginfo")
    fun profileRequest(@Body profile: ProfileBody): Call<Unit>
}

data class ProfileBody(
    @SerializedName("userPhoneNumber")val userPhoneNumber: String,
    @SerializedName("email")val email: String,
    @SerializedName("userName")val userName: String,
)