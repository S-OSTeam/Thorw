package com.example.throw_fornt.feature.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.throw_fornt.R
import com.example.throw_fornt.feature.login.LoginActivity

class Signup2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup2)
        val goLoginBtn: Button =findViewById(R.id.go_login_page)

        goLoginBtn.setOnClickListener {
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}