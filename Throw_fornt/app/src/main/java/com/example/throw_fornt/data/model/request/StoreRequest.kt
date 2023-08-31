package com.example.throw_fornt.data.model.request

import com.example.throw_fornt.data.model.response.StoreResponse
import com.example.throw_fornt.data.model.response.testBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface StoreRequest {
    /*
    상점 등록 request
     */
    @POST("api/store")
    fun registerRequest(
        @Field("Content-Type") key: String,
        @Field("storePhone") storePhone : String,
        @Field("latitudes") latitudes : String,
        @Field("longitude") longitude : String,
        @Field("crn") bno : String,
        @Field("zipCode") zipCode : String,
        @Field("fullAddress") fullAddress : String,
        @Field("storeName") storeName: String,
        @Field("type") type: String,
    ) : Call<StoreResponse>

    /*
    상점 데이터 request
     */
    @POST("/api/...")
    fun storeRequest(
        @Field("Content-Type") key: String,
    ) : Call<StoreResponse>

    @POST("api/store/crn")
    fun bnoRequest(
        @Field("Content-Type") key: String,
        @Field("crn") crn : String
    ) : Call<StoreResponse>



    @GET("fapi")
    fun getRequest(
        @Query("key") key: String,
        @Query("gb") gb:String,
        @Query("q") q:String,
        @Query("type") type:String
    ) : Call<testBody>
}
