package com.example.throw_fornt.feature.forget

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.throw_fornt.data.model.request.RetrofitService
import com.example.throw_fornt.util.common.Toaster
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ForgetPwViewModel(application: Application) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    //레트로핏 관련 선언
    val retrofit = Retrofit.Builder().baseUrl("http://{SERVER_ID}")
        .addConverterFactory(GsonConverterFactory.create()).build();
    val service = retrofit.create(RetrofitService::class.java);

    //이벤트 처리 위한 데이터 선언
    private val _event: MutableLiveData<Event> = MutableLiveData()
    val event: LiveData<Event>
        get() = _event

    //입력받은 이메일을 다루는 뮤터블 데이터
    val email: MutableLiveData<String> = MutableLiveData()
    //이메일 인증이 됐는지 확인해주는 변수
    var emailCheck:Boolean =true


    //이메일 인증버튼 기능 인증이 되면 emailCheck변수를 true로 바꾼다. 안되면 토스트 띄우고 다시 하게 만듬
    fun oauthEmail()
    {
        //도비형의 이메일 인증 사용해서 처리, 이메일 인증이 제대로 되면 emailCheck값을 true로 바꿔주면 된다. 아니면 토스트 띄워서 인증안된거 이야기해주기
    }

    //다음 페이지로 넘어가게 해주는 버튼 기능 이메일 인증이 완료 되지 않았을 시에는 토스트만 띄운다
    fun nextBtn()
    {
        if(emailCheck)
            _event.value=Event.nextPage
        else
            Toaster.showLong(context,"이메일 인증이 완료 되지 않았습니다.")
    }

    sealed class Event
    {
        object nextPage:Event()
    }
}