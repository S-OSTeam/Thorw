package com.example.throw_fornt.feature.forget

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.ActivityForgetBinding
import com.example.throw_fornt.util.common.BindingActivity

class ForgetActivity :BindingActivity<ActivityForgetBinding>(R.layout.activity_forget) {
    private val viewModel:ForgetViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel

        viewModel.event.observe(this){handleEvent(it)}
    }
    private fun handleEvent(event: ForgetViewModel.Event) {
        when (event) {
            is ForgetViewModel.Event.forgetId -> {
                val intent= Intent(this,ForgetIdActivity::class.java)
                startActivity(intent)
            }
            is ForgetViewModel.Event.forgetPw->{
                val intent=Intent(this,ForgetPwActivity::class.java)
                startActivity(intent)
            }

        }
    }
}