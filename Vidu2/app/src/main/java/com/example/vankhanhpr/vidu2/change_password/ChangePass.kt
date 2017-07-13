package com.example.vankhanhpr.vidu2.change_password

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.encode.Encode
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.login_doctor.main_doctor
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
    var dialog:Dialog?=null
    var dialog_success:Dialog?=null
    var tab_loading:ProgressBar?=null
    var phone:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        EventBus.getDefault().register(this)
        tab_loading= findViewById(R.id.tab_loading) as ProgressBar
        var mCountDownTimer: CountDownTimer
        var pass1:String?=null
        var pass2:String?=null
        var pass3:String?=null

        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)

        phone=bundle.getString(AllValue.value)//lấy phone
        your_id= bundle.getString(AllValue.value2)//cái id
        your_pass= bundle.getString(AllValue.value3)//pass

        var call=Call_Receive_Server.instance
        tv_continue_changepass.setOnClickListener()
        {
            var i = 0
            tab_loading!!.visibility= View.VISIBLE
            dialog= Dialog(this)
            dialog_success= Dialog(this)
            pass1= edt_newpassword.text.toString()
            pass2=edt_newpassagain.text.toString()
            if(!pass1.equals(pass2))
            {
                dialog!!.requestWindowFeature(Window.FEATURE_LEFT_ICON)
                dialog!!.setContentView(R.layout.dialog_error_password)
                var btn_cancel_error_pass= dialog!!.findViewById(R.id.btn_cancel_error_pass) as Button
                btn_cancel_error_pass.setOnClickListener()
                {
                    dialog!!.cancel()
                    tab_loading!!.visibility=View.GONE
                }
            }
            if(pass1.equals(pass2))
            {
                Json.Operation="D"
                pass3= Encode().encryptString(pass1.toString())
                var inval: Array<String> = arrayOf(your_id!!.toString(),your_pass!!.toString(),pass3.toString()!!)
                call.CallEmit(AllValue.workername_change_pass,AllValue.servicename_change_pass,inval,AllValue.change_password!!.toString())
                Json.Operation="Q"
            }
            mCountDownTimer = object : CountDownTimer(5000, 1000)
            {
                override fun onTick(millisUntilFinished: Long)
                {
                    i++
                    Call_Receive_Server.instance.Sevecie()
                    //mProgressBar.progress = i
                    if(i==5)
                    {
                        tab_loading!!.visibility = View.GONE
                    }
                }
                override fun onFinish()
                {
                    //Do what you want
                    i++
                    if(i==5) {
                        tab_loading!!.visibility = View.GONE
                    }
                }
            }
            mCountDownTimer.start()
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getTemp()==AllValue.change_password)
        {
            if(event.getService()!!.getResult()=="1")
            {
                dialog_success!!.requestWindowFeature(Window.FEATURE_LEFT_ICON)
                dialog_success!!.setContentView(R.layout.dialog_error_password)
                var btn_success= dialog_success!!.findViewById(R.id.btn_success) as Button
                btn_success.setOnClickListener()
                {
                    if(resources.getString(R.string.mom_or_doctor)=="mom") {
                        sendToActivityMain(phone!!,your_pass!!, AllValue.gotomain_changepass!!)
                    }
                    else
                    {
                        sendToActivityMain_Doctor(phone!!,your_pass!!, AllValue.gotomain_changepass!!)
                    }
                    dialog_success!!.cancel()
                    tab_loading!!.visibility=View.GONE
                }
            }
            else
            {//đổi mật khẩu không thành công
                dialog!!.requestWindowFeature(Window.FEATURE_LEFT_ICON)
                dialog!!.setContentView(R.layout.dialog_error_password)
                var btn_cancel_error_pass= dialog!!.findViewById(R.id.btn_cancel_error_pass) as Button
                btn_cancel_error_pass.setOnClickListener()
                {
                    dialog!!.cancel()
                    tab_loading!!.visibility=View.GONE
                }
            }
        }
    }
    //goto main mom and baby
    fun sendToActivityMain(value: String,value2:String,resultcode:Int) {

        var intent3 = Intent(applicationContext, MainActivity::class.java)
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