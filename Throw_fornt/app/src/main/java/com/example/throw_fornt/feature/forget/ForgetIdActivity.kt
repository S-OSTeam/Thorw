package com.example.throw_fornt.feature.forget

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.ActivityForgetIdBinding
import com.example.throw_fornt.util.common.BindingActivity

class ForgetIdActivity : BindingActivity<ActivityForgetIdBinding> (R.layout.activity_forget_id) {
    private val viewModel: ForgetIdViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel=viewModel
        viewModel.event.observe(this){handleEvent(it)}
    }

    private fun handleEvent(event:ForgetIdViewModel.Event)
    {
        when(event)
        {
            is ForgetIdViewModel.Event.nextPage->{
                val intent= Intent(this,ForgetId2Activity::class.java)
                startActivity(intent)
            }
        }
    }
}