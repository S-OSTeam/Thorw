package com.example.throw_fornt.feature.login

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.throw_fornt.data.model.request.KakaoObj
import com.example.throw_fornt.data.model.request.LoginObj
import com.example.throw_fornt.data.model.request.RetrofitService
import com.example.throw_fornt.data.model.response.IdDup
import com.example.throw_fornt.data.model.response.Kakao
import com.example.throw_fornt.data.model.response.Login
import com.example.throw_fornt.models.Preference
import com.example.throw_fornt.models.RetrofitObj
import com.example.throw_fornt.models.RetrofitObj.service
import com.example.throw_fornt.util.common.Toaster
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val myApp: Preference = Preference(context)

    private val _event: MutableLiveData<Event> = MutableLiveData()
    val event: LiveData<Event>
        get() = _event

     val id: MutableLiveData<String> = MutableLiveData()
     val password: MutableLiveData<String> = MutableLiveData()

   val kakaoId:MutableLiveData<String> =MutableLiveData()


    val retrofit: RetrofitObj = RetrofitObj

    //카카오톡으로 로그인시에 백과 연동해서 로그인 시켜주는 기능 매개변수로는 카카오톡 로그인 성공시에 토큰을 보낸다
    fun retroKakao(kakaoToken:OAuthToken):Int //성공시 1,실패시 0 , 회원가입해야 할시 2
    {

        var result:Int=0
        val kakaoObj: KakaoObj = KakaoObj("KAKAO", kakaoToken.toString())
        retrofit.service.getKakao(kakaoObj)?.enqueue(object:Callback<Kakao>
        {

            override fun onResponse(call: Call<Kakao>, response: Response<Kakao>) {
                if(response.isSuccessful)
                {
                    //카카오톡으로 바로 로그인이 가능한 상태(이미 서버에 정보가있음)
                    Toaster.showLong(context,"로그인 성공.")
                    result=1
                    _event.value=Event.oauthorized
                }else
                {
                    //4xx 에러 응답왔을 때(db에 회원정보가 없을때)
                    result =2
                }
            }

            override fun onFailure(call: Call<Kakao>, t: Throwable) {
                Toaster.showLong(context,"연결이 안됩니다.")
                result=0
            }
        })
        return result
    }


    //로그인 버튼 기능(백으로 입력된 아이디 및 비밀번호 보내주기
    fun login()
    {
        val loginObj: LoginObj =LoginObj(id.value.toString(),password.value.toString())
        retrofit.service.requestLogin(loginObj)?.enqueue(object:Callback<Login>
        {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                if(response.isSuccessful)
                {
                    //쉐어드 프리퍼런스에 엑세스 토큰이랑 리프레쉬 토큰 저장
                    myApp.setString("acceessToken",response.body()?.accessToken.toString())
                    myApp.setString("refreshToken",response.body()?.refreshToken.toString())
                    _event.value=Event.oauthorized
                }
                else
                {
                    Toaster.showLong(context,response.body()?.message.toString())
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                Toaster.showLong(context,"서버통신 실패.")
            }
        })
        //수형이한테 아디비번 보내서 승인 나오면
        _event.value=Event.oauthorized
    }

    //아이디 비번찾기 액티비티로 넘어가는 기능
    fun forgetIdPw()
    {
        _event.value=Event.forgetId
    }


    //카카오 로그인 버튼 기능
    fun kakaoLogin()
    {
        var result:Int=0
        //카카오톡 설치 확인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context))
        {
            //카카오톡으로 로그인
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    //카카오 로그인 실패
                    Toaster.showShort(context,"카카오 로그인 실패 다시 시도해주세요.")
                }
                else if (token != null) {
                    //카카오 로그인 성공
                    result=retroKakao(token)

                    if(result==2)
                    {
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Toaster.showShort(context,"사용자 정보 요청 실패")
                            } else if (user != null) {
                                Toaster.showShort(context,"사용자 정보 요청 성공")
                                kakaoId.value=user.kakaoAccount?.email.toString()
                                _event.value=Event.signUp
                            }
                        }
                    }
                }
            }
        }
        else
        {
            // 카카오계정으로 로그인
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                if (error != null) {
                    //카카오 로그인 실패
                    Toaster.showShort(context,"카카오 로그인 실패 다시 시도해주세요.")
                }
                else if (token != null) {
                    //카카오 로그인 성공
                    retroKakao(token)
                }
            }
        }
    }



    sealed class Event {
        object oauthorized : Event()
        object forgetId:Event()
        object signUp:Event()
    }


}