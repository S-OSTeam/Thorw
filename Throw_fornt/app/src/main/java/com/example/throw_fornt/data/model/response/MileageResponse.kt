package com.example.throw_fornt.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MileageResponse(
    @SerializedName("userName")val userName: String,
    @SerializedName("mileage")val mileage: Long,
    @SerializedName("ranking")val ranking: Long,
): Parcelable
