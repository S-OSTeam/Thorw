package com.example.throw_fornt.data.model.response

import com.example.throw_fornt.models.GeoPoint
import com.example.throw_fornt.models.MapStoreInfo
import com.google.gson.annotations.SerializedName

data class StoreInfoBySearchNameResponse(
    @SerializedName("extStoreId") val extStoreId: String,
    @SerializedName("storeName") val storeName: String,
    @SerializedName("storePhone") val storePhone: String,
    @SerializedName("crn") val crn: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("zipCode") val zipCode: String,
    @SerializedName("fullAddress") val fullAddress: String,
    @SerializedName("trashType") val trashType: String,
) {
    fun toUI(): MapStoreInfo {
        return MapStoreInfo(
            extStoreId,
            storeName,
            storePhone,
            fullAddress,
            geoPoint = GeoPoint(latitude, longitude),
        )
    }
}
