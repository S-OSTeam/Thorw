package com.example.throw_fornt.feature.store.management

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.util.common.SingleLiveEvent

class ManagementViewModel(): ViewModel() {
    private val _event: SingleLiveEvent<Event> = SingleLiveEvent()
    val event:LiveData<Event>
        get() = _event
    //인증완료 되었는지 사용자에게 눈으로 알려주기 위해 연결장치
    private val _bnoBtn: MutableLiveData<Boolean> = MutableLiveData(true)
    val bnoBtn: LiveData<Boolean>
        get() = _bnoBtn
    private val _btnText: MutableLiveData<String> = MutableLiveData("인증")
    val btnText: LiveData<String>
        get() = _btnText

    //사업자등록번호 binding
    val crn: MutableLiveData<String> = MutableLiveData()
    //대표자명 binding
    val ceoEdit: MutableLiveData<String> = MutableLiveData()
    //가게 전호번호 binding
    val storePhone: MutableLiveData<String> = MutableLiveData()
    //지번 주소 binding
    val fullAddress: MutableLiveData<String> = MutableLiveData()
    //상세 주소
    val subAddress: MutableLiveData<String> = MutableLiveData()
    //우편번호
    val zoneNo: MutableLiveData<String> = MutableLiveData()

    fun selectItem(item: StoreModel){
        crn.value = item.bno
        ceoEdit.value = "김국밥"
        storePhone.value = item.storePhone
        fullAddress.value = item.fullAddress
        subAddress.value = "101호"
        zoneNo.value = item.zipCode
    }
    sealed class Event(){
        object Modify: Event()
    }
}