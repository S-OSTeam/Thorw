package com.example.throw_fornt.feature.map.storeInfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.models.GeoPoint
import com.example.throw_fornt.models.MapStoreInfo
import com.example.throw_fornt.util.common.SingleLiveEvent

class MapStoreInfoViewModel : ViewModel() {
    private val _event: SingleLiveEvent<Event> = SingleLiveEvent()
    val event: LiveData<Event>
        get() = _event

    fun onStoreCall(mapStoreInfo: MapStoreInfo) {
        _event.value = Event.NavigateToCall("010-7658-7639")
    }

    fun onNavigatorStart(mapStoreInfo: MapStoreInfo) {
        _event.value = Event.NavigateToDestination(mapStoreInfo.geoPoint)
    }

    sealed class Event {
        object Dismiss : Event()
        data class NavigateToCall(val phoneNumber: String) : Event()
        data class NavigateToDestination(val destination: GeoPoint) : Event()
    }
}
