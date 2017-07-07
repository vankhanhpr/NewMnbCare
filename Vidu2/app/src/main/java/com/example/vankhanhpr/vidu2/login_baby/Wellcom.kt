package com.example.vankhanhpr.vidu2.login_baby

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server


/**
 * Created by VANKHANHPR on 7/5/2017.
 */

class Wellcom : AppCompatActivity()
{
    override fun onCreate(@Nullable savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        var call=Call_Receive_Server.instance
        call.Sevecie()
        val secondDelay = 5
        Handler().postDelayed(Runnable() {
            startActivity(Intent(this@Wellcom, Login_Commercial::class.java))
            finish()
        }, (secondDelay * 1000).toLong())
    }
}