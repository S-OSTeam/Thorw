package com.example.throw_fornt.feature.store.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.ActivityRegisterBinding
import com.example.throw_fornt.feature.store.register.address.AddressActivity
import com.example.throw_fornt.feature.store.register.address.AddressResult
import com.example.throw_fornt.util.common.BindingActivity
import com.example.throw_fornt.util.common.Toaster

class RegisterActivity : BindingActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    private val viewModel: RegisterViewModel by viewModels()
    private var lat:String = ""
    private var lon:String = ""
    val REQUEST_CODE: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.event.observe(this){handleEvent(it)}
    }

    private fun handleEvent(event: RegisterViewModel.Event){
        when(event){
            is RegisterViewModel.Event.Inquire -> successBno()
            is RegisterViewModel.Event.Search -> searchAddress()
            is RegisterViewModel.Event.Fail -> Toaster.showLong(this@RegisterActivity, event.msg)
        }
    }

    private fun successBno(){
    }

    private fun searchAddress(){
        val intent: Intent = Intent(this, AddressActivity::class.java)
        //startActivity(intent)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== Activity.RESULT_OK){
            val receiveData = data?.getParcelableExtra<AddressResult>("data")
            if(receiveData!=null){
                binding.addressEdit.setText(receiveData.address.toString())
                binding.addressEdit2.setText(receiveData.zoneNo.toString())
                lat = receiveData.lat.toString()
                lon = receiveData.long.toString()
            }
        }
    }
}