package com.example.vankhanhpr.vidu2.login_baby

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.encode.Encode
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
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
    var phone:String?="999999"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra("Document")
        phone= bundle.getString("Resuilt")

        tv_continue_signin.setOnClickListener()
        {
            pass1=edt_password.text.toString()
            pass2=edt_passagain.text.toString()
            Log.d("passsss",pass1+"   "+pass2)
            if (!pass1.equals(pass2)) {

                Toast.makeText(applicationContext,"Password ERROR",Toast.LENGTH_SHORT).show()
            }
            else {
                if (pass1.equals(pass2))
                {

                    var dialog=Dialog(this)
                    dialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                    dialog.setContentView(R.layout.dialog_confirm_phone)
                    var tv_dialog_signin= dialog.findViewById(R.id.tv_dialog_signin) as TextView
                    var tv_cancel= dialog.findViewById(R.id.tv_cancel) as TextView
                    var tv_agree= dialog.findViewById(R.id.tv_agree) as TextView

                    tv_dialog_signin.setText("Chúc tối sẽ gửi mã xác nhận đến số điện thoại "+ phone.toString()+" để kích hoạt tài khoản của bạn")
                    dialog.show()


                    tv_cancel.setOnClickListener()//không đồng ý gửi mã xác thực
                    {
                        dialog.cancel()
                    }
                    tv_agree.setOnClickListener()//đồng ý nhận mã xác thực
                    {
                        var pass_ser = Encode().encryptString(pass1!!.toString())
                        var inval: Array<String> = arrayOf(pass_ser)

                        var call = Call_Receive_Server.instance
                        call.CallEmit(AllValue.workername_sendcode!!.toString(), AllValue.servicename_sendcode!!.toString(), inval, AllValue.signin_baby!!.toString())
                        sendToActivityMain(phone!!,123)
                        dialog.cancel()
                    }
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

    fun sendToActivityMain(value: String,resultcode:Int) {

        var intent3 = Intent(applicationContext,Import_Code::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }

}