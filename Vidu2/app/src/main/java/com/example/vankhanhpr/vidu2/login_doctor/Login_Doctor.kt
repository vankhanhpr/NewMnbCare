package com.example.vankhanhpr.vidu2.login_doctor

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.IsNumber
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.login_baby.CheckPassLogin
import kotlinx.android.synthetic.main.activity_id_doctor.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 7/8/2017.
 */

class Login_Doctor : AppCompatActivity() {

    var id_:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_id_doctor)
        EventBus.getDefault().register(this)

        btn_doctorlogin.setOnClickListener(){
            id_ = editText_id.text.toString()
            var call= Call_Receive_Server.instance
            call.Sevecie()

            var inval: Array<String> = arrayOf(AllValue.isDoctor!!.toString(),id_.toString())
            call!!.CallEmit(AllValue.workername_checknumber!!.toString(), AllValue.servicename_checknumber!!.toString(), inval, AllValue.checkid.toString())
        }
    }
    //Nhận kết quả trả về khi login
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getTemp()== AllValue.checkid.toString())//Kiểm tra kết quả trả về có phải của mình đã gửi đi hay không
        {
            var json: JSONObject?= event.getService()!!.getData() as JSONObject
            Log.d("thang khanh",json.toString())
            var isid_: IsNumber?= readJson1(json!!)//đọc json thành class
            if(isid_!!.C0=="Y")
            {
                Toast.makeText(applicationContext,"thành công", Toast.LENGTH_SHORT).show()
                sendToActivityLogin(id_!!,AllValue.login_doctor!!)
            }
            else
            {
                Toast.makeText(applicationContext,"thất bại", Toast.LENGTH_SHORT).show()
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
    //Hàm chuyển qua Login
    fun sendToActivityLogin(value: String,resultcode:Int) {

        var intent3 = Intent(applicationContext, CheckPassLogin::class.java)
        var bundle = Bundle()
        bundle.putString("Resuilt", value)
        intent3.putExtra("Document", bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
}
