package com.example.throw_fornt.data.model.request

import com.google.gson.annotations.SerializedName

data class StoresInfoByLocationRequest(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("distance") val distance: Double,
    @SerializedName("trashType") val trashType: String,
)
