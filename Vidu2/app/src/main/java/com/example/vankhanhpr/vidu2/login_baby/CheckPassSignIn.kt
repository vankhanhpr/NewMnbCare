package com.example.vankhanhpr.vidu2.login_baby

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.encode.Encode
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.IsNumber
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.json.MessageEvent
import kotlinx.android.synthetic.main.activity_signin.*
import kotlinx.android.synthetic.main.dialog_confirm_phone.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 7/6/2017.
 */
class  CheckPassSignIn:AppCompatActivity()
{
    var pass1:String?=null
    var pass2:String?=null
    var pass3:String?=null
    var phone:String?="999999"
    var dialog6:Dialog?=null

    var progreee_signin1:ProgressBar?=null
    var call = Call_Receive_Server.getIns()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        EventBus.getDefault().register(this)

        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)
        phone= bundle.getString(AllValue.value)

        progreee_signin1= findViewById(R.id.progreee_signin) as ProgressBar


        tv_continue_signin.setOnClickListener()
        {
            dialog6 =Dialog(this)
            pass1=edt_password.text.toString()
            pass2=edt_passagain.text.toString()

            var mCountDownTimer: CountDownTimer
            progreee_signin1!!.visibility= View.VISIBLE

            if (!pass1.equals(pass2))
            {
                progreee_signin1!!.visibility= View.GONE
                var dialog2=Dialog(this)
                dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog2.setContentView(R.layout.dialog_error_password)
                var btn_cancel_error_pass1= dialog2.findViewById(R.id.btn_cancel_error_pass)
                btn_cancel_error_pass1.setOnClickListener()
                {
                    dialog2.cancel()
                }
                dialog2.show()
            }
            else
            {
                if (pass1.equals(pass2))
                {
                    pass3= Encode().encryptString(pass2.toString())
                    boolPass(pass3!!)
                    mCountDownTimer = object : CountDownTimer(5000, 1000)
                    {
                        var i=0
                        override fun onTick(millisUntilFinished: Long)
                        {
                            i++
                           // Call_Receive_Server.instance.Sevecie()
                            //mProgressBar.progress = i
                            if(i==5)
                            {
                                progreee_signin1!!.visibility = View.GONE
                            }
                        }
                        override fun onFinish()
                        {
                            //Do what you want
                            i++
                            if(i==5) {
                                progreee_signin1!!.visibility = View.GONE
                            }
                        }
                    }
                    mCountDownTimer.start()
                }
            }
        }
        //Trở lại màn hình trước đó
        tv_return.setOnClickListener()
        {
            var intent_return=Intent(applicationContext,Login::class.java)
            startActivity(intent_return)
            finish()
        }
    }
    //nhan ket qua tra ve khi kiem tra mat khau
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getTemp()==AllValue.check_Pass)
        {
            var value_return:IsNumber= IsNumber()
            var json:JSONObject= event.getService()!!.getData() as JSONObject
            value_return= readJson1(json)


            if(value_return.getSecC0()=="Y")
            {

                progreee_signin1!!.visibility=View.GONE

                dialog6!!.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog6!!.setContentView(R.layout.dialog_confirm_phone)
                var tv_dialog_signin= dialog6!!.findViewById(R.id.tv_dialog_signin) as TextView
                var tv_cancel= dialog6!!.findViewById(R.id.tv_cancel) as TextView
                var tv_agree= dialog6!!.findViewById(R.id.tv_agree) as TextView
                dialog6!!.show()

                tv_dialog_signin.setText("Chúc tối sẽ gửi mã xác nhận đến số điện thoại "+ phone.toString()+" để kích hoạt tài khoản của bạn")


                tv_cancel.setOnClickListener()//không đồng ý gửi mã xác thực
                {
                    dialog6!!.cancel()
                }
                tv_agree.setOnClickListener()//đồng ý nhận mã xác thực
                {
                    var pass_ser = Encode().encryptString(pass1!!.toString())
                    var inval2: Array<String> = arrayOf(phone.toString())
                    Json.Operation="U"
                    call.CallEmit(AllValue.workername_sendcode!!.toString(), AllValue.servicename_sendcode!!.toString(),inval2 , AllValue.signin_baby!!.toString())

                    Log.d("passkhanh",phone+pass_ser.toString())
                    sendToActivityMain(phone!!,pass_ser.toString(), 123)

                    Json.Operation="Q"
                    dialog6!!.cancel()
                    finish()
                }
            }
            else
                if(value_return.getSecC0()=="N")
                {
                    progreee_signin1!!.visibility= View.GONE
                    var dialog3=Dialog(this)
                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog3.setContentView(R.layout.dialog_error_password)
                    var btn_cancel_error_pass3= dialog3.findViewById(R.id.btn_cancel_error_pass)
                    dialog3.show()
                    btn_cancel_error_pass3.setOnClickListener()
                    {
                        dialog3.cancel()
                    }
                }
        }
    }
    //Check pass word before sent code to phone number
    fun boolPass(s:String)
    {
        var inval: Array<String> = arrayOf("2",s.toString())
        call!!.CallEmit(AllValue.workername_checkpass_signin!!,AllValue.servicename_checkpas_sign!!,inval,AllValue.check_Pass!!)
    }

    fun sendToActivityMain(value: String,value2:String,resultcode:Int) {

        var intent3 = Intent(applicationContext,Import_Code::class.java)
        var bundle = Bundle()

        bundle.putString(AllValue.value, value)
        bundle.putString(AllValue.value2, value2)

        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
    // Đọc file Json để lấy kết quả
    fun readJson1(json1: JSONObject): IsNumber
    {
        var c0: String? =json1.getString("c0")
        var ser1 : IsNumber = IsNumber()
        ser1.setSecC0(c0!!)
        return ser1
    }
}