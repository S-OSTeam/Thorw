package com.example.throw_fornt.feature.store.register.address

import android.os.Parcel
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.response.AddressResponse
import com.example.throw_fornt.data.model.response.DocumentResponse
import com.example.throw_fornt.data.remote.retrofit.AddressRetrofit
import com.example.throw_fornt.feature.map.MapViewModel
import com.example.throw_fornt.models.MapStoreInfo
import com.example.throw_fornt.util.common.SingleLiveEvent
import org.w3c.dom.Document
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressViewModel : ViewModel() {
    //AddressRetrofit 변수
    private val addressHelper: AddressRetrofit = AddressRetrofit()

    //클릭 이벤트 데이터 바인딩
    private val _event: SingleLiveEvent<Event> = SingleLiveEvent()
    val event: LiveData<Event>
        get() = _event

    fun search(content: String) {
        addressHelper.searchAddress()
        val res = AddressRetrofit.requestService
        res.getSearchAddress("KakaoAK c5ed2fbd6ebc9ee453bd9378c14b4f8d", content).enqueue(object :
            Callback<AddressResponse> {
            override fun onResponse(
                call: Call<AddressResponse>,
                response: Response<AddressResponse>
            ) {
                if (response.isSuccessful) {
                    _event.value = Event.Search(response.body()?.documents!!)
                } else _event.value = Event.Fail("검색실패")
            }

            override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                _event.value = Event.Fail("검색실패")
            }

        })
    }

    val searchAddress = { content: String? ->
        if (content.isNullOrEmpty().not()) {
            search(content.toString())
        }
    }

    fun selectAddress(selectItem: DocumentResponse) {
        if (selectItem.address == null || selectItem.roadAddress == null)
            _event.value = Event.Fail("잘못된 주소를 선택했습니다.")
        else _event.value = Event.Select(selectItem)
    }

    sealed class Event {
        data class Search(val items: List<DocumentResponse>) : Event()
        data class Fail(val msg: String) : Event()
        data class Select(val item: DocumentResponse): Event()
    }
}

//RegisterAcivity에 넘기고 싶은 값의 데이터 클래스
data class AddressResult(
    val address: String,
    val zoneNo: String,
    val lat: String,
    val long: String,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(address)
        parcel.writeString(zoneNo)
        parcel.writeString(lat)
        parcel.writeString(long)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddressResult> {
        override fun createFromParcel(parcel: Parcel): AddressResult {
            return AddressResult(parcel)
        }

        override fun newArray(size: Int): Array<AddressResult?> {
            return arrayOfNulls(size)
        }
    }

}