package com.example.throw_fornt.feature.forget

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.throw_fornt.data.model.request.RetrofitService
import com.example.throw_fornt.util.common.Toaster
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ForgetIdViewModel(application: Application) : AndroidViewModel(application){

    private val context = getApplication<Application>().applicationContext

    val retrofit = Retrofit.Builder().baseUrl("http://{SERVER_ID}")
        .addConverterFactory(GsonConverterFactory.create()).build();
    val service = retrofit.create(RetrofitService::class.java);

    private val _event: MutableLiveData<ForgetIdViewModel.Event> = MutableLiveData()
    val event: LiveData<ForgetIdViewModel.Event>
        get() = _event

    val email:MutableLiveData<String> = MutableLiveData()
    var emailCheck:Boolean =true


    //이메일 인증하기 버튼 기능
    fun oauthEmail()
    {
        //도비형의 이메일 인증 사용해서 처리, 이메일 인증이 제대로 되면 emailCheck값을 true로 바꿔주면 된다. 아니면 토스트 띄워서 인증안된거 이야기해주기
    }

    //인증이 완료됐다면 아이디를 확인할 수 있는 창으로 넘어감
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