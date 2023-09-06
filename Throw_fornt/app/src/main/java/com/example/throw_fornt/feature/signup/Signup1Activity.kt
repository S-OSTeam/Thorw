package com.example.throw_fornt.feature.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.startActivity
import com.example.throw_fornt.R
import com.example.throw_fornt.data.model.request.AuthCodeObj
import com.example.throw_fornt.data.model.request.SendMailObj
import com.example.throw_fornt.data.model.response.AuthCode
import com.example.throw_fornt.data.model.response.SendMail
import com.example.throw_fornt.databinding.ActivitySignup1Binding
import com.example.throw_fornt.models.RetrofitObj
import com.example.throw_fornt.util.common.BindingActivity
import com.example.throw_fornt.util.common.Toaster
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStream
import java.net.URL
import java.security.KeyStore
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import kotlin.concurrent.timer

class Signup1Activity : BindingActivity<ActivitySignup1Binding>(R.layout.activity_signup1) {
    private val viewModel: SignupViewModel by viewModels()

    val sns:String=intent.getStringExtra("sns").toString()
    val snsId:String=intent.getStringExtra("snsId").toString()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        viewModel.event.observe(this) { handleEvent(it) }
        val retrofit: RetrofitObj = RetrofitObj
        val checkEmailCode: Button =findViewById(R.id.check_email_code)
        val oauthEmailCode: TextView = findViewById(R.id.oauth_email_code)
        val checkEmail:Button =findViewById(R.id.check_email)
        val checkEmailTime:TextView=findViewById(R.id.email_time)

        val emailPattern ="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$"




        //이메일로 발송한 인증코드확인 기능
        checkEmailCode.setOnClickListener {

            val authCodeObj: AuthCodeObj =
                AuthCodeObj(viewModel.email.value.toString(), viewModel.emailCode.value.toString())

            retrofit.service.checkCode(authCodeObj)?.enqueue(object : Callback<String?> {
                override fun onResponse(call: Call<String?>, response: Response<String?>) {
                    if (response.isSuccessful) {
                        checkEmailCode.setEnabled(false)
                        viewModel.emailOauth.value = true
                        checkEmailCode.text="인증완료"
                        Toaster.showShort(this@Signup1Activity, "인증 확인돼었습니다.")
                    } else {
                        Toaster.showShort(this@Signup1Activity, "인증 번호가 일치하지 않습니다.")
                    }
                }

                override fun onFailure(call: Call<String?>, t: Throwable) {
                    Toaster.showShort(this@Signup1Activity, "서버 연결이 원활하지 않습니다.")
                }

            })


        }

        //이메일 인증버튼 눌렀을때의 기능
        checkEmail.setOnClickListener {

            val emailTrue:Boolean=Regex(emailPattern).find(viewModel.email.value.toString()) != null
            if (emailTrue==false) {
                Toaster.showShort(this@Signup1Activity, "이메일을 양식에 맞게 입력해주세요.")
            } else {


                val sendMailObj: SendMailObj = SendMailObj(viewModel.email.value.toString())

                retrofit.service.sendMail(sendMailObj)?.enqueue(object : Callback<String?> {
                    override fun onResponse(call: Call<String?>, response: Response<String?>) {
                        if (response.isSuccessful) {
                            checkEmail.setEnabled(false)
                            var minute: Int = 0
                            var second: Int = 0

                            timer(period = 1000, initialDelay = 1000)
                            {
                                second++
                                if (second == 60) {
                                    second = 0
                                    minute++
                                }
                                if (minute == 10) {
                                    cancel()
                                    runOnUiThread {
                                        checkEmail.setEnabled(true)
                                        checkEmailTime.text = "0분:0초"
                                    }
                                }
                                runOnUiThread {
                                    checkEmailTime.text = "${minute}분:${second}초"
                                    oauthEmailCode.text = "${minute}분:${second}초"
                                }
                            }
                        } else {
                            Toaster.showShort(this@Signup1Activity, "이메일 전송 실패")
                        }
                    }

                    override fun onFailure(call: Call<String?>, t: Throwable) {
                        Toaster.showShort(this@Signup1Activity, "서버와 연결이 원활하지가 않습니다.")
                    }

                })


            }
        }
    }
    private fun handleEvent(event: SignupViewModel.Event) {
        when (event) {
            is SignupViewModel.Event.nextPage -> {
                val intent=Intent(this, Signup2Activity::class.java)
                startActivity(intent)
            }

        }
    }
}

