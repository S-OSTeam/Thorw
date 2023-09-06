package com.example.throw_fornt.feature.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.data.model.request.ReissueObj
import com.example.throw_fornt.data.model.request.RetrofitService
import com.example.throw_fornt.data.model.response.Reissue
import com.example.throw_fornt.databinding.ActivityLoginBinding
import com.example.throw_fornt.feature.forget.ForgetActivity
import com.example.throw_fornt.feature.home.HomeActivity
import com.example.throw_fornt.feature.signup.Signup1Activity
import com.example.throw_fornt.models.Preference
import com.example.throw_fornt.models.RetrofitObj
import com.example.throw_fornt.util.common.BindingActivity
import com.example.throw_fornt.util.common.Toaster
import com.kakao.sdk.common.KakaoSdk
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {


    private val viewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //카카오 sdk 초기화 실행
        KakaoSdk.init(this, "4969e32d1feec34755a0720a391c6f12")
        val myApp: Preference = Preference(this)

        val refreshToken:String = myApp.getString("refreshToken","")

        val reissueObj:ReissueObj=ReissueObj("refresh_token",refreshToken)

        val retrofit: RetrofitObj =RetrofitObj

        val signupBtn: Button =findViewById(R.id.signup_btn)
        binding.viewModel = viewModel
        viewModel.event.observe(this) { handleEvent(it) }

        signupBtn.setOnClickListener {
            val intent= Intent(this, Signup1Activity::class.java)
            intent.putExtra("sns","NORMAL")
            startActivity(intent)
        }

        if(refreshToken!="")
        {
            retrofit.service.reissueLogin(reissueObj).enqueue(object:Callback<Reissue>
            {
                override fun onResponse(call: Call<Reissue>, response: Response<Reissue>) {
                    if(response.isSuccessful)
                    {
                        myApp.setString("accessToken",response.body()?.accessToken.toString())
                        myApp.setString("refreshToken",response.body()?.refreshToken.toString())
                        val intent=Intent(this@LoginActivity,HomeActivity::class.java)
                    }
                    else
                    {
                        Toaster.showShort(this@LoginActivity,response.body()?.message.toString())
                    }
                }

                override fun onFailure(call: Call<Reissue>, t: Throwable) {
                    Toaster.showShort(this@LoginActivity,"통신실패했습니다.")
                }

            })
        }

    }
    private fun handleEvent(event: LoginViewModel.Event) {
        when (event) {
            is LoginViewModel.Event.oauthorized -> {
                val intent=Intent(this,HomeActivity::class.java)
                startActivity(intent)
            }
            is LoginViewModel.Event.forgetId->{
                val intent=Intent(this, ForgetActivity::class.java)
                startActivity(intent)
            }
            is LoginViewModel.Event.signUp->{
                val intent=Intent(this,Signup1Activity::class.java)
                intent.putExtra("sns","KAKAO")
                intent.putExtra("kakaoId",viewModel.kakaoId.value.toString())
                startActivity(intent)
            }
        }
    }
}