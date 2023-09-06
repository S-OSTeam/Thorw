package com.example.throw_fornt.data.model.response

import com.google.gson.annotations.SerializedName

//응답시에 이용할 데이터 클래스
data class IdDup(
    @SerializedName("idDup")
    val idDup: Boolean,
)
