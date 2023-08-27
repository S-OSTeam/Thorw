package com.example.throw_fornt.models

data class MapStoreInfo(
    val id: Long,
    val storeName: String,
    val geoPoint: GeoPoint,
    val storeImageUrl: String = "http://www.bizhankook.com/upload/bk/article/202002/thumb/19402-44437-sampleM.jpg",
)
