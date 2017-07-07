package com.example.vankhanhpr.vidu2.login_baby

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.encode.Encode
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.User
import com.example.vankhanhpr.vidu2.json.MessageEvent
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 7/6/2017.
 */

class CheckPassLogin:AppCompatActivity()
{
    var phone:String?=null
    var user:User= User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        EventBus.getDefault().register(this)
        var pass:String?=null

        var inte:Intent= intent
        var bundle:Bundle=inte.getBundleExtra("Document")
        phone= bundle.getString("Resuilt")

        btn_continue.setOnClickListener()//tiến hành đăng nhập
        {
            pass = edt_passlogin.text.toString()
            var pass2= Encode().encryptString(pass)
            Log.d("psss is:",pass2.toString())
            var inval: Array<String> = arrayOf(phone.toString(),pass2)

            var call= Call_Receive_Server.instance
            call!!.CallEmit(AllValue.workername_checkpass!!.toString(),AllValue.servicename_checkpass!!.toString(),inval,AllValue.checkpass!!.toString())

        }
    }
    //Chờ đợi kết quả khi gọi emit
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getTemp()==AllValue.checkpass.toString())//Kiểm tra kết quả trả về có phải của mình đã gửi đi hay không
        {
            var json_user:JSONObject= event.getService()!!.getData()!!
            user=readJson1(json_user)

            if(true)
            {
                sendToActivityMain(json_user.toString(),AllValue.gotomain!!)
            }
        }
    }
    //Mở màn hình chính
    fun sendToActivityMain(value: String,resultcode:Int) {

        var intent3 = Intent(applicationContext,CheckPassLogin::class.java)
        var bundle = Bundle()
        bundle.putString("Resuilt", value)
        intent3.putExtra("Document", bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }

    //Đọc json gửi về
    fun readJson1(json: JSONObject): User
    {
        var UserID: String? =json.getString("c0")
        var Customer_Name: String? =json.getString("c1")
        var Nick_Name: String? =json.getString("c2")
        var Birthday: String? =json.getString("c3")
        var Mobile_Phone: String? =json.getString("c4")
        var Email: String? =json.getString("c5")
        var Address: String? =json.getString("c6")
        var Sex: String? =json.getString("c7")
        var Working_Date: String? =json.getString("c8")

        var user2 : User= User()
        user2.setUserID(UserID!!)
        user2.setCustomer_Name(Customer_Name!!)
        user2.setNick_Name(Nick_Name!!)
        user2.setBirthday(Birthday!!)
        user2.setMobile_Phone(Mobile_Phone!!)
        user2.setEmail(Email!!)
        user2.setAddress(Address!!)
        user2.setSex(Sex!!)
        user2.setWorking_Date(Working_Date!!)
        return user2
    }

}