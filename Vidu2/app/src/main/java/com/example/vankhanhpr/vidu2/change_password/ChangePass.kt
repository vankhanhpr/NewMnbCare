package com.example.vankhanhpr.vidu2.change_password

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
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
    var pass3:String?=null
    var call=Call_Receive_Server.getIns()
    var dialog_disconnect:Dialog?=null
    var mCountDownTimer: CountDownTimer? = null
    var f:Boolean?= true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        EventBus.getDefault().register(this)

        tab_loading= findViewById(R.id.tab_loading_changepass) as ProgressBar
        var passOid:String?=null
        var pass1:String?=null
        var pass2:String?=null
        var dialog_restart_pass:Dialog?=null


        var inte: Intent = intent//set data
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)
        phone=bundle.getString(AllValue.value)//lấy phone
        if(phone=="2305")
        {
            f=false
            var Shared_Preferences : String = "landau"
            var sharedpreferences : SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
            phone = sharedpreferences.getString("id","")
        }

        tv_continue_changepass.setOnClickListener()
        {
            dialog_disconnect= Dialog(this)
            dialog_disconnect!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_disconnect!!.setContentView(R.layout.dialog_error_password)
            var tv_cancel = dialog_disconnect!!.findViewById(R.id.tv_error) as TextView
            var button_cancel = dialog_disconnect!!.findViewById(R.id.btn_cancel_error_pass)

            var i = 0
            tab_loading!!.visibility= View.VISIBLE
            dialog= Dialog(this)
            dialog_success= Dialog(this)

            passOid= edt_oidpassword.text.toString()
            pass1= edt_newpassword.text.toString()
            pass2=edt_newpassagain.text.toString()
            if(!pass1.equals(pass2))
            {
                dialog!!.requestWindowFeature(Window.FEATURE_LEFT_ICON)
                dialog!!.setContentView(R.layout.dialog_error_password)
                dialog!!.show()
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
                your_pass=Encode().encryptString(passOid)
                pass3= Encode().encryptString(pass1.toString())
                //var s= Encode().encryptString(passOid)
                Json.AppLoginPswd = your_pass
                var inval: Array<String> = arrayOf(Json.AppLoginID!!.toString(),your_pass!!.toString(),pass3.toString()!!)
                call.CallEmit(AllValue.workername_change_pass,AllValue.servicename_change_pass,inval,AllValue.change_password!!.toString())
                Json.Operation="Q"
            }

            mCountDownTimer = object : CountDownTimer(10000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    i++
                    Call_Receive_Server.getIns().Sevecie()
                    if (i == 5) {
                        for (i in 0..Call_Receive_Server.getIns().hasmap!!.size - 1) {
                            Call_Receive_Server.getIns().hasmap!![i].setStatus(0)
                        }
                    }
                    if (i == 10) {
                        Call_Receive_Server.getIns().Sevecie()
                        tab_loading!!.visibility = View.GONE
                    }
                }
                override fun onFinish()
                {//Do what you want
                    i++
                    try {
                        Call_Receive_Server.getIns().Sevecie()
                        dialog_disconnect!!.show()
                        tv_cancel.setText("Vui lòng kiểm tra kết nối của bạn")
                        button_cancel.setOnClickListener()
                        {
                            dialog_disconnect!!.cancel()
                        }
                        tab_loading!!.visibility = View.GONE
                    } catch (e: Exception) {
                    }
                }
            }
            mCountDownTimer!!.start()
        }

        tab_sent_pass_again.setOnClickListener()
        {

            dialog_restart_pass= Dialog(this)
            dialog_restart_pass!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_restart_pass!!.setContentView(R.layout.dialog_noticate_resetpass)
            dialog_restart_pass!!.show()
            var btn_iport_pass_restart =dialog_restart_pass!!.findViewById(R.id.btn_iport_pass_restart) as Button
            btn_iport_pass_restart.setOnClickListener()
            {
                edt_oidpassword.setText("")
                edt_newpassword.setText("")
                edt_newpassagain.setText("")
                dialog_restart_pass!!.cancel()
            }
            Json.Operation="E"
            var inval1: Array<String> = arrayOf(Json.AppLoginID)
            //restart password
            call!!.CallEmit(AllValue.workername_change_pass,AllValue.servicename_change_pass,inval1,"anhthichtom")
            Json.Operation="Q"
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getTemp()==AllValue.change_password)
        {
            dialog_disconnect!!.cancel()
            mCountDownTimer!!.cancel()
            if(event.getService()!!.getResult()=="1")
            {
                dialog_success!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog_success!!.setContentView(R.layout.dialog_error_password)
                var btn_success= dialog_success!!.findViewById(R.id.btn_success) as Button
                btn_success.setOnClickListener()
                {
                    if(f!!) {
                        if (resources.getString(R.string.mom_or_doctor) == "mom") {
                            sendToActivityMain(phone!!, pass3!!, AllValue.gotomain_changepass!!)
                        } else {
                            sendToActivityMain_Doctor(phone!!, pass3!!, AllValue.gotomain_changepass!!)
                        }
                    }
                    else
                    {
                        finish()
                    }
                    dialog_success!!.cancel()
                    tab_loading!!.visibility=View.GONE
                }
            }
            else
            {//đổi mật khẩu không thành công
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.setContentView(R.layout.dialog_error_password)
                dialog!!.show()
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