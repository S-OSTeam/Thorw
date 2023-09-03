package com.example.throw_fornt.feature.store.management

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.data.model.response.testBody
import com.example.throw_fornt.data.remote.retrofit.StoreRetrofit
import com.example.throw_fornt.feature.store.register.RegisterViewModel
import com.example.throw_fornt.util.common.SingleLiveEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManagementViewModel(): ViewModel() {
    private val errorMsg: String = "칸이 비어있습니다."
    private val _event: SingleLiveEvent<Event> = SingleLiveEvent()
    //StoreRetrofit 변수
    private val storeHelper: StoreRetrofit = StoreRetrofit()
    //사업자등록번호가 바뀌었는지 확인하는변수
    private var checkCrn: String = ""
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

    //일반쓰레기
    val general: MutableLiveData<Boolean> = MutableLiveData()

    //병
    val bottle: MutableLiveData<Boolean> = MutableLiveData()

    //플라스틱
    val plastic: MutableLiveData<Boolean> = MutableLiveData()

    //종이
    val paper: MutableLiveData<Boolean> = MutableLiveData()

    //캔
    val can: MutableLiveData<Boolean> = MutableLiveData()

    //binding된 editText의 text값을 변경해주기 위한 함수
    fun selectItem(item: StoreModel){
        checkCrn = item.bno.toString()
        crn.value = item.bno
        //내 정보 조회가 안되서 테스트 데이터
        ceoEdit.value = "김국밥"
        storePhone.value = item.storePhone
        fullAddress.value = item.fullAddress
        subAddress.value = item.subAddress
        zoneNo.value = item.zipCode
        trashType(item.trashType)
    }

    //쓰레기 종류 Check Box 형태로 변경
    fun trashType(code: String){
        general.value = if(code[0]==('1')) true else false
        bottle.value = if(code[1]==('1')) true else false
        plastic.value = if(code[2]==('1')) true else false
        paper.value = if(code[3]==('1')) true else false
        can.value = if(code[4]==('1')) true else false
    }

    //사업자등록번호 조회
    fun bnoInquire() {
        storeHelper.bnoResponse()
        real()
        //test()
    }

    //사업자 등록번호 조회 성공시 인증완료로 변경
    fun changeSuccess(btn: Boolean, text: String) {
        _bnoBtn.value = btn
        _btnText.value = text
    }

    //가게 수정
    fun modify(){
        var trashCode: String = ""
        trashCode = checkBoxClick()
        //edit에 값이 비어있는지 확인하기 위한 처리
        if (crn.value.isNullOrEmpty()) _event.value = Event.Fail("사용자 등록번호 ${errorMsg}")
        else if(checkCrn!=crn.value && _btnText.value.equals("인증완료").not()) _event.value = Event.Fail("사업자 등록번호 인증확인 부탁드립니다.")
        else if (storePhone.value.isNullOrEmpty()) _event.value = Event.Fail("전화번호 ${errorMsg}")
        else if (fullAddress.value.isNullOrEmpty()) _event.value = Event.Fail("지번주소 ${errorMsg}")
        else if (zoneNo.value.isNullOrEmpty()) _event.value =
            Event.Fail("우편번호 ${errorMsg}\n지번주소를 한번 더 확인해주세요.")
        else {
            if (subAddress.value.isNullOrEmpty()) subAddress.value = ""

            //edit에 값이 비어있지 않으면 data값을 담아서 RegisterActivity에 전달
            val data = StoreModel("",
                storePhone.value.toString(), "", "","", crn.value.toString(),
                zoneNo.value.toString(), fullAddress.value.toString(), subAddress.value.toString(),
                trashCode, "", "",
            )
            _event.value = Event.Modify(data)
        }
    }

    //trashType 예)10100으로 변환 (일쓰->병->플라스틱->종이->캔 순서)
    fun checkBoxClick(): String{
        var trashCode:String = ""
        trashCode += if(general.value!!) "1" else "0"
        trashCode += if(bottle.value!!) "1" else "0"
        trashCode += if(plastic.value!!) "1" else "0"
        trashCode += if(paper.value!!) "1" else "0"
        trashCode += if(can.value!!) "1" else "0"
        return trashCode
    }
    sealed class Event(){
        data class Modify(val modify: StoreModel): Event()
        data class Fail(val msg: String): Event()
    }

    //사업자 등록번호 조회 test모드
    private fun test() {
        val res = StoreRetrofit.requestService
        res.getRequest(StoreRetrofit.apiKey, "1", crn.value.toString(), "json")
            .enqueue(object : Callback<testBody> {
                override fun onResponse(call: Call<testBody>, response: Response<testBody>) {
                    if (response.isSuccessful && response.body()?.items!!.size>0) {
                        changeSuccess(false, "인증 완료")
                    } else {
                        crn.value = ""
                    }
                }

                override fun onFailure(call: Call<testBody>, t: Throwable) {
                    crn.value = ""
                }
            })
    }

    //사업자 등록번호 조회 real모드
    private fun real() {
        //retorfit에 있는 requestService를 가져와서 비동기로 실행

        val res = StoreRetrofit.requestService
        res.bnoRequest(crn.value.toString())
            .enqueue(object : Callback<StoreModel> {
                override fun onResponse(call: Call<StoreModel>, response: Response<StoreModel>) {
                    if (response.isSuccessful && response.body()?.code=="200") {
                        changeSuccess(false, "인증 완료")
                    } else {
                        crn.value = ""
                    }
                }

                override fun onFailure(call: Call<StoreModel>, t: Throwable) {
                    crn.value = ""
                }
            })
    }
}