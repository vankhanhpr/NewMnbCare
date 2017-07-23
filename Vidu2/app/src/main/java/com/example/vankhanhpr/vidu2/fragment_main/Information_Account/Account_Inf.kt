package com.example.vankhanhpr.vidu2.fragment_main.Information_Account

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.IsCustomer
import com.example.vankhanhpr.vidu2.getter_setter.Json
import kotlinx.android.synthetic.main.activity_account__inf.*
import org.greenrobot.eventbus.EventBus

class Account_Inf : AppCompatActivity() {
    var call = Call_Receive_Server.getIns()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account__inf)
        //EventBus.getDefault().register(this)
        //............................ ánh xạ id
        var c1_ = findViewById(R.id.et_hoten) as EditText
        var c2_ = findViewById(R.id.et_tenrieng) as EditText
        var c3_ = findViewById(R.id.et_sodt) as EditText
        var c4_ = findViewById(R.id.et_giotinh) as EditText
        var c5_ = findViewById(R.id.et_ngaysinh) as EditText
        var c6_ = findViewById(R.id.et_diachi) as EditText
        var c7_ = findViewById(R.id.et_email) as EditText



        btn_luuthongtin.setOnClickListener {
            //................ gửi dữ liệu thay đổi thông tin khách hàng B1_thaydoitt
            var inval: Array<String> = arrayOf(
                    Json.AppLoginID,c1_.text.toString(), c2_.text.toString(),
                    c3_.text.toString(),c4_.text.toString(),c5_.text.toString(),
                    c6_.text.toString(),c7_.text.toString() )
            Json.Operation = "U"
            call.CallEmit(AllValue.workername_change_account, AllValue.servicename_change_account!!,inval, AllValue.get_change_infor!!)
            Json.Operation = "Q"
            //Log.d("suaten2", c1_.text.toString())

            //................................. tên và số điện thoại khác null
            if(c1_.text.toString() != "" && c3_.text.toString() != "" && ( c3_.text.length == 10 || c3_.text.length == 11) ){
                //............................................ lưu thay đổi vào biến SharePreference B2_thaydoitt
                luugiatri(c1_.text.toString(),c2_.text.toString(),
                        c3_.text.toString(),c4_.text.toString(),
                        c5_.text.toString(),c6_.text.toString(),c7_.text.toString())
                Toast.makeText(this, "Lưu kết quả thành công", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Lưu kết quả không thành công", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun luugiatri( c1_: String, c2_: String, c3_: String, c4_: String, c5_: String, c6_: String, c7_: String ){
        var Shared_Preferences : String = "Inf_Account"//........ ten thu muc chua
        var sharedpreferences : SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor? = sharedpreferences.edit()

        editor!!.putString("C1",c1_)//........... luu du lieu
        editor!!.putString("C2",c2_)
        editor!!.putString("C3",c3_)
        editor!!.putString("C4",c4_)
        editor!!.putString("C5",c5_)
        editor!!.putString("C6",c6_)
        editor!!.putString("C7",c7_)
        //....... da luu thanh cong
        editor!!.commit()
    }

}