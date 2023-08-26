package com.example.throw_fornt.feature.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.ActivityHomeBinding
import com.example.throw_fornt.databinding.ActivityStoreBinding
import com.example.throw_fornt.util.common.BindingActivity

class StoreActivity : BindingActivity<ActivityStoreBinding>(R.layout.activity_store) {
    private val viewModel: StoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}