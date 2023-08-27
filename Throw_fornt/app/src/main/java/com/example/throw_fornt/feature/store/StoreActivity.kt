package com.example.throw_fornt.feature.store

import android.os.Bundle
import android.view.View
import android.widget.ListView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.throw_fornt.R
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.databinding.ActivityStoreBinding
import com.example.throw_fornt.util.common.BindingActivity
import com.example.throw_fornt.util.common.Toaster

class StoreActivity : BindingActivity<ActivityStoreBinding>(R.layout.activity_store) {
    private val viewModel: StoreViewModel by viewModels()
    var items = ArrayList<StoreModel>()
    val adapter = StoreAdapter(items)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        listData()
    }

    /*
    미리 내 가게정보를 받는 데이터
     */
    fun listData(){
        var list = findViewById<RecyclerView>(R.id.store_list)

        adapter.setItemClickListener(object: StoreAdapter.OnItemClickListener{
            override fun onClick(v: View, position: Int){
                Toaster.showLong(this@StoreActivity, "${items[position].name}")
            }
        })

        items.add(StoreModel("","","test1","","플라스틱",""))
        items.add(StoreModel("","","test2","","병",""))
        items.add(StoreModel("","","test3","","일반쓰레기",""))
        adapter.notifyDataSetChanged()

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }
}