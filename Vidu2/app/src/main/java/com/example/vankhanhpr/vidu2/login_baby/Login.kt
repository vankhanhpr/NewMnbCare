package com.example.vankhanhpr.vidu2.login_baby

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import com.example.vankhanhpr.vidu2.encode.Encode
import com.example.vankhanhpr.vidu2.getter_setter.Json
import java.util.Collections.replaceAll




/**
 * Created by VANKHANHPR on 7/3/2017.
 */


class  Login: AppCompatActivity() {


    var phone:String?=""
    var progress_seclectphone1:ProgressBar? = null
    var dialog:Dialog?=null
    var dialog_disconnect:Dialog?=null

    var call= Call_Receive_Server.getIns()
    var f:Boolean?=true
    var number:String?=""
    var mCountDownTimer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_phonenumber)
        EventBus.getDefault().register(this)



        var Shared_Preferences : String = "landau"//........ ten thu muc chua
        var sharedpreferences: SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)

        Log.d("bienid",sharedpreferences.getString("id","").toString())


        progress_seclectphone1= findViewById(R.id.progress_seclectphone) as ProgressBar
        var edit_text= findViewById(R.id.editText_phone) as EditText

        edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                var text = edit_text.getText().toString()
                edit_text.setTextColor(resources.getColor(R.color.dark))
                if(f!! && text!!.length>3) {
                    if(text.substring(0,1)!="+")
                    {
                        phone=text
                    }
                    number=text
                    if (number!!.substring(1, 2) == "9" || number!!.substring(1, 2) == "8")
                    {
                        if (number!!.length == 10)
                        {
                            var x = text.substring(1, 4)
                            var x1 = text.substring(4, 7)
                            var x2 = text.substring(7)
                            var l= "+84" + "-" + x + "-" + x1 + "-" + x2
                            edit_text.setText("+84" + "-" + x + "-" + x1 + "-" + x2)
                            edit_text.setTextColor(resources.getColor(R.color.green))
                            edit_text.setSelection(l.length)//vị trí pointer
                            f = false
                        }
                    }
                    else
                    {
                        if (number!!.length == 11) {
                            var x = text.substring(1, 4)
                            var x1 = text.substring(4, 7)
                            var x2 = text.substring(7)
                            var l1= "+84" + "-" + x + "-" + x1 + "-" + x2
                            edit_text.setText("+84" + "-" + x + "-" + x1 + "-" + x2)
                            edit_text.setTextColor(resources.getColor(R.color.green))
                            edit_text.setSelection(l1.length)//vị trí pointer
                            f = false
                        }
                    }
                }
                else {
                    //Log.d("chuoi",phone)
                    if (!f!!) {
                        f=true
                        if (number!!.substring(4, 5) == "9"|| number!!.substring(4, 5) == "8") {
                            var x = text.substring(4, 7)
                            var x1 = text.substring(8, 11)
                            var x2 = text.substring(12)
                            var s = "0" + x + x1 + x2

                            edit_text.setText(s)
                            edit_text.setSelection(s.length)
                            phone=s
                        }
                        else
                        {
                            var x4 = text.substring(4, 7)
                            var x5 = text.substring(8, 11)
                            var x6 = text.substring(12)
                            var s = "0" + x4 + x5 + x6

                            edit_text.setText(s)
                            edit_text.setSelection(s.length)
                            phone=s
                        }
                    }
                }
            }
        })
        //lấy thông tin nhập
        //setCall(call_Return!!)
        tv_login_selectphone.setOnClickListener()
        {
            dialog_disconnect= Dialog(this)
            dialog_disconnect!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_disconnect!!.setContentView(R.layout.dialog_error_password)
            var tv_cancel = dialog_disconnect!!.findViewById(R.id.tv_error) as TextView
            var button_cancel = dialog_disconnect!!.findViewById(R.id.btn_cancel_error_pass)

            if(!boolPhone(phone!!))
            {
                dialog_disconnect!!.show()
                tv_cancel.setText(resources.getString(R.string.phone_error))
                button_cancel.setOnClickListener()
                {
                    dialog_disconnect!!.cancel()
                }
            }
            else {
                progress_seclectphone1!!.visibility = View.VISIBLE
                dialog = Dialog(this)

                var inval: Array<String> = arrayOf(AllValue.isBaby!!.toString(), phone.toString())
                call!!.CallEmit(AllValue.workername_checknumber!!.toString(), AllValue.servicename_checknumber!!.toString(), inval, AllValue.checknumber.toString())
                mCountDownTimer = object : CountDownTimer(15000, 1000) {
                    var i = 0
                    override fun onTick(millisUntilFinished: Long) {
                        i++
                        Call_Receive_Server.getIns().Sevecie()
                        if (i == 5) {

                            for (i in 0..Call_Receive_Server.getIns().hasmap!!.size - 1) {
                                Call_Receive_Server.getIns().hasmap!![i].setStatus(0)
                            }
                        }
                        //mProgressBar.progress = i
                        if (i == 15) {
                            Call_Receive_Server.getIns().Sevecie()
                            progress_seclectphone1!!.visibility = View.GONE
                        }
                    }

                    override fun onFinish() {
                        //Do what you want
                        i++
                        /*if(i==15)
                    {*/
                        try {
                            dialog_disconnect!!.show()
                            tv_cancel.setText("Vui lòng kiểm tra kết nối của bạn")
                            button_cancel.setOnClickListener()
                            {
                                dialog_disconnect!!.cancel()
                            }
                            progress_seclectphone1!!.visibility = View.GONE
                        } catch (e: Exception) {
                        }
                        /*}*/
                    }
                }
                mCountDownTimer!!.start()
            }
        }
    }
    //Nhận kết quả trả về khi login
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {

        if(event.getTemp()==AllValue.checknumber.toString())//Kiểm tra kết quả trả về có phải của mình đã gửi đi hay không
        {
            var json:ArrayList<JSONObject>?= event.getService()!!.getData()

            var isnum:IsNumber?= readJson1(json!!)//đọc json thành class
            if(isnum!!.C0=="Y")
            {
               // var array:Array<String>?= arrayOf(phone.toString())
                //call!!.CallEmit(AllValue.workername_getID,AllValue.servicename_getID,array!!,AllValue.getId_Main.toString())
                mCountDownTimer!!.cancel()

                sendToActivityLogin(phone!!,AllValue.login!!)
            }
            else
            {
                try {
                    mCountDownTimer!!.cancel()
                    progress_seclectphone1!!.visibility = View.GONE
                    dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog!!.setContentView(R.layout.dialog_sentto_signin)
                    dialog!!.show()
                    var btn_agree = dialog!!.findViewById(R.id.btn_agree) as Button
                    var btn_cancel = dialog!!.findViewById(R.id.btn_cancel) as Button
                    btn_cancel.setOnClickListener()
                    {
                        dialog!!.cancel()
                    }
                    btn_agree!!.setOnClickListener()
                    {
                        sendToActivitySignIn(phone!!, AllValue.signin!!)
                        dialog!!.cancel()
                    }
                }
                catch (e:Exception){}
            }
        }

        /*if(event.getTemp()==AllValue.getId_Main)
        {
            var tem4:IsNumber?=null
            tem4=readJson1(event.getService()!!.getData()!!)
            Json.AppLoginID=tem4.getSecC0()!!
            Log.d("AllValue",tem4.getSecC0())
        }*/
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
    fun readJson1(json1: ArrayList<JSONObject>): IsNumber
    {
        var jsonO: JSONObject?=null
        if(json1.size>0) {
            jsonO = json1[0]
        }
        var c0: String? =jsonO!!.getString("c0")
        var ser1 : IsNumber = IsNumber()
        ser1.setSecC0(c0!!)
        return ser1
    }

    //Kiểm tra số điện thoại
    fun boolPhone(phone2:String):Boolean {
        if (phone2.length == 10 && (phone2.substring(1,2) == "9" || phone2.substring(1,2) == "8" )) {
            return true
        }
        if (phone2.length == 11 && phone2.substring(1,2) == "1") {
            return true
        }
        return  false
    }

}