package com.example.throw_fornt.feature.signup

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.throw_fornt.data.model.request.IdDupObj
import com.example.throw_fornt.data.model.request.RetrofitService
import com.example.throw_fornt.data.model.request.UserSignUpObj
import com.example.throw_fornt.data.model.response.IdDup
import com.example.throw_fornt.data.model.response.UserSignUp
import com.example.throw_fornt.models.EmailTimer
import com.example.throw_fornt.models.OauthTimer
import com.example.throw_fornt.models.Preference
import com.example.throw_fornt.models.RetrofitObj
import com.example.throw_fornt.models.RetrofitObj.service
import com.example.throw_fornt.util.common.Toaster
import com.kakao.sdk.user.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.timer

class SignupViewModel (application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext


    val retrofit: RetrofitObj = RetrofitObj



    //정규표현식들
    private val idPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])[0-9a-zA-Z]{5,19}\$"//숫자,문자 포함한 5-19자리 아이디
    private val pwPattern =
        "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\\\$`~!@\$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}\$"
    private val emailPattern =
        "\"[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}\""
    private val phonePattern = "^01[01][0-9]{8}\$"
    private val namePattern ="^[가-힣a-zA-Z]+$"


    //각각 정규표현식에 일치하는지의 여부를 보여주는 변수
    var nameCheck: Boolean = false
    var emailCheck: Boolean = false
    var phoneCheck: Boolean = false
    var idCheck: Boolean = false
    var passwordCheck: Boolean = false

    private val _event: MutableLiveData<SignupViewModel.Event> = MutableLiveData()
    val event: LiveData<SignupViewModel.Event>
        get() = _event


    //입력받은 이메일,이메일로 전송한 인증코드,아이디,이름,전화번호,패스워드 들의 값을 갖는 변수
    val email: MutableLiveData<String> = MutableLiveData()
    val emailCode: MutableLiveData<String> = MutableLiveData()
    val id: MutableLiveData<String> = MutableLiveData("")
    val name: MutableLiveData<String> = MutableLiveData("")
    val phone: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val password2:MutableLiveData<String> =MutableLiveData()
    val sendEmail: MutableLiveData<Boolean> =MutableLiveData(true)
    val checkEmailCode:MutableLiveData<Boolean> = MutableLiveData(true)
    val emailTimeTxt:MutableLiveData<String> = MutableLiveData("0분:0초")
    val oauthTimeTxt:MutableLiveData<String> = MutableLiveData("0분:0초")

    //이메일인증,아이디 중복 여부를 확인해주는 변수
    val emailOauth: MutableLiveData<Boolean> = MutableLiveData(false)
    val idDupCheck: MutableLiveData<Boolean> = MutableLiveData()





    //아이디 중복확인 버튼 기능. 먼저 아이디 유효성을 체크하고 그 후에 중복 여부에 따라 idDupCheck를 변경해준다
    fun idClick()
    {
        val idDupObj: IdDupObj =IdDupObj(id.value.toString())
        idCheck=Regex(idPattern).find(id.value.toString())!=null
        if(!idCheck)
        {
            Toaster.showShort(context,"아이디를 제대로 입력해주세요")
        }else
        {
            retrofit.service.getIdDup(idDupObj)?.enqueue(object:Callback<IdDup>
            {
                override fun onResponse(call: Call<IdDup>, response: Response<IdDup>) {
                    if(response.isSuccessful)
                    {
                        var result: Boolean = response.body()?.idDup!!
                        if(result)
                        {
                            Toaster.showLong(context,"중복되는 아이디가 있습니다.")
                        }else {
                            Toaster.showLong(context,"사용가능한 아이디 입니다.")
                        }
                    }
                    else {
                        Toaster.showLong(context,"유효하지 않은 request입니다.")
                    }
                }

                override fun onFailure(call: Call<IdDup>, t: Throwable) {
                    Toaster.showLong(context,"중복확인을 수행할 수 없습니다.")
                }

            })

        }
    }

    //다음페이지로 넘어가게 해주는 기능. 모든 입력된 것들의 유효성을 체크하고, 아이디 중복과 이메일 인증 여부를 체크 한후에 다음페이지로 넘어가게 해준다
    fun nextPage()
    {
        Log.d("이름",name.value.toString())
        nameCheck=Regex(namePattern).find(name.value.toString())!=null
        phoneCheck=Regex(phonePattern).find(phone.value.toString())!=null
        passwordCheck=Regex(pwPattern).find(password.value.toString())!=null
        idCheck=Regex(idPattern).find(id.value.toString())!=null

        if(name.value.toString()=="")
        {
            Toaster.showLong(context,"이름을 제대로 입력해주세요.")
            return
        }
        if(nameCheck==false) {
            Toaster.showLong(context,"이름을 제대로 입력해주세요.")
            return
        }
        if(emailOauth.value==false) {
            Toaster.showLong(context,"이메일 인증을 해주세요.")
            return
        }
        if(phoneCheck==false) {
            Toaster.showLong(context,"전화번호를 제대로 입력해주세요.")
            return
        }
        if(id.value=="")
        {
            Toaster.showLong(context,"아이디를 제대로 입력해주세요.")
            return
        }
        if(idCheck==false)
        {
            Toaster.showLong(context,"아이디를 제대로 입력해주세요.")
            return
        }

        if(idDupCheck.value==true) {
            Toaster.showLong(context,"아이디 중복확인을 해주세요.")
            return
        }
        if(passwordCheck==false) {
            Toaster.showLong(context,"비밀번호를 제대로 입력해주세요.")
            return
        }
        val userSignUpObj: UserSignUpObj =UserSignUpObj(id.value.toString(),password.value.toString(),password2.value.toString(),null,"NORMAL","NORMAL",
            "ROLE_USER",name.value.toString(),phone.value.toString(),email.value.toString())


       retrofit.service.getUser(userSignUpObj)?.enqueue(object:Callback<String?>
       {
           override fun onResponse(call: Call<String?>, response: Response<String?>) {
               if(response.isSuccessful)
               {
                   Toaster.showShort(context,"정상 회원가입 완료")
                   _event.value=Event.nextPage
               }
               else
               {
                   Toaster.showShort(context,"리스폰스가 이상합니다.")
                   Log.d("회원가입 리스폰스",response.body().toString())
               }
           }

           override fun onFailure(call: Call<String?>, t: Throwable) {
                Toaster.showShort(context,"서버와 통신이 되지않았습니다.")
           }
       })

    }


    sealed class Event {
        object nextPage : Event()
    }
}