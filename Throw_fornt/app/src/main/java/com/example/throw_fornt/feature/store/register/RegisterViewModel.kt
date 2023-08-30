package com.example.throw_fornt.feature.store.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.response.StoreResponse
import com.example.throw_fornt.data.model.response.testBody
import com.example.throw_fornt.data.remote.retrofit.StoreRetrofit
import com.example.throw_fornt.util.common.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    //StoreRetrofit 변수
    private val storeHelper: StoreRetrofit = StoreRetrofit()

    //인증완료 되었는지 사용자에게 눈으로 알려주기 위해 연결장치
    private val _bnoBtn: MutableLiveData<Boolean> = MutableLiveData(true)
    val bnoBtn: LiveData<Boolean>
        get() = _bnoBtn
    private val _btnText: MutableLiveData<String> = MutableLiveData("인증")
    val btnText: LiveData<String>
        get() = _btnText

    //Activity에 버튼 Event로 버튼을 실행시킴
    private val _event: SingleLiveEvent<Event> = SingleLiveEvent()
    val event: LiveData<Event>
        get() = _event

    //사업자등록번호 binding
    val bno: SingleLiveEvent<String> = SingleLiveEvent()

    //대표자명
    val ceoEdit: SingleLiveEvent<String> = SingleLiveEvent()


    //사업자등록번호 조회
    fun bnoInquire() {
        storeHelper.bnoResponse()
        test()
    }

    fun changeSuccess(btn: Boolean, text: String) {
        _bnoBtn.value = btn
        _btnText.value = text
    }

    //주소검색
    fun search() {
        _event.value = Event.Search
    }

    sealed class Event() {
        object Inquire : Event()
        object Search : Event()
        data class Fail(val msg: String) : Event()
    }

    private fun test(){
        val res = StoreRetrofit.requestService
        res.getRequest(StoreRetrofit.apiKey, "1", bno.value.toString(),"json").enqueue(object : Callback<testBody> {
            override fun onResponse(call: Call<testBody>, response: Response<testBody>) {
                if (response.isSuccessful) {
                    changeSuccess(false, "인증 완료")
                } else {
                    _event.value = Event.Fail("조회 실패")
                }
            }

            override fun onFailure(call: Call<testBody>, t: Throwable) {
                true
                _event.value = Event.Fail("조회실패")
            }
        })
    }
    private fun real(){
        //retorfit에 있는 requestService를 가져와서 비동기로 실행
        val res = StoreRetrofit.requestService
        res.bnoRequest(StoreRetrofit.apiKey,bno.value.toString()).enqueue(object : Callback<StoreResponse> {
            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                if (response.isSuccessful) {
                    changeSuccess(false, "인증 완료")
                } else {
                    _event.value = Event.Fail("조회 실패")
                }
            }

            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                true
                _event.value = Event.Fail("조회실패")
            }
        })
    }
}