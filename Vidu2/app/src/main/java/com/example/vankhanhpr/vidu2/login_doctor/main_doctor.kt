package com.example.vankhanhpr.vidu2.login_doctor

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.vankhanhpr.vidu2.R

/**
 * Created by User on 7/10/2017.
 */
class main_doctor : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_doctor)
        //......lần đầu đăng nhập
        var Shared_Preferences : String = "landau"//........ ten thu muc chua
        var sharedpreferences : SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
        var thefirst : String = ""
        var editor : SharedPreferences.Editor? = sharedpreferences.edit()
        editor!!.putString(thefirst,"1")//........... luu du lieu
        editor!!.commit()


    }
}