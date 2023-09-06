package com.example.throw_fornt.data.model.response

import com.google.gson.annotations.SerializedName

//자동로그인 시에 사용하는 응답 데이터 클래스
data class Reissue(
    @SerializedName("accessToken")
    val accessToken:String,

    @SerializedName("refreshToken")
    val refreshToken :String,

    @SerializedName("code")
    val code: String,

    @SerializedName("message")
    val message: String
)
