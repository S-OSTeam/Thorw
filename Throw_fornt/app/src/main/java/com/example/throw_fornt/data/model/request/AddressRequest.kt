package com.example.throw_fornt.data.model.request

import com.example.throw_fornt.data.model.response.AddressResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AddressRequest {
    @GET("v2/local/search/address.json")
    fun getSearchAddress(
        @Header("Authorization") key: String,
        @Query("query") query: String,
    ): Call<AddressResponse>
}