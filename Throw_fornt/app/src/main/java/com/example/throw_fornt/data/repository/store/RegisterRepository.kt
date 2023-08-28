package com.example.throw_fornt.data.repository.store

class RegisterRepository {
    var btn: Boolean = true
        private set

    var text: String = "인증"
        private set

    fun success(): Boolean{
        btn = false
        text = "인증 완료"
        return true
    }
}