package com.example.throw_fornt.feature.forget

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.throw_fornt.util.common.Toaster

class ForgetPw2ViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext


    //각각 비밀번호 입력칸에 들어가는 비밀번호와 비밀번호 확인칸에 들어가는 비밀번호들
    val newPw: MutableLiveData<String> = MutableLiveData()
    val checkPw:MutableLiveData<String> = MutableLiveData()

    //비밀번호 정규 표현식
    private val pwPattern = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\\\$`~!@\$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}\$"

    private val _event: MutableLiveData<ForgetPw2ViewModel.Event> = MutableLiveData()
    val event: LiveData<ForgetPw2ViewModel.Event>
        get() = _event

    var goodPw:Boolean=false //비밀번호를 양식에 맞게 입력했는지 확인해주는 변수
    var samePw:Boolean=false //비밀번호 두개를 일치하게 적었는지 확인해주는 변수

    //다 입력후 확인버튼 눌렀을 때의 기능
    fun checkBtn()
    {
        goodPw=Regex(pwPattern).find(newPw.value.toString())!=null //비밀번호 양식이 맞는지 확인
        //양식이 맞는지 여부부터 조건문 실행 후에 비밀번호와 비밀번호 확인이 일치하는지 확인 후에 변경완료 되는지 확인 후 로그인 페이지로 이동
        if(!goodPw)
        {
            Toaster.showShort(context,"비밀번호 양식에 맞게 입력해주세요")
        }
        else if(!(newPw.value==checkPw.value)){
            Toaster.showShort(context, "비밀번호가 일치하지 않습니다.")
        }
        else
        {
            //도비형한테 보내서 변경완료 후에 실행
            Toaster.showShort(context,"비밀번호가 변경완료 되었습니다. 로그인 페이지로 이동합니다")
            _event.value=Event.nextPage
        }
    }

    sealed class Event
    {
        object nextPage:Event()
    }


}