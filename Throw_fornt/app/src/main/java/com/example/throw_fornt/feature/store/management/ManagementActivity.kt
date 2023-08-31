package com.example.throw_fornt.feature.store.management

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.ActivityManagementBinding
import com.example.throw_fornt.util.common.BindingActivity

class ManagementActivity : BindingActivity<ActivityManagementBinding>(R.layout.activity_management) {
    private val viewModel: ManagementViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_management)
    }
}