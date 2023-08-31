package com.example.throw_fornt.feature.store.register.address

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.throw_fornt.R
import com.example.throw_fornt.data.model.response.DocumentResponse
import com.example.throw_fornt.databinding.ActivityAddressBinding
import com.example.throw_fornt.util.common.BindingActivity
import com.example.throw_fornt.util.common.Toaster

class AddressActivity : BindingActivity<ActivityAddressBinding>(R.layout.activity_address) {
    private val viewModel: AddressViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.event.observe(this){handleEvent(it)}
    }

    fun handleEvent(event: AddressViewModel.Event){
        when(event){
            is AddressViewModel.Event.Search -> search(event.items)
            is AddressViewModel.Event.Select -> select(event.item)
            is AddressViewModel.Event.Fail -> Toaster.showLong(this@AddressActivity, event.msg)
        }
    }

    //검색창에 자신의 주소 키워드를 입력 시 전체를 받아서 RecyclerView에 보여주도록 한다.
    fun search(items: List<DocumentResponse>){
        val list = binding.addressList
        val adapter = AddressAdapter(items, viewModel::selectAddress)
        adapter.notifyDataSetChanged()

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    //원하는 주소를 입력받았을때 data에 값을 담고서 RegisterActivit에 있는 onActivityResult()함수에 값을 전달시켜준다.
    fun select(item: DocumentResponse){
        val intent = Intent()
        val data:AddressResult = AddressResult(
            item.address?.address.toString(), item.roadAddress?.zoneNo.toString(),
            item.latitude.toString(), item.longitude.toString(),
        )

        intent.putExtra("data", data)
        setResult(RESULT_OK, intent)
        finish()
    }
}
