package com.example.vankhanhpr.vidu2.login_baby

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.change_password.ChangePass
import com.example.vankhanhpr.vidu2.encode.Encode
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.IsNumber
import com.example.vankhanhpr.vidu2.getter_setter.User
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.json.Service_Response
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import android.os.CountDownTimer
import android.telecom.Call
import android.widget.ProgressBar
import android.widget.TextView
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.login_doctor.Login_Doctor
import com.example.vankhanhpr.vidu2.login_doctor.main_doctor


/**
 * Created by VANKHANHPR on 7/6/2017.
 */

class CheckPassLogin:AppCompatActivity()
{
    var phonem:String?=""
    var user:User= User()
    var valu:IsNumber= IsNumber()
    var pass:String?=null
    var tab_loading1: ProgressBar? = null
    var dialog:Dialog?=null
    var dialog_agree:Dialog?=null
    var dialog_error0:Dialog?=null

    var call= Call_Receive_Server.getIns()
    var pass2:String?=""
    var mCountDownTimer: CountDownTimer? = null
    var dialog_disconnect:Dialog?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        EventBus.getDefault().register(this)
        var inte:Intent= intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)
        phonem= bundle.getString(AllValue.value)
        Json.phone=phonem
        Log.d("sodienthoaidau",phonem!!+Json.phone)


        //----------Login cho bác sĩ
        if(getResources().getString(R.string.mom_or_doctor) == "doctor")
        {
            btn_continue.setOnClickListener()//tiến hành đăng nhập
            {
                dialog_disconnect= Dialog(this)
                dialog_disconnect!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog_disconnect!!.setContentView(R.layout.dialog_error_password)
                var tv_cancel = dialog_disconnect!!.findViewById(R.id.tv_error) as TextView
                var button_cancel = dialog_disconnect!!.findViewById(R.id.btn_cancel_error_pass)

                dialog_error0= Dialog(this)

                tab_loading1 = findViewById(R.id.tab_loading) as ProgressBar
                tab_loading1!!.visibility= View.VISIBLE
                var i = 0
                pass = edt_passlogin.text.toString()
                pass2 = Encode().encryptString(pass)
                var inval: Array<String> = arrayOf(phonem.toString(), pass2!!)

                call.CallEmit(AllValue.workername_checkpass!!.toString(), AllValue.servicename_checkpass!!.toString(), inval, AllValue.checkpass1!!.toString())
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
                            tab_loading1!!.visibility = View.GONE
                        }
                    }
                    override fun onFinish() {//Do what you want
                        i++
                        try {
                            dialog_disconnect!!.show()
                            tv_cancel.setText("Vui lòng kiểm tra kết nối của bạn")
                            button_cancel.setOnClickListener()
                            {
                                dialog_disconnect!!.cancel()
                            }
                            tab_loading1!!.visibility = View.GONE
                        } catch (e: Exception) {
                        }
                    }
                }
                mCountDownTimer!!.start()
            }
            //Trở về màn hình trước đó
            tv_return_selectphone.setOnClickListener()
            {
                var inten9=Intent(applicationContext,Login_Doctor::class.java)
                startActivity(inten9)
                finish()
            }
            tv_forget_pass.setOnClickListener()
            {
                dialog= Dialog(this)
                dialog_agree=Dialog(this)
               /* //lây id của hệ thống
                var array:Array<String>?= arrayOf(phone.toString())
                call.CallEmit(AllValue.workername_getID,AllValue.servicename_getID,array!!,AllValue.getId.toString())
*/
                //Log.d("idhethong",phone.toString()+valu.getSecC0().toString())
                dialog!!.requestWindowFeature(Window.FEATURE_LEFT_ICON)
                dialog_agree!!.requestWindowFeature(Window.FEATURE_LEFT_ICON)
                dialog!!.setContentView(R.layout.dialog_reset_password)
                dialog_agree!!.setContentView(R.layout.dialog_noticate_resetpass)

                dialog!!.show()
                var btn_cancel_dialogres=dialog!!.findViewById(R.id.btn_cancel_dialogres) as Button
                var btn_agree_dialogres= dialog!!.findViewById(R.id.btn_agree_dialogres) as Button

                btn_cancel_dialogres.setOnClickListener()
                {
                    dialog!!.cancel()
                }
                btn_agree_dialogres.setOnClickListener()
                {
                    var inval12: Array<String> = arrayOf(phonem!!.toString())
                    Log.d("id",valu.getSecC0().toString())
                    Json.Operation="E"
                    call.CallEmit(AllValue.workername_restartpass,AllValue.servicename_restartpass,inval12,AllValue.restart_passwork!!.toString())
                    Json.Operation="Q"
                    dialog_agree!!.show()
                    dialog!!.cancel()
                    var btn_iport_pass_restart =dialog_agree!!.findViewById(R.id.btn_iport_pass_restart) as Button
                    btn_iport_pass_restart.setOnClickListener()
                    {
                        dialog_agree!!.cancel()
                    }
                }
            }
            return
        }

        //-----------Login cho mẹ và bé-------------------
        //tiến hành đăng nhập
        btn_continue.setOnClickListener()//tiến hành đăng nhập
        {
            dialog_error0 = Dialog(this)

            dialog_disconnect= Dialog(this)
            dialog_disconnect!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_disconnect!!.setContentView(R.layout.dialog_error_password)
            var tv_cancel = dialog_disconnect!!.findViewById(R.id.tv_error) as TextView
            var button_cancel = dialog_disconnect!!.findViewById(R.id.btn_cancel_error_pass)

            tab_loading1 = findViewById(R.id.tab_loading) as ProgressBar
            tab_loading1!!.visibility= View.VISIBLE

            var i = 0

            pass = edt_passlogin.text.toString()
            pass2 = Encode().encryptString(pass)

            var inval4: Array<String> = arrayOf(phonem.toString(), pass2!!)
            call.CallEmit(AllValue.workername_checkpass!!.toString(), AllValue.servicename_checkpass!!.toString(), inval4, AllValue.checkpass1!!.toString())

            mCountDownTimer = object : CountDownTimer(10000, 1000)
            {
                override fun onTick(millisUntilFinished: Long)
                {
                    i++
                    if (i == 2)
                    {
                        Call_Receive_Server.getIns().Sevecie()
                        for (i in 0..Call_Receive_Server.getIns().hasmap!!.size - 1) {
                            Call_Receive_Server.getIns().hasmap!![i].setStatus(0)
                        }
                    }
                    if (i == 10) {
                        Call_Receive_Server.getIns().Sevecie()
                        tab_loading1!!.visibility = View.GONE
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
                        tab_loading1!!.visibility = View.GONE
                    } catch (e: Exception) {
                    }
                }
            }
            mCountDownTimer!!.start()
        }
        //Trở về màn hình trước đó
        tv_return_selectphone.setOnClickListener()
        {
            var inten9=Intent(applicationContext,Login::class.java)
            startActivity(inten9)
            finish()
        }

        //QUên mật khẩu
        tv_forget_pass.setOnClickListener()
        {
            dialog= Dialog(this)
            dialog_agree=Dialog(this)
            //lấy id của hệ thống
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_agree!!.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog!!.setContentView(R.layout.dialog_reset_password)
            dialog_agree!!.setContentView(R.layout.dialog_noticate_resetpass)

            dialog!!.show()
            var btn_cancel_dialogres=dialog!!.findViewById(R.id.btn_cancel_dialogres) as Button
            var btn_agree_dialogres= dialog!!.findViewById(R.id.btn_agree_dialogres) as Button

            btn_cancel_dialogres.setOnClickListener()
            {
                dialog!!.cancel()
            }
            btn_agree_dialogres.setOnClickListener()
            {
                Json.Operation="E"
                var inval15: Array<String> = arrayOf(Json.AppLoginID)
                //restart password
                call!!.CallEmit(AllValue.workername_sendcode,AllValue.servicename_sendcode,inval15,AllValue.restart_passwork!!.toString())
                Json.Operation="Q"
                dialog_agree!!.show()
                dialog!!.cancel()
                var btn_iport_pass_restart =dialog_agree!!.findViewById(R.id.btn_iport_pass_restart) as Button
                btn_iport_pass_restart.setOnClickListener()
                {
                    sendToActivityChangePass(phonem!!,348)
                    dialog_agree!!.cancel()
                }
            }
        }
    }
    //Chờ đợi kết quả khi gọi emit
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        var  serve:Service_Response = event.getService()!!
        if(event.getTemp()==AllValue.checkpass1.toString())//Kiểm tra kết quả trả check pass
        {

            if(serve.getResult()=="1")
            {
                var temp8:IsNumber= readJson1(event.getService()!!.getData()!!)
                Json.AppLoginPswd=pass2
                Json.AppLoginID=temp8.getSecC0()!!
                Log.d("sodienthoaigetid",phonem!!)
                var array:Array<String>?= arrayOf(Json.phone.toString())
                call!!.CallEmit(AllValue.workername_getID,AllValue.servicename_getID,array!!,AllValue.getId_Login.toString())

            }
            if(serve.getResult()=="0")
            {
                try {
                    tab_loading1!!.visibility = View.GONE
                    mCountDownTimer!!.cancel()
                    dialog_error0!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog_error0!!.setContentView(R.layout.dialog_impot_code)
                    dialog_error0!!.show()
                    var tv_set_import_codea = dialog_error0!!.findViewById(R.id.tv_set_import_code) as TextView
                    var btn_cancel_import_codea = dialog_error0!!.findViewById(R.id.btn_cancel_import_code) as Button
                    tv_set_import_codea.setText(resources.getText(R.string.error_password))
                    btn_cancel_import_codea.setOnClickListener()
                    {
                        dialog_error0!!.cancel()
                    }
                }
                catch (e:Exception){}
            }
        }
        if(event.getTemp()==AllValue.getId_Login)//lấy id của hệ thống
        {
            var x:ArrayList<JSONObject> = event.getService()!!.getData()!!
            var y:IsNumber=readJson1(x)
            if(y.getSecC0()!="N") {

                var Shared_Preferences : String = "IDSYSTEM"//........ ten thu muc chua
                var sharedpreferences:SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
                var editor2 : SharedPreferences.Editor?= sharedpreferences.edit()
                editor2!!.putString("id_system",Encode().encryptString(y.getSecC0().toString()))
                editor2.commit()
                mCountDownTimer!!.cancel()
                dialog_disconnect!!.cancel()
                if(getResources().getString(R.string.mom_or_doctor) == "mom")
                {//đến giao diện của mẹ và bé

                    Log.d("sodienthoaigotomainmom",phonem!!)
                    sendToActivityMain(Json.phone!!,pass2!!, AllValue.gotomain!!)
                }
                if(getResources().getString(R.string.mom_or_doctor) == "doctor")
                {//đến giao diện của bác sĩ
                    Log.d("sodienthoaigotomain_do",phonem!!)
                    sendToActivityMain_Doctor(Json.phone!!,pass2!!, AllValue.gotomain!!)
                }
            }
        }
    }
    //Mở màn hình chính
    fun sendToActivityMain(value:String,value2:String,resultcode:Int) {

        var intent3 = Intent(applicationContext,MainActivity::class.java)
        var bundle = Bundle()
        bundle.putString("Resuilt", value)
        bundle.putString("Resuilt2", value2)
        intent3.putExtra("Document", bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
    //goto main doctor
    fun sendToActivityMain_Doctor(value: String,value2:String,resultcode:Int) {

        var intent3 = Intent(applicationContext, main_doctor::class.java)
        var bundle = Bundle()

        bundle.putString(AllValue.value, value)//tai data
        bundle.putString(AllValue.value2,value2)//tai data

        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }

    //go to class change pass
    fun sendToActivityChangePass(value: String,resultcode:Int) {

        var intent3 = Intent(applicationContext, ChangePass::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value,value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
    //Đọc json gửi về
    fun readJson1(json: ArrayList<JSONObject>): IsNumber
    {
        var jsonO: JSONObject?=null
        if(json.size>0) {
            jsonO = json[0]
        }
        var c0: String? =jsonO!!.getString("c0")
        var ser1 : IsNumber = IsNumber()
        ser1.setSecC0(c0!!)
        return ser1
    }
}