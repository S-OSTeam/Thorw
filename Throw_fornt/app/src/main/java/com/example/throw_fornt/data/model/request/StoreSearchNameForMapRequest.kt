package com.example.throw_fornt.data.model.request

import com.google.gson.annotations.SerializedName

data class StoreSearchNameForMapRequest(
    @SerializedName("name") val name: String,
)
