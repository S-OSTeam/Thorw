package com.example.throw_fornt.models

import kotlin.concurrent.timer

class EmailTimer {
    var emailMinute:Int=0
    var emailSecond:Int=0

    fun EmailTimeWating():Boolean
    {
        timer(period=1000, initialDelay = 1000)
        {
            changeEmailSecond()
            if(emailMinute==10)
            {
                cancel()
            }
        }
        return true
    }
    fun changeEmailMinute()
    {
        emailMinute++
    }
    fun changeEmailSecond()
    {
        emailSecond++
        if(emailSecond==60)
        {
            emailSecond=0
            changeEmailMinute()
        }
    }
}