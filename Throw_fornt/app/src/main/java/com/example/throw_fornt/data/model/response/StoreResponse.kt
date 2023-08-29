package com.example.throw_fornt.data.model.response

import com.google.gson.annotations.SerializedName

//가게 조회 모델
data class StoreModel(
    @SerializedName("address_id")val addrressId: String,
    @SerializedName("location_id")val locationId: String,
    @SerializedName("name")val name: String,
    @SerializedName("crn")val bno: String,
    @SerializedName("type")val type: String,
    @SerializedName("seconde_company_password")val secondPassword: String,
)

//api호출시 데이터 response값을 받는 모델
data class StoreResponse(
    @SerializedName("code")val code:String,         //결과 코드
    @SerializedName("msg")val msg:String,           //결과 메세지
){
    override fun toString(): String {
        return  "resultCode: $code\n" +
                "resultMsg: $msg\n"
    }
}
