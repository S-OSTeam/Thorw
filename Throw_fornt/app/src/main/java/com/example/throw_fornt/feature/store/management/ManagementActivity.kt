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

class ManagementActivity : BindingActivity<ActivityManagementBinding>(R.layout.activity_management) {
    private val viewModel: ManagementViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //ManagementActivity를 실행되면 AlertDialog창을 띄워서 수정할지 삭제할지 물어본다.
        val builder = AlertDialog.Builder(this)
        builder.setTitle("수정 및 삭제")
            .setMessage("수정할지 삭제할지 선택해주세요.")
            .setNegativeButton("수정",
                DialogInterface.OnClickListener{
                    dialog, id -> modify()
            })
            .setPositiveButton("삭제",
                DialogInterface.OnClickListener {
                    dialog, id -> remove()
            })
        builder.show()
    }

    //수정하기 위한 프로그램
    fun modify(){
        //StoreActivity에서 내가 선택한 가게 값을 전달받아 ManagementViewModel.selectItem함수를 호출하여 edit에 값을 입력해준다.
        val intent: Intent = getIntent()
        val receive = intent?.getParcelableExtra<StoreModel>("data")
        if(receive!=null) viewModel.selectItem(receive)
        viewModel.event.observe(this) { handleEvent(it) }
    }

    fun remove(){
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