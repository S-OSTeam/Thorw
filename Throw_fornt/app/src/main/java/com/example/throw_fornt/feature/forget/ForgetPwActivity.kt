package com.example.throw_fornt.feature.forget

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.ActivityForgetPwBinding
import com.example.throw_fornt.util.common.BindingActivity

class ForgetPwActivity :BindingActivity<ActivityForgetPwBinding>(R.layout.activity_forget_pw) {

    private val viewModel:ForgetPwViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel=viewModel
        viewModel.event.observe(this){handleEvent(it)}
    }
    private fun handleEvent(event:ForgetPwViewModel.Event)
    {
        when(event)
        {
            is ForgetPwViewModel.Event.nextPage->{
                val intent= Intent(this,ForgetPw2Activity::class.java)
                startActivity(intent)
            }
        }

    }
}