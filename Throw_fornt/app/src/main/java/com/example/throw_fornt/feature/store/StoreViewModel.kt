package com.example.throw_fornt.feature.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.data.model.response.StoreResponse
import com.example.throw_fornt.util.common.SingleLiveEvent
import com.example.throw_fornt.util.common.Toaster

class StoreViewModel : ViewModel() {

    val event: SingleLiveEvent<Event> = SingleLiveEvent()

//    private val _btn: MutableLiveData<Boolean> = MutableLiveData()
//
//    val btn: LiveData<Boolean>
//        get() = _btn

    fun register(){
        event.value = Event.Register
    }
    fun service(storeitem: StoreModel){
        event.value = Event.Service(storeitem)
    }
    sealed class Event(){
        object Register: Event()
        data class Service(val item: StoreModel): Event()
    }
}