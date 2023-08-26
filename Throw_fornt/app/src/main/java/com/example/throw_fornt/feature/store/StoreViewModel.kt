package com.example.throw_fornt.feature.store

import androidx.lifecycle.ViewModel
import com.example.throw_fornt.data.model.response.StoreResponse

class StoreViewModel : ViewModel() {
    val storeList: MutableList<StoreResponse> = ArrayList<StoreResponse>()
    val size get() = storeList.size

    public fun addStore(){

    }

    public fun getStore(): List<StoreResponse>{
        return storeList.toList()
    }
}