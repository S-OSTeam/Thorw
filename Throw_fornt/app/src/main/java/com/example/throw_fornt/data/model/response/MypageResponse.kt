package com.example.throw_fornt.data.model.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MypageResponse(
    @SerializedName("inputId")val inputId: String,
    @SerializedName("role")val role: String,
    @SerializedName("userName")val userName: String,
    @SerializedName("userPhoneNumber")val userPhoneNumber: String,
    @SerializedName("email")val email: String,
):Parcelable