package com.example.vankhanhpr.vidu2.change_password

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.encode.Encode
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.json.MessageEvent
import kotlinx.android.synthetic.main.activity_change_password.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by VANKHANHPR on 7/8/2017.
 */
class ChangePass:AppCompatActivity()
{
    var your_id:String?=null
    var your_pass:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        EventBus.getDefault().register(this)

        var pass1:String?=null
        var pass2:String?=null
        var pass3:String?=null

        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra("Document")
        your_id= bundle.getString("Resuilt")
        your_pass= bundle.getString("Resuilt2")

        var call=Call_Receive_Server.instance

        tv_continue_changepass.setOnClickListener()
        {
            pass1= edt_newpassword.text.toString()
            pass2=edt_newpassagain.text.toString()
            if(!pass1.equals(pass2))
            {
                Toast.makeText(applicationContext,"Mật khẩu không trùng nhau",Toast.LENGTH_SHORT)
                sendToActivityMain(your_id!!,AllValue.gotomain_changepass!!)
            }
            if(pass1.equals(pass2))
            {
                pass3= Encode().encryptString(pass1.toString())
                var inval: Array<String> = arrayOf(your_id!!.toString(),your_pass!!.toString(),pass3.toString()!!)
                call.CallEmit(AllValue.workername_change_pass,AllValue.servicename_change_pass,inval,AllValue.change_password!!.toString())
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getTemp()==AllValue.change_password)
        {
            if(event.getService()!!.getResult()=="1")
            {
                Toast.makeText(applicationContext,"Thay đổi mật khẩu thành công !!!",Toast.LENGTH_SHORT).show()

            }
            else
            {
                Toast.makeText(applicationContext,"Thay đổi mật khẩu không thành công,vui lòng kiểm tra lại mật khẩu !!!",Toast.LENGTH_SHORT).show()

            }
        }
    }
    fun sendToActivityMain(value: String,resultcode:Int) {

        var intent3 = Intent(applicationContext, MainActivity::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
}