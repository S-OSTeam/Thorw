package com.example.throw_fornt.data.model.response

import com.google.gson.annotations.SerializedName

//데이터를 받을때 사용할 데이터 클래스
data class UserSignUp(
    @SerializedName("code")
    val code: String,
    @SerializedName("message")
    val message:String
)
