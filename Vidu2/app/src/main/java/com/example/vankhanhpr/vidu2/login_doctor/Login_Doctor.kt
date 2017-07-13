package com.example.vankhanhpr.vidu2.login_doctor

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
    var dialog_id_doctor: Dialog?=null
    var tab_loading_doctor1: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_id_doctor)
        EventBus.getDefault().register(this)


        var mCountDownTimer: CountDownTimer
        tab_loading_doctor1= findViewById(R.id.tab_loading_doctor) as ProgressBar

        btn_continue_doctor.setOnClickListener(){
            dialog_id_doctor= Dialog(this)
            tab_loading_doctor1!!.visibility= View.VISIBLE
            id_ = editText_id.text.toString()
            var call= Call_Receive_Server.instance
            call.Sevecie()
            var inval: Array<String> = arrayOf(AllValue.isDoctor!!.toString(),id_.toString())
            call!!.CallEmit(AllValue.workername_checknumber!!.toString(), AllValue.servicename_checknumber!!.toString(), inval, AllValue.checkid.toString())

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
                        tab_loading_doctor1!!.visibility = View.GONE
                    }
                }
                override fun onFinish()
                {
                    //Do what you want
                    i++
                    if(i==5) {
                        tab_loading_doctor1!!.visibility = View.GONE
                    }
                }
            }
            mCountDownTimer.start()

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
                sendToActivityLogin(id_!!,AllValue.login_doctor!!)
            }
            else
            {

                dialog_id_doctor!!.requestWindowFeature(Window.FEATURE_LEFT_ICON)
                dialog_id_doctor!!.setContentView(R.layout.dialog_impot_code)
                dialog_id_doctor!!.show()
                var tv_set_import_code =dialog_id_doctor!!.findViewById(R.id.tv_set_import_code) as TextView
                var btn_cancel_import_code1= dialog_id_doctor!!.findViewById(R.id.btn_cancel_import_code)as TextView
                tv_set_import_code.setText("ID đăng nhập không tồn tại vui lòng kiểm tra lại")
                btn_cancel_import_code1.setOnClickListener()
                {
                    dialog_id_doctor!!.cancel()
                }
                tab_loading_doctor1!!.visibility=View.GONE
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
        var intent3 = Intent(applicationContext,CheckPassLogin::class.java)
        var bundle = Bundle()
        bundle.putString("Resuilt", value)
        intent3.putExtra("Document", bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
}
