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

data class testData(
    val bno: String,        //사업자등록번호
    val cno: String,        //법인등록번호
    val company: String,    //회사명
    val BSttCd: String,     //사업자상태(코드)
    val bstt: String,       //사업자상태(명칭)
    val TaxTypeCd: String,  //과세유형(코드)
    val taxtype: String,    //과세유형(명칭)
    val EndDt: String,      //폐업일
)

//사업자 등록번호로 조회한 데이터 받는 class
data class testBody(
    @SerializedName("resultCode")var resultCode: String,        //결과코드
    @SerializedName("resultMsg")var resultMsg: String,          //결과메세지
    @SerializedName("totalCount")var totalCount: String,        //총 아이템 갯수
    @SerializedName("items")var items: List<testData>        //가게 리스트
){
    override fun toString(): String {
        return "$items\n\n" +
                "resultCode: $resultCode\n" +
                "resultMsg: $resultMsg\n" +
                "totalCount: $totalCount"
    }
}
