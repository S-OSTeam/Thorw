package com.example.throw_fornt.data.model.response

import com.google.gson.annotations.SerializedName

data class StoreResponse(
    val address_id: String,
    val location_id: String,
    val name: String,
    val company_registration_number: String,
    val type: String,
    val second_password: String,
)

data class ResponseBody(
    @SerializedName("code")var code:String,         //결과 코드
    @SerializedName("msg")var msg:String,           //결과 메세지
){
    override fun toString(): String {
        return  "resultCode: $code\n" +
                "resultMsg: $msg\n"
    }
}
