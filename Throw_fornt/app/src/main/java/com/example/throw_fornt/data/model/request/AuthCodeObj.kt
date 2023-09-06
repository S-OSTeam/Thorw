package com.example.throw_fornt.data.model.request

//이메일 인증코드 확인할때 요청할때의 정보가 담긴 데이터 클래스
data class AuthCodeObj(
    val email:String,
    val checkCode:String,
)
