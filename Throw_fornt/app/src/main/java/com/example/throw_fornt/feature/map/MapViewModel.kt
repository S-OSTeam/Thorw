package com.example.throw_fornt.feature.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.models.GeoPoint
import com.example.throw_fornt.models.MapStoreInfo
import com.example.throw_fornt.models.Trash
import com.example.throw_fornt.util.common.SingleLiveEvent

class MapViewModel : ViewModel() {
    private val _event: SingleLiveEvent<Event> = SingleLiveEvent()
    val event: LiveData<Event>
        get() = _event

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

    private val _selectedTrashTypes: MutableLiveData<List<Trash>> =
        MutableLiveData(listOf(Trash.ALL))
    val selectedTrashTypes: LiveData<List<Trash>>
        get() = _selectedTrashTypes

    var searchedStores: List<MapStoreInfo> = listOf()
        private set(value) {
            field = value
            _event.value = Event.ShowMapStoreInfo
        }

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

    fun moveCameraToCurPosition() {
        if (updateCurPositionLoading) return
        _lastUserPoint.value?.let {
            updateCurPositionLoading = true
            _curCameraCenterPoint.value = it
            updateCurPositionLoading = false
        }
    }

    fun refreshNearbyStores() {
        if (refreshStoreLoading) return
        _lastUserPoint.value?.let {
            refreshStoreLoading = true
            // 가게 목록 갱신
            _nearByStores.value = listOf(
                MapStoreInfo(
                    "가게1",
                    GeoPoint(it.latitude + 0.01, it.longitude + 0.01),
                ),
                MapStoreInfo(
                    "가게2",
                    GeoPoint(it.latitude - 0.01, it.longitude - 0.01),
                ),
            )
            //
            refreshStoreLoading = false
        }
    }

    val searchStores = { content: String? ->
        if (content.isNullOrEmpty().not()) {
            Log.d("mendel", "검색: $content")
            lastUserPoint.value?.let {
                Log.d("mendel", "진짜: ${this.hashCode()}")

                // 검색 결과가 0이 아닐때만.
                searchedStores = listOf(
                    MapStoreInfo(
                        "가게1",
                        GeoPoint(it.latitude + 0.01, it.longitude + 0.01),
                    ),
                    MapStoreInfo(
                        "가게2",
                        GeoPoint(it.latitude - 0.01, it.longitude - 0.01),
                    ),
                )
            }
        }
    }

    fun changeCheckedState(type: Trash, isChecked: Boolean) {
        if (type == Trash.ALL && isChecked) {
            _selectedTrashTypes.value = listOf(type)
            return
        }

        val checkedStates = (_selectedTrashTypes.value?.toMutableList() ?: mutableListOf()).apply {
            if (isChecked) {
                add(type)
            } else {
                remove(type)
            }
        }

        if (checkedStates.size == 0) {
            _selectedTrashTypes.value = listOf(Trash.ALL)
        } else if (checkedStates.size >= 2 && checkedStates.contains(Trash.ALL)) {
            checkedStates.remove(Trash.ALL)
            _selectedTrashTypes.value = checkedStates
        } else {
            _selectedTrashTypes.value = checkedStates
        }
    }

    sealed class Event {
        object ShowMapStoreInfo : Event()
    }
}
