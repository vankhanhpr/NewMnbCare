package com.example.vankhanhpr.vidu2.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.getter_setter.IsNumber
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.json.Service_Response
import kotlinx.android.synthetic.main.activity_select_phonenumber.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 7/3/2017.
 */


class  Login: AppCompatActivity() {


    var ser = Service_Response()
    var isRight:String?=null
    var key:String?= "isNumber"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_phonenumber)
        EventBus.getDefault().register(this)

        //lấy thông tin nhập

        var phone:String?=null

        //setCall(call_Return!!)
        btn_login.setOnClickListener()
        {
            phone =editText_phone.text.toString()
            var call= Call_Receive_Server.instance

            //tạo key

            //call.Sevecie()
            var inval: Array<String> = arrayOf("PHONE_CHK",phone.toString())
            call!!.CallEmit("ALTqCommon","ALTqCommon_CheckExists", inval, key.toString())
        }
    }
    //Nhận kết quả trả về khi login

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {

        if(event.getTemp()==key)//Kiểm tra kết quả trả về có phải của mình đã gửi đi hay không
        {
            var json:JSONObject?= event.getService()!!.getData() as JSONObject
            var isnum:IsNumber?= readJson1(json!!)//đọc json thành class
            if(isnum!!.C0=="Y")
            {
                Toast.makeText(applicationContext,"Nó cmndr",Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(applicationContext,"thất bại cmndr",Toast.LENGTH_SHORT).show()
            }
        }
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