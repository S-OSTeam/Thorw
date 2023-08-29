package com.example.throw_fornt.models

class Global{
    companion object{
        lateinit var address: String
    }

    fun getAddress(select: String){
        address = select
    }
}
