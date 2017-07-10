package com.example.vankhanhpr.vidu2.login_baby

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.login_doctor.Login_Doctor
import com.example.vankhanhpr.vidu2.login_doctor.main_doctor


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
        //................. mom or doctor
        var mom_doctor : String = getResources().getString(R.string.mom_or_doctor)
        //............... lan dau
        var Shared_Preferences : String = "landau"//..............ten thu muc chua
        var sharedpreferences : SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
        var thefirst = ""

        val secondDelay = 1
        Handler().postDelayed(Runnable() {
            if(sharedpreferences.getString(thefirst,"") == "") {//.... the first
                startActivity(Intent(this@Wellcom, Login_Commercial::class.java))
                finish()
            }
            else if( sharedpreferences.getString(thefirst,"") == "1" && mom_doctor == "doctor"){//..main_doctor
                startActivity(Intent(this@Wellcom, main_doctor::class.java))
                finish()
            }
            else if( sharedpreferences.getString(thefirst,"") == "1" && mom_doctor == "mom"){//... main_mom
                startActivity(Intent(this@Wellcom, MainActivity::class.java))
                finish()
            }
        }, (secondDelay * 1000).toLong())
    }
}