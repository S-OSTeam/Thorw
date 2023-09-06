package com.example.throw_fornt.feature.forget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ForgetId2ViewModel: ViewModel() {

    val userId:MutableLiveData<String> = MutableLiveData("default")

    private val _event: MutableLiveData<ForgetId2ViewModel.Event> = MutableLiveData()
    val event: LiveData<ForgetId2ViewModel.Event>
        get() = _event

    //아이디를 확인했으니 로그인하러 가는 버튼
    fun goLoginBtn()
    {
        _event.value=Event.goLogin
    }



    sealed class Event {
        object goLogin : Event()
    }
}