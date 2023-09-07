package com.example.throw_fornt.feature.myPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.request.MypageRequest
import com.example.throw_fornt.data.model.response.MypageResponse
import com.example.throw_fornt.data.remote.retrofit.MypageRetrofit
import com.example.throw_fornt.data.remote.retrofit.StoreRetrofit
import com.example.throw_fornt.util.common.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageViewModel : ViewModel() {
    //MypageRetrofit 변수
    private val mypageHelper: MypageRetrofit = MypageRetrofit()
    private val _event: SingleLiveEvent<Event> = SingleLiveEvent()
    lateinit var myProfile: MypageResponse
    val event: LiveData<Event>
        get() = _event

    private val _name: SingleLiveEvent<String> = SingleLiveEvent()
    val name: LiveData<String>
        get() = _name

    private val _email: SingleLiveEvent<String> = SingleLiveEvent()
    val email: LiveData<String>
        get() = _email

    private val _mileage: SingleLiveEvent<String> = SingleLiveEvent()
    val mileage: LiveData<String>
        get() = _mileage

    //가게관리 버튼이벤트
    fun store(){
        _event.value = Event.Store
    }

    //마일리지/상품 구매 버튼이벤트
    fun mileage(){
        _event.value = Event.Mileage
    }

    fun profileModify(){

        _event.value = Event.Profile(myProfile)
    }

    //회원정보 조회
    fun inquiry(){
        mypageHelper.mypageService.memberRequest().enqueue(object: Callback<MypageResponse> {
            override fun onResponse(call: Call<MypageResponse>, response: Response<MypageResponse>) {
                if(response.isSuccessful&&response.body()!=null){
                    _name.value = response.body()?.userName
                    _email.value = response.body()?.email
                    _mileage.value = response.body()?.mileage
                    val profile = MypageResponse(response.body()?.inputId.toString(), response.body()?.role.toString(),
                        response.body()?.userName.toString(),response.body()?.userPhoneNumber.toString(),
                        response.body()?.email.toString(),response.body()?.mileage.toString())
                    myProfile = profile
                }
                else{

                }
            }

            override fun onFailure(call: Call<MypageResponse>, t: Throwable) {
                true
            }
        })
    }

    sealed class Event(){
        object Store: Event()
        object Mileage: Event()
        data class Profile(val profile: MypageResponse): Event()
    }
}