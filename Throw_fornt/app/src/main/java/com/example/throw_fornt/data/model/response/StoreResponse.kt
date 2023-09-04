package com.example.throw_fornt.data.model.response

import android.os.Parcel
import android.os.Parcelable
import com.example.throw_fornt.feature.store.register.address.AddressResult
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

//가게 조회 모델
@Parcelize
data class StoreModel(
    @SerializedName("extStoreId")val uuid: String,              //가게 고유 id
    @SerializedName("storePhone")val storePhone: String,        //가게 전화번호
    @SerializedName("storeName")val storeName: String,          //가게 이름
    @SerializedName("latitude")val latitudes: Double,           //위도 (-90~90)
    @SerializedName("longitude")val longitude: Double,          //경도 (-180~180)
    @SerializedName("crn")val bno: String,                      //사업자등록번호
    @SerializedName("zipCode")val zipCode: String,              //우편번호
    @SerializedName("fullAddress")val fullAddress: String,      //지번주소
    @SerializedName("subAddress")val subAddress: String,        //세부주소
    @SerializedName("trashType")val trashType: String,          //일쓰->병->플라스틱->종이->캔
    @SerializedName("code")val code:String,                     //에러시 결과 코드
    @SerializedName("message")val msg:String,                   //에러시 결과 메세지
):Parcelable

//api호출시 데이터 response값을 받는 모델
data class StoreResponse(
    @SerializedName("code")val code:String,         //결과 코드
    @SerializedName("message")val msg:String,           //결과 메세지
){
    override fun toString(): String {
        return  "resultCode: $code\n" +
                "resultMsg: $msg\n"
    }
}

//사업자 데이터받는 class
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
