package com.example.throw_fornt.feature.store.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.data.model.response.StoreResponse
import com.example.throw_fornt.data.model.response.testBody
import com.example.throw_fornt.data.remote.retrofit.StoreRetrofit
import com.example.throw_fornt.util.common.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private val errorMsg: String = "칸이 비어있습니다."
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
    val crn: MutableLiveData<String> = MutableLiveData("")
    //대표자명 binding
    val ceoEdit: MutableLiveData<String> = MutableLiveData("")
    //가게 전호번호 binding
    val storePhone: MutableLiveData<String> = MutableLiveData("")
    //지번 주소 binding
    val fullAddress: MutableLiveData<String> = MutableLiveData("")
    //상세 주소
    val subAddress: MutableLiveData<String> = MutableLiveData("")
    //우편번호
    val zoneNo: MutableLiveData<String> = MutableLiveData("")


    //사업자등록번호 조회
    fun bnoInquire() {
        storeHelper.bnoResponse()
        //real()
        real()
    }

    fun changeSuccess(btn: Boolean, text: String) {
        _bnoBtn.value = btn
        _btnText.value = text
    }

    //주소 입력 edit를 클릭시 발생된다.
    fun search() {
        _event.value = Event.Search
    }

    //가게등록
    fun register(){
        if(crn.value.isNullOrEmpty()) _event.value = Event.Fail("사용자 등록번호 ${errorMsg}")
        else if(ceoEdit.value.isNullOrEmpty()) _event.value = Event.Fail("대표자명이 없습니다.")
        else if(storePhone.value.isNullOrEmpty()) _event.value = Event.Fail("전화번호 ${errorMsg}")
        else if(fullAddress.value.isNullOrEmpty()) _event.value = Event.Fail("지번주소 ${errorMsg}")
        else if(zoneNo.value.isNullOrEmpty()) _event.value = Event.Fail("우편번호 ${errorMsg}\n지번주소를 한번 더 확인해주세요.")
        else if(subAddress.value.isNullOrEmpty()) subAddress.value = ""
        else{
            val data = StoreModel(
                storePhone.value.toString(),"","",crn.value.toString(),
                zoneNo.value.toString(), fullAddress.value.toString()+subAddress.value.toString(), "", ""
            )
            _event.value = Event.Register(data)
        }
    }

    sealed class Event() {
        object Search : Event()
        data class Fail(val msg: String) : Event()
        data class Register(val register: StoreModel): Event()
    }

    private fun test(){
        val res = StoreRetrofit.requestService
        res.getRequest(StoreRetrofit.apiKey, "1", crn.value.toString(),"json").enqueue(object : Callback<testBody> {
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
        res.bnoRequest(StoreRetrofit.apiKey,crn.value.toString()).enqueue(object : Callback<StoreResponse> {
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