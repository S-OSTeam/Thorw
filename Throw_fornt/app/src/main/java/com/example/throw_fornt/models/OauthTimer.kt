package com.example.throw_fornt.models

import kotlin.concurrent.timer

class OauthTimer {

    var oauthMinute:Int=0
    var oauthSecond:Int=0

    fun OauthTimeWating():Boolean
    {
        timer(period=1000, initialDelay = 1000)
        {
            changeOauthSecond()
            if(oauthMinute==10)
            {
                cancel()
            }
        }
        return true
    }

    fun changeOauthMinute()
    {
        oauthMinute++
    }
    fun changeOauthSecond()
    {
        oauthSecond++
        if(oauthSecond==60)
        {
            oauthSecond=0
            changeOauthMinute()
        }
    }
}