package com.example.vankhanhpr.vidu2.login_baby

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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

/**
 * Created by VANKHANHPR on 7/6/2017.
 */

class CheckPassLogin:AppCompatActivity()
{
    var phone:String?=null
    var user:User= User()
    var valu:IsNumber= IsNumber()
    var key_lock:Int?=0//biến cho biết là đăng nhập hay là lấy lại mật khẩu
    var pass:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        EventBus.getDefault().register(this)

        var id:String?=null

        var inte:Intent= intent
        var bundle:Bundle=inte.getBundleExtra("Document")
        phone= bundle.getString("Resuilt")

        //tiến hành đăng nhập
        btn_continue.setOnClickListener()//tiến hành đăng nhập
        {
            if(key_lock==0) {
                pass = edt_passlogin.text.toString()
                var pass2 = Encode().encryptString(pass)
                Log.d("psss is:", pass2.toString())
                var inval: Array<String> = arrayOf(phone.toString(), pass2)

                var call = Call_Receive_Server.instance
                call!!.CallEmit(AllValue.workername_checkpass!!.toString(), AllValue.servicename_checkpass!!.toString(), inval, AllValue.checkpass!!.toString())
            }
            if(key_lock==1)
            {

                pass = edt_passlogin.text.toString()
                var pass2 = Encode().encryptString(pass)
                Log.d("psss is:", pass2.toString())
                var inval: Array<String> = arrayOf(phone.toString(), pass2)

                var call = Call_Receive_Server.instance
                call!!.CallEmit(AllValue.workername_checkpass!!.toString(), AllValue.servicename_checkpass!!.toString(), inval, AllValue.checpass_getpass!!.toString())
            }
        }
        //Trở về màn hình trước đó
        tv_return_selectphone.setOnClickListener()
        {
            var inten4=Intent(applicationContext,Login::class.java)
            startActivity(inten4)
            finish()
        }
        //QUên mật khẩu
        tv_forget_pass.setOnClickListener()
        {
            var call= Call_Receive_Server.instance
            var dialog= Dialog(this)
            var dialog_agree=Dialog(this)

            var array:Array<String>?= arrayOf(phone.toString())
            call.CallEmit(AllValue.workername_getID.toString(),AllValue.servicename_getID.toString(),array!!,AllValue.getId.toString())



            dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON)
            dialog_agree.requestWindowFeature(Window.FEATURE_LEFT_ICON)

            dialog.setContentView(R.layout.dialog_reset_password)
            dialog_agree.setContentView(R.layout.dialog_noticate_resetpass)

            dialog.show()
            var btn_cancel_dialogres=dialog.findViewById(R.id.btn_cancel_dialogres) as Button
            var btn_agree_dialogres= dialog.findViewById(R.id.btn_agree_dialogres) as Button

            btn_cancel_dialogres.setOnClickListener()
            {
                dialog.cancel()
            }
            btn_agree_dialogres.setOnClickListener()
            {
                var inval1: Array<String> = arrayOf(valu.getSecC0()!!.toString())

                call.CallEmit(AllValue.workername_restartpass.toString(),AllValue.servicename_restartpass.toString(),inval1,AllValue.restart_passwork!!.toString())

                dialog_agree.show()
                dialog.cancel()
                var btn_iport_pass_restart =dialog_agree.findViewById(R.id.btn_iport_pass_restart) as Button
                btn_iport_pass_restart.setOnClickListener()
                {
                    dialog_agree.cancel()
                    key_lock=1;
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
                var json_user:JSONObject= event.getService()!!.getData()!!
                /*user=readJson1(json_user)
                Log.d("thông tin trả về là: ",json_user.toString())*/
                sendToActivityMain(json_user.toString(),AllValue.gotomain!!)
            }
            else{
                if(serve.getResult()=="0")
                {
                    Toast.makeText(applicationContext,"Mật khẩu không đúng hoặc đã xảy ra lỗi",Toast.LENGTH_SHORT).show()
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
                /*user=readJson1(json_user)
                Log.d("thông tin trả về là: ",json_user.toString())*/
                sendToActivityChangePass(valu.getSecC0().toString()!!,pass!!,AllValue.gotomain!!)
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
    fun sendToActivityMain(value: String,resultcode:Int) {

        var intent3 = Intent(applicationContext,MainActivity::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
    //go to class change pass
    fun sendToActivityChangePass(value: String,value2:String,resultcode:Int) {

        var intent3 = Intent(applicationContext, ChangePass::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        bundle.putString(AllValue.value2,value2)

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