package com.example.throw_fornt.feature.myPage.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.data.model.response.MypageResponse
import com.example.throw_fornt.databinding.ActivityProfileBinding
import com.example.throw_fornt.util.common.BindingActivity
import com.example.throw_fornt.util.common.Toaster
import com.example.throw_fornt.util.common.showDialog
import java.lang.Exception

class ProfileActivity : BindingActivity<ActivityProfileBinding>(R.layout.activity_profile) {
    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        try {
            val intent: Intent = getIntent()
            val receive = intent?.getParcelableExtra<MypageResponse>("data")
            if(receive!=null) viewModel.profile(receive)
        }
        catch (e:Exception){
            Log.d("PROFILE_TAG",e.toString())
        }

        viewModel.event.observe(this){handlerEvent(it)}
    }

    fun handlerEvent(event: ProfileViewModel.Event){
        when(event){
            is ProfileViewModel.Event.Modify -> success(event.msg)
            is ProfileViewModel.Event.Fail -> Toaster.showLong(this@ProfileActivity, event.msg)
            is ProfileViewModel.Event.Cancel -> cancel()
        }
    }

    fun success(msg: String){
        val intent: Intent = Intent()
        intent.putExtra("data", msg)
        setResult(RESULT_OK, intent)
        finish()
    }

    fun cancel(){
        showDialog(
            titleId = R.string.profile_tilte_text,
            messageId = R.string.profile_modify_text,
            negativeStringId = R.string.profile_no_text,
            positiveStringId = R.string.profile_yes_text,
            actionNegative = {no()},
            actionPositive = {yes()}
        )
    }

    fun no(){
        finish()
    }
    fun yes(){

    }
}