package com.example.throw_fornt.feature.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.models.GeoPoint
import com.example.throw_fornt.models.MapStoreInfo
import com.example.throw_fornt.models.Trash
import com.example.throw_fornt.util.common.SingleLiveEvent
import java.math.RoundingMode

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

    private val _nearByStores: MutableLiveData<Map<Int, List<MapStoreInfo>>> =
        MutableLiveData()
    val nearByStores: LiveData<Map<Int, List<MapStoreInfo>>>
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
        val adjustGeoPoint = geoPoint.adjustGeoPoint()
        if (_lastUserPoint.value == null) {
            _curCameraCenterPoint.value = adjustGeoPoint
            _lastUserPoint.value = adjustGeoPoint
            updateCurPositionLoading = false
            refreshNearbyStores()
            return
        }
        _curCameraCenterPoint.value?.let { cameraCenterPoint ->
            val distanceFromCameraCenter = DistanceManager.getDistance(
                cameraCenterPoint.latitude,
                cameraCenterPoint.longitude,
                adjustGeoPoint.latitude,
                adjustGeoPoint.longitude,
            )
            Log.d("mendel", "카메라 중심까지 거리: $distanceFromCameraCenter")
            if (distanceFromCameraCenter > visibleMapMinQuarterDistance) {
                _curCameraCenterPoint.value = adjustGeoPoint
            }
            _lastUserPoint.value = adjustGeoPoint
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
            _nearByStores.value =
                fakeDataAroundStores(it).groupStoresByGeoPoint().mapKeys { it.key.hashCode() }
            refreshStoreLoading = false
        }
    }

    private fun List<MapStoreInfo>.groupStoresByGeoPoint(precision: Int = DEFAULT_PRECISION): Map<GeoPoint, List<MapStoreInfo>> {
        val groupedStores = mutableMapOf<GeoPoint, MutableList<MapStoreInfo>>()

        for (store in this) {
            val roundedPoint = store.geoPoint.adjustGeoPoint(precision)

            Log.d("mendel", "${store.storeName}, 조정된 값: $roundedPoint")
            if (!groupedStores.containsKey(roundedPoint)) {
                groupedStores[roundedPoint] = mutableListOf()
            }
            groupedStores[roundedPoint]?.add(store)
        }

        return groupedStores
    }

    private fun GeoPoint.adjustGeoPoint(precision: Int = DEFAULT_PRECISION): GeoPoint {
        val roundedLatitude =
            latitude.toBigDecimal().setScale(precision, RoundingMode.HALF_UP).toDouble()
        val roundedLongitude =
            longitude.toBigDecimal().setScale(precision, RoundingMode.HALF_UP).toDouble()

        return GeoPoint(roundedLatitude, roundedLongitude)
    }

    fun selectMapPoint(groupKey: Int) {
        nearByStores.value?.let { nearByStores ->
            val selectedStoreGroup = nearByStores[groupKey] ?: return@let
            searchedStores = selectedStoreGroup
        }
    }

    val searchStores = { content: String? ->
        if (content.isNullOrEmpty().not()) {
            lastUserPoint.value?.let {
                // 검색 결과가 0이 아닐때만.
                searchedStores = fakeDataSearchedResult(it)
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

    companion object {
        private const val DEFAULT_PRECISION = 5
        private fun fakeDataAroundStores(userPoint: GeoPoint) = listOf(
            MapStoreInfo(
                1L,
                "가게1",
                GeoPoint(userPoint.latitude + 0.00020, userPoint.longitude + 0.00020),
            ),
            MapStoreInfo(
                2L,
                "가게2",
                GeoPoint(userPoint.latitude + 0.000201, userPoint.longitude + 0.000201),
            ),
            MapStoreInfo(
                3L,
                "가게3",
                GeoPoint(userPoint.latitude + 0.000204, userPoint.longitude + 0.000204),
            ),
            MapStoreInfo(
                5L,
                "가게5",
                GeoPoint(userPoint.latitude + 0.000204, userPoint.longitude + 0.000204),
            ),
            MapStoreInfo(
                4L,
                "가게4",
                GeoPoint(userPoint.latitude - 0.0001, userPoint.longitude - 0.0001),
            ),
        )

        private fun fakeDataSearchedResult(userPoint: GeoPoint) = listOf(
            MapStoreInfo(
                1L,
                "가게1",
                GeoPoint(userPoint.latitude + 0.01, userPoint.longitude + 0.01),
            ),
            MapStoreInfo(
                2L,
                "가게2",
                GeoPoint(userPoint.latitude - 0.01, userPoint.longitude - 0.01),
            ),
            MapStoreInfo(
                3L,
                "가게3",
                GeoPoint(userPoint.latitude + 0.01, userPoint.longitude + 0.01),
            ),
            MapStoreInfo(
                4L,
                "가게4",
                GeoPoint(userPoint.latitude - 0.01, userPoint.longitude - 0.01),
            ),
            MapStoreInfo(
                5L,
                "가게5",
                GeoPoint(userPoint.latitude + 0.01, userPoint.longitude + 0.01),
            ),
            MapStoreInfo(
                6L,
                "가게6",
                GeoPoint(userPoint.latitude - 0.01, userPoint.longitude - 0.01),
            ),
            MapStoreInfo(
                7L,
                "가게7",
                GeoPoint(userPoint.latitude + 0.01, userPoint.longitude + 0.01),
            ),
            MapStoreInfo(
                8L,
                "가게8",
                GeoPoint(userPoint.latitude - 0.01, userPoint.longitude - 0.01),
            ),
        )
    }
}
