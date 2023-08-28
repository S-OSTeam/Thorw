package com.example.throw_fornt.data.model.request

import com.example.throw_fornt.data.model.response.StoreResponse
import com.example.throw_fornt.data.model.response.testBody
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreRequest {
    /*
    상점 등록 request
     */
    @GET("/api/...")
    fun registerRequest(
        @Query("address_id") address_id : String,
        @Query("location_id") loction_id : String,
        @Query("name") name : String,
        @Query("company_registration_number") company_registration_number : String,
        @Query("type") type : String,
        @Query("second_password") second_password : String,
    ) : Call<StoreResponse>

    /*
    상점 데이터 request
     */
    @GET("/api/...")
    fun storeRequest(
    ) : Call<StoreResponse>

    @GET("/api/...")
    fun bnoRequest(
        @Query("bno") address_id : String
    ) : Call<StoreResponse>

    @GET("fapi")
    fun testRequest(
        @Query("key") key: String,
        @Query("gb") gb:String,
        @Query("q") q:String,
        @Query("type") type:String
    ) : Call<testBody>
}
