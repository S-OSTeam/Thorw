package com.example.throw_fornt.data.model.request

import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.data.model.response.StoreResponse
import com.example.throw_fornt.data.model.response.testBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface StoreRequest {
    /*
    상점 등록 request
     */
    @FormUrlEncoded
    @POST("/api/store")
    fun registerRequest(
        @Header("Content-Type") key: String,
        @Field("storePhone") storePhone : String,
        @Field("latitudes") latitudes : Double,
        @Field("longitude") longitude : Double,
        @Field("crn") bno : String,
        @Field("zipCode") zipCode : String,
        @Field("fullAddress") fullAddress : String,
        @Field("type") type: String,
    ) : Call<StoreModel>

    /*
    상점 데이터 request
     */
    @FormUrlEncoded
    @POST("/api/...")
    fun storeRequest(
        @Header("Content-Type") key: String,
    ) : Call<StoreModel>

    @FormUrlEncoded
    @POST("/api/store/crn")
    fun bnoRequest(
        @Header("Content-Type") key: String,
        @Field("crn") crn : String
    ) : Call<StoreModel>


    @FormUrlEncoded
    @PUT("/api/store")
    fun mondifyRequest(
        @Header("Content-Type") key: String,
        @Field("extStoreId") uuid: String,
        @Field("storePhone") storePhone: String,
        @Field("latitudes") latitudes : Double,
        @Field("longitude") longitude : Double,
        @Field("crn") bno : String,
        @Field("zipCode") zipCode : String,
        @Field("fullAddress") fullAddress : String,
        @Field("type") type: String,
    )

    @GET("fapi")
    fun getRequest(
        @Query("key") key: String,
        @Query("gb") gb:String,
        @Query("q") q:String,
        @Query("type") type:String
    ) : Call<testBody>
}
