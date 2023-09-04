package com.example.throw_fornt.feature.store.management

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.throw_fornt.R
import com.example.throw_fornt.data.model.response.StoreModel
import com.example.throw_fornt.databinding.ActivityManagementBinding
import com.example.throw_fornt.util.common.BindingActivity
import com.example.throw_fornt.util.common.Toaster
import com.example.throw_fornt.util.common.showDialog

class ManagementActivity : BindingActivity<ActivityManagementBinding>(R.layout.activity_management) {
    private val viewModel: ManagementViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //ManagementActivity를 실행되면 AlertDialog창을 띄워서 수정할지 삭제할지 물어본다.
        showDialog(
            titleId = R.string.store_management_title_text,
            messageId = R.string.store_management_message_text,
            negativeStringId = R.string.store_management_delete_text,
            positiveStringId = R.string.store_management_modify_text,
            actionNegative = {delete()},
            actionPositive = {modify()}
        )
    }

    //수정하기 위한 프로그램
    fun modify(){
        //StoreActivity에서 내가 선택한 가게 값을 전달받아 ManagementViewModel.selectItem함수를 호출하여 edit에 값을 입력해준다.
        val intent: Intent = getIntent()
        val receive = intent?.getParcelableExtra<StoreModel>("data")
        if(receive!=null) viewModel.selectItem(receive)
        viewModel.event.observe(this) { handleEvent(it) }
    }

    fun delete(){
        finish()
    }

    fun handleEvent(event: ManagementViewModel.Event){
        when(event){
            is ManagementViewModel.Event.Modify -> successModify(event.modify)
            is ManagementViewModel.Event.Fail -> failMsg(event.msg)
        }
    }

    fun successModify(modify: StoreModel){

    }
    fun failMsg(msg: String){
        Toaster.showLong(this@ManagementActivity, msg)
    }
}