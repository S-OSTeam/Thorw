package com.example.throw_fornt.data.model.request

//데이터를 보낼때 데이터 클래스
data class UserSignUpObj(
    val inputId:String,
    val inputPassword:String,
    val inputPasswordCheck:String,
    val snsId:String?,
    val sns:String?,
    val userStatus:String,
    val role:String,
    val userName:String,
    val userPhoneNumber:String,
    val email:String
)
