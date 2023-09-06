package com.example.throw_fornt.data.model.request

import com.google.gson.annotations.SerializedName

data class StoreSearchNameForMapRequest(
    @SerializedName("storeName") val storeName: String,
)
