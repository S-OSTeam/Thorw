package com.example.throw_fornt.feature.myPage.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.request.ProfileBody
import com.example.throw_fornt.data.model.response.MypageResponse
import com.example.throw_fornt.data.remote.retrofit.MypageRetrofit
import com.example.throw_fornt.util.common.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel: ViewModel() {
    private val mypageHelper = MypageRetrofit()
    private val _event: SingleLiveEvent<Event> = SingleLiveEvent()
    val event: LiveData<Event>
        get() = _event

    val phoneNumber: MutableLiveData<String> = MutableLiveData("")
    val email: MutableLiveData<String> = MutableLiveData("")
    val userName: MutableLiveData<String> = MutableLiveData("")

    fun profile(data: MypageResponse){
        phoneNumber.value = data.userPhoneNumber
        email.value = data.email
        userName.value = data.userName
    }
    fun cancel(){
        _event.value = Event.Cancel
    }

    fun modify(){
        val body: ProfileBody
        body = ProfileBody(phoneNumber.value.toString(), email.value.toString(), userName.value.toString())
        mypageHelper.mypageService.profileRequest(body).enqueue(object: Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful&&response.code()==200){
                    _event.value = Event.Modify("회원 정보가 변경 되었습니다.")
                }
                else _event.value = Event.Fail("수정 하지 못했습니다.")
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                _event.value = Event.Fail("인터넷 통신이 원할하지 않습니다.")
            }
        })
    }

    sealed class Event(){
        data class Modify(val msg: String): Event()
        data class Fail(val msg: String): Event()
        object Cancel: Event()
    }
}