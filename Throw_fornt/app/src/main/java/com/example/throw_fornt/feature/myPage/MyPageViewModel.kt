package com.example.throw_fornt.feature.myPage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyPageViewModel : ViewModel() {
    val name: MutableLiveData<String> =MutableLiveData("홍길동")


}