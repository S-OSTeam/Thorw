package com.example.throw_fornt.data.model.request

import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.data.model.response.StoreResponse
import com.example.throw_fornt.data.model.response.testBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface StoreRequest {

    //상점 등록 request
    @FormUrlEncoded
    @POST("api/store")
    fun registerRequest(
        @Field("storePhone") storePhone : String,
        @Field("latitudes") latitudes : Double,
        @Field("longitude") longitude : Double,
        @Field("crn") bno : String,
        @Field("zipCode") zipCode : String,
        @Field("fullAddress") fullAddress : String,
        @Field("type") type: String,
    ) : Call<StoreModel>

    //내 가게 조회 request
    @FormUrlEncoded
    @POST("/api/...")
    fun storeRequest(
        @Header("Content-Type") key: String,
    ) : Call<StoreModel>

    //사업자등록번호 조회 request
    @FormUrlEncoded
    @POST("api/store/crn")
    fun bnoRequest(
        @Field("crn") crn : String
    ) : Call<StoreModel>


    //가게 수정 request
    @FormUrlEncoded
    @PUT("api/store")
    fun mondifyRequest(
        @Field("extStoreId") uuid: String,
        @Field("storePhone") storePhone: String,
        @Field("latitudes") latitudes : Double,
        @Field("longitude") longitude : Double,
        @Field("crn") bno : String,
        @Field("zipCode") zipCode : String,
        @Field("fullAddress") fullAddress : String,
        @Field("type") type: String,
    ): Call<StoreModel>

    //가게 삭제 request
    @FormUrlEncoded
    @DELETE("api/store")
    fun deleteRequest(
        @Field("extStoreId") uuid: String,
        @Field("storeName") storeName: String,
        @Field("storePhone") storePhone: String,
        @Field("latitudes") latitudes : Double,
        @Field("longitude") longitude : Double,
        @Field("crn") bno : String,
        @Field("zipCode") zipCode : String,
        @Field("fullAddress") fullAddress : String,
        @Field("type") type: String,
    ): Call<StoreModel>

    @GET("fapi")
    fun getRequest(
        @Query("key") key: String,
        @Query("gb") gb:String,
        @Query("q") q:String,
        @Query("type") type:String
    ) : Call<testBody>
}
