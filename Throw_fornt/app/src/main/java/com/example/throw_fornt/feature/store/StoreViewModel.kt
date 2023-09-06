package com.example.throw_fornt.feature.store

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.data.model.response.StoreResponse
import com.example.throw_fornt.data.remote.retrofit.StoreRetrofit
import com.example.throw_fornt.util.common.SingleLiveEvent
import com.example.throw_fornt.util.common.Toaster
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel : ViewModel() {
    val storeHelper: StoreRetrofit = StoreRetrofit()

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

    fun storeList(){
        storeHelper.storeService.storeRequest().enqueue(object: Callback<ArrayList<StoreModel>?>{
            override fun onResponse(call: Call<ArrayList<StoreModel>?>, response: Response<ArrayList<StoreModel>?>) {
                if(response.isSuccessful&&response.body() != null){
                    event.value = Event.StoreList(response.body()!!)
                }
                else{
                    event.value = Event.BlankList
                }
            }

            override fun onFailure(call: Call<ArrayList<StoreModel>?>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    sealed class Event(){
        object Register: Event()
        data class Service(val item: StoreModel): Event()
        data class StoreList(val item: ArrayList<StoreModel>?): Event()
        object BlankList: Event()
    }
}