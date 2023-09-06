package com.example.throw_fornt.feature.forget

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.ActivityForgetPw2Binding
import com.example.throw_fornt.feature.login.LoginActivity
import com.example.throw_fornt.util.common.BindingActivity

class ForgetPw2Activity : BindingActivity<ActivityForgetPw2Binding>(R.layout.activity_forget_pw2){
    private val viewModel:ForgetPw2ViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel=viewModel
        viewModel.event.observe(this){handleEvent(it)}
    }
    private fun handleEvent(event:ForgetPw2ViewModel.Event)
    {
        when(event)
        {
            is ForgetPw2ViewModel.Event.nextPage ->{
                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

}