package com.example.throw_fornt.feature.forget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ForgetViewModel: ViewModel() {

    private val _event: MutableLiveData<ForgetViewModel.Event> = MutableLiveData()
    val event: LiveData<ForgetViewModel.Event>
        get() = _event

    //아이디 찾으러 가는 창으로 넘어가짐
    fun searchId()
    {
        _event.value=Event.forgetId
    }

    //비밀번호 찾으러 가는 창으로 넘어가짐
    fun searchPw()
    {
        _event.value=Event.forgetPw
    }

    sealed class Event {
        object forgetId : Event()
        object forgetPw : Event()
    }
}