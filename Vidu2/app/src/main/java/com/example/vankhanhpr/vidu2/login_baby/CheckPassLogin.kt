package com.example.vankhanhpr.vidu2.login_baby

import android.app.Dialog
import android.content.Intent
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
    var phone:String?=""
    var user:User= User()
    var valu:IsNumber= IsNumber()
    var key_lock:Int?=0//biến cho biết là đăng nhập hay là lấy lại mật khẩu
    var pass:String?=null
    var tab_loading1: ProgressBar? = null
    var dialog:Dialog?=null
    var dialog_agree:Dialog?=null
    var dialog_error:Dialog?=null

    var call= Call_Receive_Server.instance
    var pass2:String?=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        EventBus.getDefault().register(this)

        var id:String?=null

        var inte:Intent= intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)
        phone= bundle.getString(AllValue.value)
        Log.d("phone1",phone)


        //----------Login cho bác sĩ
        if(getResources().getString(R.string.mom_or_doctor) == "doctor")
        {
            btn_continue.setOnClickListener()//tiến hành đăng nhập
            {

                dialog_error= Dialog(this)

                tab_loading1 = findViewById(R.id.tab_loading) as ProgressBar
                tab_loading1!!.visibility= View.VISIBLE

                var mCountDownTimer: CountDownTimer
                var i = 0
                pass = edt_passlogin.text.toString()
                pass2 = Encode().encryptString(pass)
                var inval: Array<String> = arrayOf(phone.toString(), pass2!!)

                var call = Call_Receive_Server.instance
                call!!.CallEmit(AllValue.workername_checkpass!!.toString(), AllValue.servicename_checkpass!!.toString(), inval, AllValue.checkpass!!.toString())

                mCountDownTimer = object : CountDownTimer(5000, 1000)
                {
                    override fun onTick(millisUntilFinished: Long)
                    {
                        i++
                        Call_Receive_Server.instance.Sevecie()
                        //mProgressBar.progress = i
                        if(i==5)
                        {
                            tab_loading1!!.visibility = View.GONE
                        }
                    }
                    override fun onFinish()
                    {
                        //Do what you want
                        i++
                        if(i==5) {
                            tab_loading1!!.visibility = View.GONE
                        }
                    }
                }
                mCountDownTimer.start()
            }
            //Trở về màn hình trước đó
            tv_return_selectphone.setOnClickListener()
            {
                key_lock=0
                var inten4=Intent(applicationContext,Login_Doctor::class.java)
                startActivity(inten4)
                finish()
            }
            tv_forget_pass.setOnClickListener()
            {
                key_lock=0
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
                    var inval1: Array<String> = arrayOf(phone!!.toString())
                    Log.d("id",valu.getSecC0().toString())
                    Json.Operation="E"
                    call.CallEmit(AllValue.workername_restartpass,AllValue.servicename_restartpass,inval1,AllValue.restart_passwork!!.toString())
                    Json.Operation="Q"
                    dialog_agree!!.show()
                    dialog!!.cancel()
                    var btn_iport_pass_restart =dialog_agree!!.findViewById(R.id.btn_iport_pass_restart) as Button
                    btn_iport_pass_restart.setOnClickListener()
                    {
                        dialog_agree!!.cancel()
                        key_lock=1
                    }
                }
            }
            return
        }

        //-----------Login cho mẹ và bé-------------------
        //tiến hành đăng nhập
        btn_continue.setOnClickListener()//tiến hành đăng nhập
        {
            dialog_error= Dialog(this)
            tab_loading1 = findViewById(R.id.tab_loading) as ProgressBar
            tab_loading1!!.visibility= View.VISIBLE

            var mCountDownTimer: CountDownTimer
            var i = 0

            pass = edt_passlogin.text.toString()
            pass2 = Encode().encryptString(pass)
            var inval: Array<String> = arrayOf(phone.toString(), pass2!!)

            var call = Call_Receive_Server.instance
            call!!.CallEmit(AllValue.workername_checkpass!!.toString(), AllValue.servicename_checkpass!!.toString(), inval, AllValue.checkpass!!.toString())

            mCountDownTimer = object : CountDownTimer(5000, 1000)
            {
                override fun onTick(millisUntilFinished: Long)
                {
                    i++
                    Call_Receive_Server.instance.Sevecie()
                    //mProgressBar.progress = i
                    if(i==5)
                    {
                        tab_loading1!!.visibility = View.GONE
                    }
                }
                override fun onFinish()
                {
                    //Do what you want
                    i++
                    if(i==5) {
                        tab_loading1!!.visibility = View.GONE
                    }
                }
            }
            mCountDownTimer.start()
        }
        //Trở về màn hình trước đó
        tv_return_selectphone.setOnClickListener()
        {
            key_lock=0
            var inten4=Intent(applicationContext,Login::class.java)
            startActivity(inten4)
            finish()
        }
        //QUên mật khẩu
        tv_forget_pass.setOnClickListener()
        {
            key_lock=0
            dialog= Dialog(this)
            dialog_agree=Dialog(this)
            //lấy id của hệ thống
            var array:Array<String>?= arrayOf(phone.toString())
            call.CallEmit(AllValue.workername_getID,AllValue.servicename_getID,array!!,AllValue.getId.toString())

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


                Json.Operation="E"
                var inval1: Array<String> = arrayOf(valu.getSecC0()!!.toString())
                call.CallEmit(AllValue.workername_restartpass,AllValue.servicename_restartpass,inval1,AllValue.restart_passwork!!.toString())
                Json.Operation="Q"
                dialog_agree!!.show()
                dialog!!.cancel()
                var btn_iport_pass_restart =dialog_agree!!.findViewById(R.id.btn_iport_pass_restart) as Button
                btn_iport_pass_restart.setOnClickListener()
                {
                    key_lock=1
                    dialog_agree!!.cancel()

                }
            }
        }
    }
    //Chờ đợi kết quả khi gọi emit
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        var  serve:Service_Response = event.getService()!!
        if(event.getTemp()==AllValue.checkpass.toString())//Kiểm tra kết quả trả về có phải của mình đã gửi đi hay không
        {
            if(serve.getResult()=="1")
            {
                var json_user: JSONObject = event.getService()!!.getData()!!
                if(getResources().getString(R.string.mom_or_doctor) == "mom")
                {//đến giao diện của mẹ và bé
                    if(key_lock==0) {
                        Log.d("phone2",phone)
                        sendToActivityMain(phone!!,pass2!!, AllValue.gotomain!!)
                    }
                    else{
                        var s=Encode().encryptString(pass.toString())
                        key_lock=0
                        sendToActivityChangePass(phone!!,valu.getSecC0()!!.toString(),s,AllValue.gotomain_changepass!!)
                    }
                }
                if(getResources().getString(R.string.mom_or_doctor) == "doctor")
                {//đến giao diện của bác sĩ

                    if(key_lock==0) {
                        sendToActivityMain_Doctor(phone!!,pass2!!, AllValue.gotomain!!)
                    }
                    else
                    {
                        key_lock=0
                        var s=Encode().encryptString(pass.toString())
                        sendToActivityChangePass(phone!!,valu.getSecC0()!!.toString(),s,AllValue.gotomain_changepass!!)
                    }
                }
            }
            else{
                if(serve.getResult()=="0")
                {
                    dialog_error!!.requestWindowFeature(Window.FEATURE_LEFT_ICON)
                    dialog_error!!.setContentView(R.layout.dialog_impot_code)
                    dialog_error!!.show()
                    var tv_set_import_code= dialog_error!!.findViewById(R.id.tv_set_import_code) as TextView
                    var btn_cancel_import_code= dialog_error!!.findViewById(R.id.btn_cancel_import_code) as Button
                    tv_set_import_code.setText(resources.getText(R.string.error_password))
                    btn_cancel_import_code.setOnClickListener()
                    {
                        dialog_error!!.cancel()
                    }
                    tab_loading1!!.visibility= View.GONE
                }
            }
        }
        if(event.getTemp()==AllValue.getId.toString())
        {
            var json_getid= serve.getData()
            valu = readJson1(json_getid!!)
        }

        if(event.getTemp()==AllValue.checpass_getpass.toString())//Kiểm tra kết quả trả về có phải của mình đã gửi đi hay không
        {
            if(serve.getResult()=="1")
            {
                var json_user:JSONObject= event.getService()!!.getData()!!
                sendToActivityChangePass(phone!!,valu.getSecC0().toString()!!,pass!!,AllValue.gotomain!!)
            }
            else{
                if(serve.getResult()=="0")
                {
                    Toast.makeText(applicationContext,"Mật khẩu không đúng hoặc đã xảy ra lỗi",Toast.LENGTH_SHORT).show()
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
    fun sendToActivityChangePass(value: String,value2:String,value3:String,resultcode:Int) {

        var intent3 = Intent(applicationContext, ChangePass::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        bundle.putString(AllValue.value2,value2)
        bundle.putString(AllValue.value3,value3)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
    //Đọc json gửi về
    fun readJson1(json: JSONObject): IsNumber
    {
        var C0: String? =json.getString("c0")
        var value : IsNumber= IsNumber()
        value.setSecC0(C0!!)
        return value
    }

}