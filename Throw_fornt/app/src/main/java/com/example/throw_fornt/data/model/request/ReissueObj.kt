package com.example.throw_fornt.data.model.request

import com.google.gson.annotations.SerializedName

//자동로그인시에 사용하는 요청 데이터 클래스
data class ReissueObj(

    val grant_type:String,


    val refresh_token :String,


)
