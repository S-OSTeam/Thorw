package com.example.throw_fornt.data.model.request

import com.google.gson.annotations.SerializedName

//서버요청할 데이터 클래스
data class KakaoObj(

    val sns:String,
    val accessToken: String,
   )
