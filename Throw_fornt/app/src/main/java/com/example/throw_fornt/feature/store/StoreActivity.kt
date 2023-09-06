package com.example.throw_fornt.feature.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.throw_fornt.R
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.databinding.ActivityStoreBinding
import com.example.throw_fornt.feature.store.management.ManagementActivity
import com.example.throw_fornt.feature.store.management.ManagementViewModel
import com.example.throw_fornt.feature.store.register.RegisterActivity
import com.example.throw_fornt.feature.store.register.RegisterViewModel
import com.example.throw_fornt.feature.store.register.address.AddressResult
import com.example.throw_fornt.util.common.BindingActivity
import com.example.throw_fornt.util.common.Toaster
import java.lang.Exception

class StoreActivity : BindingActivity<ActivityStoreBinding>(R.layout.activity_store) {
    private val viewModel: StoreViewModel by viewModels()
    private val managerViewModel: ManagementViewModel by viewModels()
    var items = ArrayList<StoreModel>()
    val REQUEST_CODE: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        try {
            viewModel.storeList()
        }
        catch (e:Exception){

        }
        viewModel.event.observe(this) { changeActivity(it) }
    }

    //내 가게정보를 받는 데이터를 adapter를 통해 RecyclerView에 item을 넣어줌
    fun listData(storeList: ArrayList<StoreModel>?){
        var list = findViewById<RecyclerView>(R.id.store_list)
        items = storeList!!

        //TODO 가게 정보 조회 api 넣기
        val adapter = StoreAdapter(items, viewModel::service)
        adapter.notifyDataSetChanged()

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    fun blankData(){
        var list = findViewById<RecyclerView>(R.id.store_list)
        items = ArrayList<StoreModel>()
        val adapter = StoreAdapter(items, viewModel::service)
        adapter.notifyDataSetChanged()

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    //수정 및 삭제, 등록 화면으로 이동하기 위한 함수
    fun changeActivity(event: StoreViewModel.Event){
        when (event){
            is StoreViewModel.Event.Register -> register()
            is StoreViewModel.Event.Service -> storeService(event.item)
            is StoreViewModel.Event.StoreList -> listData(event.item)
            is StoreViewModel.Event.BlankList -> blankData()
        }
    }

    private fun register(){
        val intent: Intent = Intent(this, RegisterActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun storeService(storeItem: StoreModel) {
        val intent: Intent = Intent(this, ManagementActivity::class.java)
        //ManagementActivity에 선택한 내 가게 data 전달
        intent.putExtra("data", storeItem)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val receiveData = data!!.getStringExtra("data")
            if (receiveData != null) {
                if(receiveData.contains("삭제")){
                    if(receiveData.contains("실패")){
                        Toaster.showLong(this@StoreActivity, "삭제 하지 못했습니다.")
                    }
                    else Toaster.showLong(this@StoreActivity, "성공적으로 삭제 처리 되었습니다.")
                }
                else if(receiveData.contains("등록")){
                    Toaster.showLong(this@StoreActivity, "성공적으로 등록 처리 되었습니다.")
                }
                else{
                    Toaster.showLong(this@StoreActivity, "성공적으로 수정 되었습니다.")
                }
                viewModel.storeList()
            }
        }
    }
}