package com.example.throw_fornt.feature.store.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.data.model.request.Register
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.data.remote.retrofit.StoreRetrofit
import com.example.throw_fornt.databinding.ActivityRegisterBinding
import com.example.throw_fornt.feature.store.register.address.AddressActivity
import com.example.throw_fornt.feature.store.register.address.AddressResult
import com.example.throw_fornt.util.common.BindingActivity
import com.example.throw_fornt.util.common.Toaster
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : BindingActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    private val viewModel: RegisterViewModel by viewModels()

    private var lat: String = ""
    private var lon: String = ""

    //StoreRetrofit 변수
    private val storeHelper: StoreRetrofit = StoreRetrofit()
    val REQUEST_CODE: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.event.observe(this) { handleEvent(it) }
    }

    private fun handleEvent(event: RegisterViewModel.Event) {
        when (event) {
            is RegisterViewModel.Event.Search -> searchAddress()
            is RegisterViewModel.Event.Register -> register(event.register)
            is RegisterViewModel.Event.Fail -> Toaster.showLong(this@RegisterActivity, event.msg)
        }
    }

    //RegisterViewModel에서 전달받은 값을 가지고 등록함수를 호출
    private fun register(data: StoreModel) {
        //서버에 넘겨줘야되는 데이터를 위도경도를 추가하여 StoreRetrofit에 데이터 전달
        val res = StoreModel(
            "",
            data.storePhone,
            data.storeName,
            lat.toDouble(),
            lon.toDouble(),
            data.bno,
            data.zipCode,
            data.fullAddress,
            data.subAddress,
            data.trashType,
            "",
            ""
        )
        storeHelper.registerResponse()
        val body: Register
        body = Register(
            res.storePhone, res.bno, res.latitudes, res.longitude,
            res.zipCode, res.fullAddress + "(${res.subAddress})", res.trashType
        )

        StoreRetrofit.requestService.registerRequest(body).enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful && response.body().isNullOrEmpty()) {
                    Toast.makeText(this@RegisterActivity, "등록이 완료되었습니다.",Toast.LENGTH_SHORT).show()
                } else {
                    val jsonObject = JSONObject(response.errorBody()?.string())
                    viewModel.crn.value = ""
                    viewModel.changeSuccess(true, "인증")
                    Toaster.showShort(this@RegisterActivity, jsonObject.getString("message"))
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toaster.showShort(this@RegisterActivity, "접속 실패")
                //TODO 실패 토스트 넣기
            }
        })
    }

    //주소검색 함수
    private fun searchAddress() {
        val intent: Intent = Intent(this, AddressActivity::class.java)
        //AddressActivity와 쌍방향을 데이터를 전달받기위해서 startActivityForResult를 사용
        startActivityForResult(intent, REQUEST_CODE)
    }

    //AddressActivity에서 전달받는 값을 처리해준다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val receiveData = data?.getParcelableExtra<AddressResult>("data")
            if (receiveData != null) {
                binding.addressEdit.setText(receiveData.address.toString())
                binding.addressEdit2.setText(receiveData.zoneNo.toString())
                lat = receiveData.lat.toString()
                lon = receiveData.long.toString()
            }
        }
    }
}