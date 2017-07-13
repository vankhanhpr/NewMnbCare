package com.example.vankhanhpr.vidu2.login_baby

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.IsNumber
import com.example.vankhanhpr.vidu2.json.MessageEvent
import kotlinx.android.synthetic.main.activity_select_phonenumber.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 7/3/2017.
 */


class  Login: AppCompatActivity() {


    var phone:String?=null
    var progress_seclectphone1:ProgressBar? = null
    var dialog:Dialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_phonenumber)
        EventBus.getDefault().register(this)
        var mCountDownTimer: CountDownTimer
        progress_seclectphone1= findViewById(R.id.progress_seclectphone) as ProgressBar

        //lấy thông tin nhập
        //setCall(call_Return!!)
        tv_login_selectphone.setOnClickListener()
        {
            progress_seclectphone1!!.visibility=View.VISIBLE
            dialog= Dialog(this)



            phone =editText_phone.text.toString()
            var call= Call_Receive_Server.instance

            //tạo key

            //call.Sevecie()
            var inval: Array<String> = arrayOf(AllValue.isBaby!!.toString(),phone.toString())
            call!!.CallEmit(AllValue.workername_checknumber!!.toString(),AllValue.servicename_checknumber!!.toString(), inval, AllValue.checknumber.toString())

            mCountDownTimer = object : CountDownTimer(5000, 1000)
            {
                var i=0
                override fun onTick(millisUntilFinished: Long)
                {
                    i++
                    Call_Receive_Server.instance.Sevecie()
                    //mProgressBar.progress = i
                    if(i==5)
                    {
                        progress_seclectphone1!!.visibility = View.GONE
                    }
                }
                override fun onFinish()
                {
                    //Do what you want
                    i++
                    if(i==5) {
                        progress_seclectphone1!!.visibility = View.GONE
                    }
                }
            }
            mCountDownTimer.start()
        }
    }
    //Nhận kết quả trả về khi login
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {

        if(event.getTemp()==AllValue.checknumber.toString())//Kiểm tra kết quả trả về có phải của mình đã gửi đi hay không
        {
            var json:JSONObject?= event.getService()!!.getData() as JSONObject

            var isnum:IsNumber?= readJson1(json!!)//đọc json thành class
            if(isnum!!.C0=="Y")
            {
                //Toast.makeText(applicationContext,"Chuyển qua trang đăng nhập",Toast.LENGTH_SHORT).show()
                sendToActivityLogin(phone!!,AllValue.login!!)

            }
            else
            {
                progress_seclectphone1!!.visibility=View.GONE
                dialog!!.requestWindowFeature(Window.FEATURE_LEFT_ICON)
                dialog!!.setContentView(R.layout.dialog_sentto_signin)
                dialog!!.show()
                var btn_agree = dialog!!.findViewById(R.id.btn_agree) as Button
                var btn_cancel =dialog!!.findViewById(R.id.btn_cancel) as Button
                btn_cancel.setOnClickListener()
                {
                    dialog!!.cancel()
                }
                btn_agree!!.setOnClickListener()
                {
                    sendToActivitySignIn(phone!!,AllValue.signin!!)
                    dialog!!.cancel()
                }
            }
        }
    }
    //truyền số diện thoại qua các activity khác
    //Hàm chuyển qua Login
    fun sendToActivityLogin(value: String,resultcode:Int) {

        var intent3 = Intent(applicationContext,CheckPassLogin::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }

    //hàm chuyển qua Sign In
    fun sendToActivitySignIn(value: String,resultcode:Int) {
        var intent3 = Intent(applicationContext,CheckPassSignIn::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
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