package com.example.throw_fornt.data.model.response

import com.google.gson.annotations.SerializedName


data class AddressResponse(
    @SerializedName("meta")val meta: MetaResponse,
    @SerializedName("documents")val documents: List<DocumentResponse>,
)

data class MetaResponse(
    @SerializedName("total_count")val totalCnt: String,
    @SerializedName("pageable_count")val pageCnt: String,
    @SerializedName("is_end")val isEnd: String,
)

data class DocumentResponse(
    @SerializedName("address_name")val addressName: String?,
    @SerializedName("address_type")val addressType: String?,
    @SerializedName("x")val longitude: String?,
    @SerializedName("y")val latitude: String?,
    @SerializedName("address")val address: AddressModel?,
    @SerializedName("road_address")val roadAddress: RoadAddressModel?,
)

data class AddressModel(
    @SerializedName("address_name")val address: String?,
)

data class RoadAddressModel(
    @SerializedName("address_name")val address: String?,
    @SerializedName("zone_no")val zoneNo: String?,
)