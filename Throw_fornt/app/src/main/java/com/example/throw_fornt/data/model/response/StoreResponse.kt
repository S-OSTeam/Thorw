package com.example.throw_fornt.data.model.response

import com.google.gson.annotations.SerializedName

//가게 조회 모델
data class StoreModel(
    val address_id: String,
    val location_id: String,
    val name: String,
    val company_registration_number: String,
    val type: String,
    val second_password: String,
)

//api호출시 데이터 response값을 받는 모델
data class StoreResponse(
    @SerializedName("code")var code:String,         //결과 코드
    @SerializedName("msg")var msg:String,           //결과 메세지
){
    override fun toString(): String {
        return  "resultCode: $code\n" +
                "resultMsg: $msg\n"
    }
}
