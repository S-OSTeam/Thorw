package com.example.throw_fornt.feature.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.models.GeoPoint
import com.example.throw_fornt.models.MapStoreInfo
import com.example.throw_fornt.util.common.SingleLiveEvent

class MapViewModel : ViewModel() {
    private var visibleMapMinQuarterDistance: Int = 100
    private val _curCameraCenterPoint: SingleLiveEvent<GeoPoint> = SingleLiveEvent()
    val curCameraCenterPoint: LiveData<GeoPoint>
        get() = _curCameraCenterPoint

    private val _lastUserPoint: SingleLiveEvent<GeoPoint> = SingleLiveEvent()
    val lastUserPoint: LiveData<GeoPoint>
        get() = _lastUserPoint

    private val _nearByStores: MutableLiveData<List<MapStoreInfo>> = MutableLiveData()
    val nearByStores: LiveData<List<MapStoreInfo>>
        get() = _nearByStores

    private var updateCurPositionLoading: Boolean = false
    private var refreshStoreLoading: Boolean = false

    // 카카오맵 줌 이벤트 리스너가 제대로 안먹힘. 먹혔다가 안먹혔다가 해서 일단 이건 고정으로 놓을 예정
    fun setVisibleMapDistance(bottomLeft: GeoPoint, topRight: GeoPoint) {
        val widthDistance = DistanceManager.getDistance(
            bottomLeft.latitude,
            bottomLeft.longitude,
            bottomLeft.latitude,
            topRight.longitude,
        )
        val heightDistance = DistanceManager.getDistance(
            bottomLeft.latitude,
            bottomLeft.longitude,
            topRight.latitude,
            bottomLeft.longitude,
        )
        visibleMapMinQuarterDistance = (listOf(widthDistance, heightDistance).min() / 8)
        Log.d("mendel", "최소 반지름: $visibleMapMinQuarterDistance")
    }

    fun updateCurPosition(geoPoint: GeoPoint) {
        if (updateCurPositionLoading) return
        updateCurPositionLoading = true
        if (_lastUserPoint.value == null) {
            _curCameraCenterPoint.value = geoPoint
            _lastUserPoint.value = geoPoint
            updateCurPositionLoading = false
            refreshNearbyStores()
            return
        }
        _curCameraCenterPoint.value?.let { cameraCenterPoint ->
            val distanceFromCameraCenter = DistanceManager.getDistance(
                cameraCenterPoint.latitude,
                cameraCenterPoint.longitude,
                geoPoint.latitude,
                geoPoint.longitude,
            )
            Log.d("mendel", "카메라 중심까지 거리: $distanceFromCameraCenter")
            if (distanceFromCameraCenter > visibleMapMinQuarterDistance) {
                _curCameraCenterPoint.value = geoPoint
            }
            _lastUserPoint.value = geoPoint
        }
        updateCurPositionLoading = false
    }

    fun refreshNearbyStores() {
        if (refreshStoreLoading) return
        _lastUserPoint.value?.let {
            refreshStoreLoading = true
            // 가게 목록 갱신
            _nearByStores.value = listOf(
                MapStoreInfo(
                    "가게1",
                    GeoPoint(it.latitude + 0.05, it.longitude + 0.05),
                ),
                MapStoreInfo(
                    "가게2",
                    GeoPoint(it.latitude - 0.05, it.longitude - 0.05),
                ),
            )
            //
            refreshStoreLoading = false
        }
    }
}
