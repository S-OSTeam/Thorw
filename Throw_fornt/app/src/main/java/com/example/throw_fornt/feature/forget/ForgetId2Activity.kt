package com.example.throw_fornt.feature.forget

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.ActivityForgetId2Binding
import com.example.throw_fornt.feature.login.LoginActivity
import com.example.throw_fornt.util.common.BindingActivity

class ForgetId2Activity : BindingActivity<ActivityForgetId2Binding>(R.layout.activity_forget_id2) {
    private val viewModel:ForgetId2ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.event.observe(this){handleEvent(it)}
    }

    private fun handleEvent(event:ForgetId2ViewModel.Event){
        when(event)
        {
            is ForgetId2ViewModel.Event.goLogin->{
                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}