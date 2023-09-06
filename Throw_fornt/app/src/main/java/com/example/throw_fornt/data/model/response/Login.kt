package com.example.throw_fornt.data.model.response

import com.google.gson.annotations.SerializedName
//서버 응답받을 데이터 클래스
data class Login(
    @SerializedName("accessToken")
    val accessToken:String,

    @SerializedName("refreshToken")
    val refreshToken :String,

    @SerializedName("grantType")
    val grantType:String,

    @SerializedName("expiresIn")
    val expiresIn:String,

    @SerializedName("code")
    val code: String,

    @SerializedName("message")
    val message: String
)
