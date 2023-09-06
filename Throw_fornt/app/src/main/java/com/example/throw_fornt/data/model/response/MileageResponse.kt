package com.example.throw_fornt.data.model.response

import com.google.gson.annotations.SerializedName

data class MileageResponse(
    @SerializedName("userName")val userName: String,
    @SerializedName("mileage")val mileage: Long,
    @SerializedName("ranking")val ranking: Long,
)
