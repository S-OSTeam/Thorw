package com.example.throw_fornt.feature.mileage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.throw_fornt.R
import com.example.throw_fornt.data.model.response.MileageResponse
import com.example.throw_fornt.databinding.ActivityMileageBinding
import com.example.throw_fornt.feature.store.register.address.AddressViewModel
import com.example.throw_fornt.util.common.BindingActivity
import com.example.throw_fornt.util.common.Toaster

class MileageActivity : BindingActivity<ActivityMileageBinding>(R.layout.activity_mileage) {
    private val viewModel: MileageViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.mileage()
        viewModel.event.observe(this) { handleEvent(it) }
    }

    fun handleEvent(event: MileageViewModel.Event) {
        when (event) {
            is MileageViewModel.Event.Ranking -> listMileage(event.items)
            is MileageViewModel.Event.Mileage -> mileageData()
        }
    }

    fun mileageData() {
        viewModel.rank()
    }

    fun listMileage(items: List<MileageResponse>) {
        val list = binding.recyclerMileage
        val adapter = MileageAdapter(items, viewModel::selectMileage)
        adapter.notifyDataSetChanged()

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}