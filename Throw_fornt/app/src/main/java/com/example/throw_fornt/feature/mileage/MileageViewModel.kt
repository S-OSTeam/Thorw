package com.example.throw_fornt.feature.mileage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.response.MileageResponse
import com.example.throw_fornt.data.remote.retrofit.MileageRetrofit
import com.example.throw_fornt.util.common.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MileageViewModel: ViewModel() {
    private val mileageHelper = MileageRetrofit()
    private val _event: SingleLiveEvent<Event> = SingleLiveEvent()
    val event: LiveData<Event>
        get() = _event

    private val _mileageMy: MutableLiveData<String> = MutableLiveData()
    val mileageMy: LiveData<String>
        get() = _mileageMy

    private val _rankMy: MutableLiveData<String> = MutableLiveData()
    val rankMy: LiveData<String>
        get() = _rankMy
    fun selectMileage(item: MileageResponse){
    }

    fun mileage(){
        mileageHelper.mileageService.mileageRequest().enqueue(object: Callback<MileageResponse>{
            override fun onResponse(
                call: Call<MileageResponse>,
                response: Response<MileageResponse>
            ) {
                if(response.isSuccessful&&response.code()==200){
                    _mileageMy.value = response.body()?.mileage.toString()+"P"
                    _rankMy.value = response.body()?.ranking.toString()+"위"
                    _event.value = Event.Mileage
                }
                else{

                }
            }

            override fun onFailure(call: Call<MileageResponse>, t: Throwable) {

            }
        })
    }
    fun rank(){
        mileageHelper.mileageService.rankingRequest().enqueue(object: Callback<List<MileageResponse>>{
            override fun onResponse(
                call: Call<List<MileageResponse>>,
                response: Response<List<MileageResponse>>
            ) {
                if(response.isSuccessful&&response.code()==200){
                    _event.value = Event.Ranking(response.body()!!)
                }
            }

            override fun onFailure(call: Call<List<MileageResponse>>, t: Throwable) {
            }
        })
    }

    sealed class Event(){
        data class Ranking(val items: List<MileageResponse>): Event()
        object Mileage: Event()
    }
}