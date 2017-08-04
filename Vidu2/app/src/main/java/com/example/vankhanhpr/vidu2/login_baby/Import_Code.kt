package com.example.vankhanhpr.vidu2.login_baby

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.vankhanhpr.vidu2.getter_setter.Json
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
    var dialog_disconnect:Dialog?=null
    var dialog_success:Dialog?=null

    var progreee_importcode1:ProgressBar?=null
    var call= Call_Receive_Server.getIns()
    var mCountDownTimer: CountDownTimer? = null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_code)
        EventBus.getDefault().register(this)

        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)

        phone= bundle.getString(AllValue.value)
        password=bundle.getString(AllValue.value2)

        tv_phone_positively.setText(phone.toString())
        progreee_importcode1= findViewById(R.id.progreee_importcode) as ProgressBar?

        //Kiểm tra mã xác thực
        tv_cotinue_importcode.setOnClickListener()
        {
            dialog11= Dialog(this)
            progreee_importcode1!!.visibility= View.VISIBLE
            positively= edt_positively.text.toString()
            var temp5=Encode().encryptString(positively)


            dialog_disconnect= Dialog(this)
            dialog_disconnect!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_disconnect!!.setContentView(R.layout.dialog_error_password)
            var tv_cancel = dialog_disconnect!!.findViewById(R.id.tv_error) as TextView
            var button_cancel = dialog_disconnect!!.findViewById(R.id.btn_cancel_error_pass)


            //kiểm tra code đúng không
            var inval: Array<String> = arrayOf("1",phone.toString(),temp5!!.toString())
            call.CallEmit(AllValue.workername_verification_code,AllValue.servicename_verification_code,inval,AllValue.verification2!!.toString())

            //khi connect thất bại
            mCountDownTimer = object : CountDownTimer(7000, 1000) {
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
                    if (i == 7) {
                        Call_Receive_Server.getIns().Sevecie()
                        progreee_importcode1!!.visibility = View.GONE
                    }
                }
                override fun onFinish() {
                    //Do what you want
                    i++
                    try {
                        dialog_disconnect!!.show()
                        tv_cancel.setText("Vui lòng kiểm tra kết nối của bạn")
                        button_cancel.setOnClickListener()
                        {
                            dialog_disconnect!!.cancel()
                        }
                        progreee_importcode1!!.visibility = View.GONE
                    } catch (e: Exception) {
                    }
                }
            }
            mCountDownTimer!!.start()
        }
        //Lấy lại mã đăng kí trên phone
        tv_restartpass_again.setOnClickListener()
        {
            dialog_success= Dialog(this)
            dialog_success!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_success!!.setContentView(R.layout.dialog_success)
            var tv_error1= dialog_success!!.findViewById(R.id.tv_error) as TextView
            var btn_success1=dialog_success!!.findViewById(R.id.btn_success)
            dialog_success!!.show()
            tv_error1.setText(resources.getString(R.string.notical_confirm))
            btn_success1.setOnClickListener()
            {
                dialog_success!!.cancel()
            }
            var inval2: Array<String> = arrayOf(phone.toString())
            Json.Operation="U"
            call.CallEmit(AllValue.workername_sendcode!!.toString(), AllValue.servicename_sendcode!!.toString(),inval2 , AllValue.signin_baby!!.toString())
            Json.Operation="Q"
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getTemp()==AllValue.verification2.toString())//Kiểm tra kết quả trả về có phải của mình đã gửi đi hay không
        {
            dialog_disconnect!!.cancel()
            mCountDownTimer!!.cancel()
            if(event.getService()!!.getResult()=="1")
            {
                var temp :IsNumber= IsNumber()
                var json:ArrayList<JSONObject> = event.getService()!!.getData()!!
                temp=readJson1(json)
                if(temp.getSecC0()=="Y")
                {
                    //Thêm mới thông tin khách hàng khi import code thành công
                    Json.Operation="I"
                    var inval5: Array<String> = arrayOf("","",phone!!,"","","","",password!!)
                    call!!.CallEmit(AllValue.workername_sendcode,AllValue.servicename_sendcode,inval5,AllValue.insetCustomer!!)
                    Json.Operation="Q"
                }
                else//import code không thành công
                    /*if(temp.getSecC0()=="N")*/
                    {
                        dialog11!!.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog11!!.setContentView(R.layout.dialog_impot_code)
                        var btn_cancel_import_code= dialog11!!.findViewById(R.id.btn_cancel_import_code)
                        dialog11!!.show()
                        btn_cancel_import_code.setOnClickListener()
                        {
                            dialog11!!.cancel()
                        }
                    }
            }
            else//báo lỗi khi trả về kết quả không thành công
                if(event.getService()!!.getResult()=="0")
                {
                    dialog11!!.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog11!!.setContentView(R.layout.dialog_impot_code)
                    var btn_cancel_import_code= dialog11!!.findViewById(R.id.btn_cancel_import_code)
                    var tv_set_import_code1= dialog11!!.findViewById(R.id.tv_set_import_code) as TextView
                    var s= event.getService()!!.getMessage()!!.substring(10)
                    tv_set_import_code1.setText(s)
                    dialog11!!.show()
                    btn_cancel_import_code.setOnClickListener()
                    {
                        dialog11!!.cancel()
                    }
                    progreee_importcode1!!.visibility= View.GONE
                }
        }
        if(event.getTemp()==AllValue.insetCustomer)
        {//thêm khách hàng thành công thì đi tới hàm main và gửi các  biến liên quan
            Json.AppLoginPswd=password
            var inval5: Array<String> = arrayOf(phone!!)
            call!!.CallEmit(AllValue.workername_getID,AllValue.servicename_getID,inval5,AllValue.getId_Signin!!)

        }
        if(event.getTemp()==AllValue.getId_Signin)
        {
            var json3:IsNumber= readJson1(event!!.getService()!!.getData()!!)
            if(json3.getSecC0()!="N") {
                Json.AppLoginID = json3.getSecC0()!!
                var Shared_Preferences : String = "IDSYSTEM"//........ ten thu muc chua
                var sharedpreferences: SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
                var editor2 : SharedPreferences.Editor?= sharedpreferences.edit()
                editor2!!.putString("id_system",Encode().encryptString(json3.getSecC0().toString()))
                editor2.commit()
                sendToActivityMain(phone!!, password!!, AllValue.gotomain!!)
            }
        }
    }
    public override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    //Đọc file json
    fun readJson1(json: ArrayList<JSONObject>): IsNumber
    {
        var jsonO: JSONObject?=null
        if(json.size>0) {
            jsonO = json[0]
        }
        var c0: String? =jsonO!!.getString("c0")
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