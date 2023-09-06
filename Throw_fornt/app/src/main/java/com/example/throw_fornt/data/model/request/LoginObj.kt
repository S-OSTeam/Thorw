package com.example.throw_fornt.data.model.request

import com.google.gson.annotations.SerializedName

//서버 요청시 사용할 데이터 클래스
data class LoginObj(

    val inputId:String,
    val inputPassword:String,
)
