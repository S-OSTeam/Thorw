package com.example.throw_fornt.feature.store.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.ActivityRegisterBinding
import com.example.throw_fornt.util.common.BindingActivity
import com.example.throw_fornt.util.common.Toaster

class RegisterActivity : BindingActivity<ActivityRegisterBinding>(R.layout.activity_register) {
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.event.observe(this){handleEvent(it)}
    }

    private fun handleEvent(event: RegisterViewModel.Event){
        when(event){
            is RegisterViewModel.Event.Inquire -> successBno()
            is RegisterViewModel.Event.Fail -> Toaster.showLong(this@RegisterActivity, event.msg)
        }
    }

    private fun successBno(){
    }
}