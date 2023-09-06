package com.example.throw_fornt.feature.mileage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.ActivityMileageBinding
import com.example.throw_fornt.util.common.BindingActivity

class MileageActivity : BindingActivity<ActivityMileageBinding>(R.layout.activity_mileage) {
    private val viewModel:MileageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mileage)
    }
}