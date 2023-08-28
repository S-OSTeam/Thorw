package com.example.throw_fornt.feature.store.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.response.testBody
import com.example.throw_fornt.data.remote.retrofit.StoreRetrofit
import com.example.throw_fornt.data.repository.store.RegisterRepository
import com.example.throw_fornt.util.common.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel()  {
    //bno버튼을 비활성화 및 text문구를 바꾸기위한 repository연결 변수
    private val bnoRepository = RegisterRepository()
    //StoreRetrofit 변수
    private val storeHelper: StoreRetrofit by lazy{ StoreRetrofit.getInstance() }

    //인증완료 되었는지 사용자에게 눈으로 알려주기 위해 연결장치
    private val _bnoBtn: MutableLiveData<Boolean> = MutableLiveData(bnoRepository.btn)
    val bnoBtn: LiveData<Boolean>
        get() = _bnoBtn
    private val _btnText: MutableLiveData<String> = MutableLiveData(bnoRepository.text)
    val btnText: LiveData<String>
        get() = _btnText

    //Activity에 버튼 Event로 버튼을 실행시킴
    private val _event: SingleLiveEvent<Event> = SingleLiveEvent()
    val event: LiveData<Event>
        get() = _event

    val bno: MutableLiveData<String> = MutableLiveData()
    val bnoEdit: SingleLiveEvent<String> = SingleLiveEvent()


    //사업자등록번호 조회
    fun bnoInquire(){
        storeHelper.bnoResponse()
        try {
            val res = StoreRetrofit.requestService
            res.testRequest(
                StoreRetrofit.apiKey, "1", "0000000000", "json"
            ).enqueue(object : Callback<testBody> {
                override fun onResponse(call: Call<testBody>, response: Response<testBody>) {
                    if (response.isSuccessful && response.body()?.resultCode == "0") {
                        if(bnoRepository.success()) return changeSuccess(bnoRepository.btn, bnoRepository.text)
                    //_event.value = Event.Inquire
                    } else {
                        //_event.value = Event.Fail("조회 실패")
                    }
                }

                override fun onFailure(call: Call<testBody>, t: Throwable) {
                    true
                    //_event.value = Event.Fail("조회실패")
                }
            })
        }catch (e: Exception){

        }
    }

    fun changeSuccess(btn: Boolean, text: String){
        _bnoBtn.value = btn
        _btnText.value = text
    }

    //주소검색
    fun search(){

    }

    sealed class Event(){
        object Inquire: Event()
        //object Search: Event()
        data class Fail(val msg: String): Event()
    }
}