package com.example.vankhanhpr.vidu2.login_baby

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_code)
        EventBus.getDefault().register(this)

        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra("Document")
        phone= bundle.getString("Resuilt")
        tv_phone_positively.setText(phone.toString())


        tv_cotinue_importcode.setOnClickListener()
        {
            positively= edt_positively.text.toString()
            var call= Call_Receive_Server.instance
            var inval: Array<String> = arrayOf("1",phone.toString(),positively!!.toString())
            call.CallEmit(AllValue.workername_verification_code,AllValue.servicename_verification_code,inval,AllValue.verification!!.toString())
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
                    sendToActivityMain(phone.toString(),AllValue.gotomain!!)
                }
                else
                    if(temp.getSecC0()=="N")
                    {
                        Toast.makeText(applicationContext,"Mã xác thực không hợp lệ",Toast.LENGTH_SHORT).show()
                    }
            }
            else
            {
                Toast.makeText(applicationContext,"ERROR SYSTEM",Toast.LENGTH_SHORT).show()
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
    fun sendToActivityMain(value: String,resultcode:Int) {

        var intent3 = Intent(applicationContext,MainActivity::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }

}