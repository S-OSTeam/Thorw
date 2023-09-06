package com.example.throw_fornt.data.model.request

import com.example.throw_fornt.data.model.response.MileageResponse
import retrofit2.Call
import retrofit2.http.GET

interface MileageRequest {
    //마일리지 랭킹 조회
    @GET("rank/leaderboard")
    fun rankingRequest(): Call<List<MileageResponse>>

    //내 마일리지 랭킹 조회
    @GET("rank")
    fun mileageRequest(): Call<MileageResponse>
}