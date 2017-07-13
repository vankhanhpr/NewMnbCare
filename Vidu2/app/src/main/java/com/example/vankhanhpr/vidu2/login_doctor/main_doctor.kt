package com.example.vankhanhpr.vidu2.login_doctor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.getter_setter.AllValue

/**
 * Created by User on 7/10/2017.
 */
class main_doctor : AppCompatActivity() {

    var user_id:String?=null
    var pass:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_doctor)

        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)
        user_id= bundle.getString(AllValue.value)
        pass=bundle.getString(AllValue.value2)
        Log.d("matkhau2",user_id + pass)


        //......lần đầu đăng nhập
        var Shared_Preferences : String = "landau"//........ ten thu muc chua
        var sharedpreferences : SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)

        //Đăng nhập một lần
        /*var editor : SharedPreferences.Editor? = sharedpreferences.edit()
        editor!!.putString("thefirst","1")//........... luu du lieu
        editor!!.putString("id",user_id!!)
        editor!!.putString("password",pass!!)
        editor!!.commit()*/


}
}