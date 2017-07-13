package com.example.vankhanhpr.vidu2.login_baby

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.encode.Encode
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.IsNumber
import com.example.vankhanhpr.vidu2.json.MessageEvent
import kotlinx.android.synthetic.main.activity_import_code.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 7/7/2017.
 */
class Import_Code :AppCompatActivity()
{
    var phone:String?=null
    var positively:String?=null
    var dialog11:Dialog?=null
    var password:String?=null

    var progreee_importcode1:ProgressBar?=null
    var call= Call_Receive_Server.instance

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_code)
        EventBus.getDefault().register(this)

        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)

        phone= bundle.getString(AllValue.value)
        password=bundle.getString(AllValue.value2)

        dialog11= Dialog(this)

        tv_phone_positively.setText(phone.toString())
        var mCountDownTimer: CountDownTimer
        progreee_importcode1= findViewById(R.id.progreee_importcode) as ProgressBar?

        tv_cotinue_importcode.setOnClickListener()
        {
            progreee_importcode1!!.visibility= View.VISIBLE
            positively= edt_positively.text.toString()
            var temp5=Encode().encryptString(positively)

            var inval: Array<String> = arrayOf("1",phone.toString(),temp5!!.toString())
            call.CallEmit(AllValue.workername_verification_code,AllValue.servicename_verification_code,inval,AllValue.verification!!.toString())

            //khi connect thất bại
            mCountDownTimer = object : CountDownTimer(5000, 1000) {
                var i=0
                override fun onTick(millisUntilFinished: Long) {
                    i++
                    Call_Receive_Server.instance.Sevecie()
                    //mProgressBar.progress = i
                    if(i==5) {
                        progreee_importcode1!!.visibility = View.GONE
                    }
                }
                override fun onFinish() {
                    //Do what you want
                    i++
                    if(i==5) {
                        progreee_importcode1!!.visibility = View.GONE
                    }
                }
            }
            mCountDownTimer.start()
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getTemp()==AllValue.verification.toString())//Kiểm tra kết quả trả về có phải của mình đã gửi đi hay không
        {
            if(event.getService()!!.getResult()=="1")
            {
                var temp :IsNumber= IsNumber()
                var json:JSONObject=event.getService()!!.getData()as JSONObject
                temp=readJson1(json)
                if(temp.getSecC0()=="Y")
                {
                    //tiến hành vào tràng chủ
                    sendToActivityMain(phone!!,password!!,AllValue.gotomain!!)
                }
                else
                    if(temp.getSecC0()=="N")
                    {
                        dialog11!!.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                        dialog11!!.setContentView(R.layout.dialog_impot_code)
                        var btn_cancel_import_code= dialog11!!.findViewById(R.id.btn_cancel_import_code)
                        dialog11!!.show()
                        btn_cancel_import_code.setOnClickListener()
                        {
                            dialog11!!.cancel()
                        }
                    }
            }
            else
                if(event.getService()!!.getResult()=="0")
                {
                    dialog11!!.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                    dialog11!!.setContentView(R.layout.dialog_impot_code)
                    var btn_cancel_import_code= dialog11!!.findViewById(R.id.btn_cancel_import_code)
                    var tv_set_import_code1= dialog11!!.findViewById(R.id.tv_set_import_code) as TextView
                    tv_set_import_code1.setText(event.getService()!!.getMessage())
                    dialog11!!.show()
                    btn_cancel_import_code.setOnClickListener()
                    {
                        dialog11!!.cancel()
                    }
                    progreee_importcode1!!.visibility= View.GONE
                }
        }
    }
    //Đọc file json
    fun readJson1(json1: JSONObject): IsNumber
    {
        var c0: String? =json1.getString("c0")
        var ser1 : IsNumber = IsNumber()
        ser1.setSecC0(c0!!)
        return ser1
    }
    //nhảy tới hàm main
    fun sendToActivityMain(value: String,value2:String,resultcode:Int) {

        var intent3 = Intent(applicationContext,MainActivity::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        bundle.putString(AllValue.value2, value2)

        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
}