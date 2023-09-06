package com.example.throw_fornt.feature.myPage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.FragmentMyPageBinding
import com.example.throw_fornt.feature.store.StoreActivity
import com.example.throw_fornt.util.common.BindingFragment

class MyPageFragment : BindingFragment<FragmentMyPageBinding>(R.layout.fragment_my_page) {

    private val viewModel: MyPageViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.inquiry()
        viewModel.event.observe(viewLifecycleOwner){handlerEvnet(it)}
    }

    fun handlerEvnet(event: MyPageViewModel.Event){
        when(event){
            is MyPageViewModel.Event.Store -> storeActivity()
            is MyPageViewModel.Event.Mileage -> mileageActivity()
        }
    }

    fun storeActivity(){
        val intent: Intent = Intent(activity, StoreActivity::class.java)
        startActivity(intent)
    }

    fun mileageActivity(){

    }
}
