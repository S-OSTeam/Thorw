package com.example.throw_fornt.feature.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.request.StoreSearchNameForMapRequest
import com.example.throw_fornt.data.model.request.StoresInfoByLocationRequest
import com.example.throw_fornt.data.model.response.StoreInfoBySearchNameResponse
import com.example.throw_fornt.data.model.response.StoresInfoByLocationResponse
import com.example.throw_fornt.data.remote.retrofit.StoreRetrofit
import com.example.throw_fornt.models.GeoPoint
import com.example.throw_fornt.models.MapStoreInfo
import com.example.throw_fornt.models.Trash
import com.example.throw_fornt.util.common.SingleLiveEvent
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    private val _refreshStoreLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val refreshStoreLoading: LiveData<Boolean>
        get() = _refreshStoreLoading

    private val refreshStoreLoadingValue: Boolean
        get() = _refreshStoreLoading.value!!

    private fun startLoading() {
        _refreshStoreLoading.value = true
    }

    private fun stopLoading() {
        _refreshStoreLoading.value = false
    }

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
        Log.d("mendel", "맵외곽 보이는 거리: $visibleMapMinQuarterDistance")
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
        if (refreshStoreLoadingValue) return
        _lastUserPoint.value?.let {
            startLoading()
            StoreRetrofit().storeService.getStoresInfoByLocation(
                StoresInfoByLocationRequest(
                    it.latitude,
                    it.longitude,
                    4.toDouble(),
                    (selectedTrashTypes.value ?: listOf(Trash.ALL)).toBinaryFormat(),
                ),
            ).enqueue(object : Callback<List<StoresInfoByLocationResponse>> {
                override fun onResponse(
                    call: Call<List<StoresInfoByLocationResponse>>,
                    response: Response<List<StoresInfoByLocationResponse>>,
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        _nearByStores.value =
                            response.body()?.map { it.toUI() }?.groupStoresByGeoPoint()
                                ?.mapKeys { it.key.hashCode() } ?: mapOf()
                        _event.value = Event.ToastMessage("가게목록을 갱신에 성공했습니다.")
                    } else if (response.isSuccessful) {
                        _event.value = Event.ToastMessage("주변에 가게가 존재하지 않습니다.")
                    } else {
                        _event.value = Event.ToastMessage("가게 목록을 불러오는 과정에서 문제가 발생했습니다.")
                    }
                    stopLoading()
                }

                override fun onFailure(
                    call: Call<List<StoresInfoByLocationResponse>>,
                    t: Throwable,
                ) {
                    _event.value = Event.ToastMessage("가게목록을 갱신과정에서 실패했습니다.")
                    stopLoading()
                }
            })
        }
    }

    private fun List<MapStoreInfo>.groupStoresByGeoPoint(precision: Int = DEFAULT_PRECISION): Map<GeoPoint, List<MapStoreInfo>> {
        val groupedStores = mutableMapOf<GeoPoint, MutableList<MapStoreInfo>>()

        for (store in this) {
            val roundedPoint = store.geoPoint.adjustGeoPoint(precision)
            Log.d("mendel", "${store.storeName}, 조정된 값: $roundedPoint")

            var addToGroup = false
            groupedStores.keys.forEach { keyGeoPoint ->
                if (roundedPoint.latitude in keyGeoPoint.latitude - ADJUST_ERROR_RANGE..keyGeoPoint.latitude + ADJUST_ERROR_RANGE &&
                    roundedPoint.longitude in keyGeoPoint.longitude - ADJUST_ERROR_RANGE..keyGeoPoint.longitude + ADJUST_ERROR_RANGE
                ) {
                    groupedStores[keyGeoPoint]?.add(store)
                    addToGroup = true
                    return@forEach
                }
            }

            if (addToGroup.not()) {
                groupedStores[roundedPoint] = mutableListOf(store)
            }
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

    private fun List<Trash>.toBinaryFormat(): String {
        return buildString {
            if (contains(Trash.ALL)) {
                repeat(Trash.values().size - 1) { append("0") }
                return@buildString
            }

            Trash.values().filterNot { it == Trash.ALL }.forEach { trash ->
                if (contains(trash)) {
                    append("1")
                } else {
                    append("0")
                }
            }
        }
    }

    fun selectMapPoint(groupKey: Int) {
        nearByStores.value?.let { nearByStores ->
            val selectedStoreGroup = nearByStores[groupKey] ?: return@let
            searchedStores = selectedStoreGroup
        }
    }

    val searchStores = { content: String ->
        lastUserPoint.value?.let {
            if (refreshStoreLoadingValue) return@let
            startLoading()
            StoreRetrofit().storeService.getStoresInfoBySearchName(
                StoreSearchNameForMapRequest(content.trim()),
            ).enqueue(object : Callback<List<StoreInfoBySearchNameResponse>> {
                override fun onResponse(
                    call: Call<List<StoreInfoBySearchNameResponse>>,
                    response: Response<List<StoreInfoBySearchNameResponse>>,
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        searchedStores = response.body()?.map { it.toUI() } ?: return stopLoading()
                    } else if (response.isSuccessful) {
                        Event.ToastMessage("검색된 이름의 가게가 존재하지 않습니다.")
                    } else {
                        runCatching {
                            val jsonObject = JSONObject(response.errorBody()!!.string())
                            jsonObject.getString("message")
                        }.onSuccess { message ->
                            _event.value = Event.ToastMessage(message)
                        }.onFailure {
                            _event.value = Event.ToastMessage("가게 검색에 실패했습니다")
                        }
                    }
                    stopLoading()
                }

                override fun onFailure(
                    call: Call<List<StoreInfoBySearchNameResponse>>,
                    t: Throwable,
                ) {
                    _event.value = Event.ToastMessage("가게 검색에 실패했습니다")
                    stopLoading()
                }
            })
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
        data class ToastMessage(val message: String) : Event()
    }

    companion object {
        private const val DEFAULT_PRECISION = 5
        private const val ADJUST_ERROR_RANGE = 0.00001
    }
}
