package com.example.throw_fornt.data.model.response

import android.os.Parcel
import android.os.Parcelable
import com.example.throw_fornt.feature.store.register.address.AddressResult
import com.google.gson.annotations.SerializedName

//가게 조회 모델
data class StoreModel(
    @SerializedName("storePhone")val storePhone: String,
    @SerializedName("latitudes")val latitudes: String,
    @SerializedName("longitude")val longitude: String,
    @SerializedName("crn")val bno: String,
    @SerializedName("zipCode")val zipCode: String,
    @SerializedName("fullAddress")val fullAddress: String,
    @SerializedName("storeName")val storeName: String,
    @SerializedName("trashType")val trashType: String,
    @SerializedName("extStoreId")val uuid: String,
    @SerializedName("code")val code:String,         //결과 코드
    @SerializedName("message")val msg:String,           //결과 메세지
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(storePhone)
        parcel.writeString(latitudes)
        parcel.writeString(longitude)
        parcel.writeString(bno)
        parcel.writeString(zipCode)
        parcel.writeString(fullAddress)
        parcel.writeString(storeName)
        parcel.writeString(trashType)
        parcel.writeString(uuid)
        parcel.writeString(code)
        parcel.writeString(msg)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StoreModel> {
        override fun createFromParcel(parcel: Parcel): StoreModel {
            return StoreModel(parcel)
        }

        override fun newArray(size: Int): Array<StoreModel?> {
            return arrayOfNulls(size)
        }
    }
}

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
