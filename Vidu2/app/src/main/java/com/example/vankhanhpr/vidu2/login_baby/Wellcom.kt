package com.example.vankhanhpr.vidu2.login_baby

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.json.Service_Response
import com.example.vankhanhpr.vidu2.json.singerton.Singleton
import com.example.vankhanhpr.vidu2.login_doctor.Login_Doctor
import com.example.vankhanhpr.vidu2.login_doctor.main_doctor
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * Created by VANKHANHPR on 7/5/2017.
 */

class Wellcom : AppCompatActivity()
{
    var id1:String? = ""
    var pass1:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        EventBus.getDefault().register(this)

        var call = Call_Receive_Server.getIns()
        call!!.Sevecie()
        call!!.ListenEvent()
        //................. mom or doctor
        var mom_doctor : String = getResources().getString(R.string.mom_or_doctor)
        //............... lan dau
        var Shared_Preferences : String = "landau"//..............ten thu muc chua
        var sharedpreferences : SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)

        var thefirst:String?=""

        var thefirst1:String?=null
        var id:String?=null
        var password:String?=null

         id1 = sharedpreferences.getString("id","")
         pass1 =sharedpreferences.getString("password","")
         thefirst1= sharedpreferences.getString("thefirst","")

        val secondDelay = 1

        Handler().postDelayed(Runnable()
        {
            if(thefirst1=="")
            {
                startActivity(Intent(this@Wellcom, Login_Commercial::class.java))
                finish()
            }
            else
            {
                if(id1=="")
                {
                    if(resources.getString(R.string.mom_or_doctor)=="doctor") {
                        startActivity(Intent(this@Wellcom, Login_Doctor::class.java))
                        finish()
                    }
                    else
                        if(resources.getString(R.string.mom_or_doctor)=="mom") {
                            startActivity(Intent(this@Wellcom, Login::class.java))
                            finish()
                        }
                }
                if(id1!=null)
                {
                    Log.d("iddd",id1+ pass1!!)


                    if(resources.getString(R.string.mom_or_doctor)=="mom") {
                        sendToActivityMain(id1!!, pass1!!, 453)
                    }
                    else
                    {
                        sendToActivityMain_Doctor(id1!!,pass1!!,454)
                    }
                    var inval: Array<String> = arrayOf(id1!!,pass1!!)
                    call!!.CallEmit(AllValue.workername_checkpass!!.toString(), AllValue.servicename_checkpass!!.toString(), inval, AllValue.checkpass!!.toString())
                }
            }
        }, (secondDelay * 1000).toLong())
    }
    //Chờ đợi kết quả khi gọi emit
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        var serve: Service_Response = event.getService()!!
        if (event.getTemp() == AllValue.checkpass.toString())//Kiểm tra kết quả trả về có phải của mình đã gửi đi hay không
        {
            if(serve.getResult()=="1")
            {
                //Tính sau
            }
            else
            {
                //từ từ tính 2
                /*startActivity(Intent(this@Wellcom, Login::class.java))
                finish()*/
            }
        }
    }
    fun sendToActivityMain(value: String,value2: String,resultcode:Int) {

        var intent3 = Intent(applicationContext,MainActivity::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        bundle.putString(AllValue.value2, value2)

        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }

    //goto main doctor
    fun sendToActivityMain_Doctor(value: String,value2:String,resultcode:Int) {

        var intent3 = Intent(applicationContext, main_doctor::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        bundle.putString(AllValue.value2, value2)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
}
