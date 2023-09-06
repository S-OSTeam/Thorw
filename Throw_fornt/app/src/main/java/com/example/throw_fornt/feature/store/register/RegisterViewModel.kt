package com.example.throw_fornt.feature.store.register

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.request.Register
import com.example.throw_fornt.data.model.request.StoreRequest
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.data.model.response.StoreResponse
import com.example.throw_fornt.data.model.response.testBody
import com.example.throw_fornt.data.remote.retrofit.StoreRetrofit
import com.example.throw_fornt.util.common.SingleLiveEvent
import com.example.throw_fornt.util.common.Toaster
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.URL

class RegisterViewModel : ViewModel() {
    private val errorMsg: String = "칸이 비어있습니다."
    var lat: String = ""
    var lon: String = ""

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

    //가게 전호번호 binding
    val storePhone: MutableLiveData<String> = MutableLiveData("")

    //지번 주소 binding
    val fullAddress: MutableLiveData<String> = MutableLiveData("")

    //상세 주소
    val subAddress: MutableLiveData<String> = MutableLiveData("")

    //우편번호
    val zoneNo: MutableLiveData<String> = MutableLiveData("")

    //일반쓰레기
    val general: MutableLiveData<Boolean> = MutableLiveData(false)

    //병
    val bottle: MutableLiveData<Boolean> = MutableLiveData(false)

    //플라스틱
    val plastic: MutableLiveData<Boolean> = MutableLiveData(false)

    //종이
    val paper: MutableLiveData<Boolean> = MutableLiveData(false)

    //캔
    val can: MutableLiveData<Boolean> = MutableLiveData(false)


    //사업자등록번호 조회
    fun bnoInquire() {
        //real()
        test()
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
    fun register() {
        var trashCode: String = ""
        trashCode = checkBoxClick()
        //edit에 값이 비어있는지 확인하기 위한 처리
        if (crn.value.isNullOrEmpty()) _event.value = Event.Fail("사용자 등록번호 ${errorMsg}")
        else if (storePhone.value.isNullOrEmpty()) _event.value = Event.Fail("전화번호 ${errorMsg}")
        else if (fullAddress.value.isNullOrEmpty()) _event.value = Event.Fail("지번주소 ${errorMsg}")
        else if (zoneNo.value.isNullOrEmpty()) _event.value =
            Event.Fail("우편번호 ${errorMsg}\n지번주소를 한번 더 확인해주세요.")
        else {
            if (subAddress.value.isNullOrEmpty()) subAddress.value = ""

            //storeHelper.registerResponse()
            val body: Register
            body = Register(
                storePhone.value.toString(), crn.value.toString(), lat.toDouble(), lon.toDouble(),
                zoneNo.value.toString(), fullAddress.value.toString() + "(${subAddress.value.toString()})", trashCode
            )

            storeHelper.storeService.registerRequest(body).enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    if (response.isSuccessful && response.body()!=null) {
                        _event.value = Event.Register("등록이 완료되었습니다.")
                    } else {
                        val jsonObject = JSONObject(response.errorBody()?.string())
                        crn.value = ""
                        changeSuccess(true, "인증")
                        _event.value = Event.Fail(jsonObject.getString("message"))
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    _event.value = Event.Fail("접속실패")
                }
            })
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

    sealed class Event() {
        object Search : Event()
        data class Fail(val msg: String) : Event()
        data class Register(val msg: String) : Event()
    }

    //사업자 등록번호 조회 test모드
    private fun test() {
        lateinit var requestService: StoreRequest
        try {
            val urls = URL("https://bizno.net/api/")
            val retrofit = Retrofit.Builder()
                .baseUrl(urls)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            requestService = retrofit.create(StoreRequest::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        requestService.getRequest("ZG1sdG4zNDI2QGdtYWlsLmNvbSAg", "1", crn.value.toString(), "json")
            .enqueue(object : Callback<testBody> {
                override fun onResponse(call: Call<testBody>, response: Response<testBody>) {
                    if (response.isSuccessful && response.body()?.items!!.size>0) {
                        changeSuccess(false, "인증 완료")
                    } else {
                        crn.value = ""
                        _event.value = Event.Fail("조회 실패")
                    }
                }

                override fun onFailure(call: Call<testBody>, t: Throwable) {
                    crn.value = ""
                    _event.value = Event.Fail("조회실패")
                }
            })
    }

    //사업자 등록번호 조회 real모드
    private fun real() {
        //retorfit에 있는 requestService를 가져와서 비동기로 실행
        val res = storeHelper.storeService
        val body = HashMap<String, String>()
        body["crn"] = crn.value.toString()

        res.bnoRequest(body)
            .enqueue(object : Callback<StoreModel> {
                override fun onResponse(call: Call<StoreModel>, response: Response<StoreModel>) {
                    if (response.isSuccessful && response.body()!=null) {
                        changeSuccess(false, "인증 완료")
                    } else {
                        crn.value = ""
                        _event.value = Event.Fail("조회 실패")
                    }
                }

                override fun onFailure(call: Call<StoreModel>, t: Throwable) {
                    crn.value = ""
                    _event.value = Event.Fail("조회실패")
                }
            })
    }
}