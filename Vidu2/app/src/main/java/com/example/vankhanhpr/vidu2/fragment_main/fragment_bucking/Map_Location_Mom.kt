package com.example.vankhanhpr.vidu2.fragment_main.fragment_bucking

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby.Create_File_Mon
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.Doctor
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.File_Mom_Baby
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.login_baby.CheckPassSignIn
import com.google.gson.Gson
import kotlinx.android.synthetic.main.map_location_doctor.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.io.File

/**
 * Created by VANKHANHPR on 7/22/2017.
 */

class Map_Location_Mom :AppCompatActivity() {
    var call = Call_Receive_Server.getIns()
    var tab_bucking: LinearLayout? = null
    var listFile:ArrayList<File_Mom_Baby>?=null
    var lisJson:ArrayList<JSONObject>?=null
    var dialog5:Dialog?=null
    var dialog_bucking:Dialog?= null
    var file:File_Mom_Baby?=null
    var doctorS:String?=null
    var doctor:Doctor?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_location_doctor)
        EventBus.getDefault().register(this)

        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)
        doctorS= bundle.getString(AllValue.value)
        val gson = Gson()
        doctor = gson.fromJson(doctorS,Doctor::class.java)

        Log.d("doctor",doctor!!.getC1())
        listFile= ArrayList()
        lisJson= ArrayList()
        tab_bucking = findViewById(R.id.tab_bucking) as LinearLayout
        tab_bucking!!.setOnClickListener()
        {
            dialog5= Dialog(this)
            dialog_bucking= Dialog(this)
            dialog5!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog5!!.setContentView(R.layout.dialog_bucking)
            dialog_bucking!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_bucking!!.setContentView(R.layout.dialog_success_bucking)

            getListFile()
        }
        back_map_location.setOnClickListener()
        {
            finish()
        }
    }

    fun getListFile() {
        var inval: Array<String> = arrayOf(Json.AppLoginID)
        call.CallEmit(AllValue.workername_search_file, AllValue.servicename_search_file, inval, AllValue.getlist_file_mom!!)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getTemp() == AllValue.getlist_file_mom && event.getService()!!.getData().toString() != null) {
            lisJson= event.getService()!!.getData()
            Log.d("listFIle",lisJson.toString())
            for(i in 0..lisJson!!.size-1)
            {
                var gson = Gson()
                var tm: File_Mom_Baby = gson.fromJson(lisJson!![i].toString(),File_Mom_Baby::class.java)
                listFile!!.add(tm)
            }
            var s= boolMom(listFile!!)
            if(s=="")
            {
                dialog5!!.show()
                var btn_ok= dialog5!!.findViewById(R.id.tv_agree)
                var btn_cancel = dialog5!!.findViewById(R.id.tv_cancel)

                btn_ok.setOnClickListener()
                {
                    Json.bucking=false
                    sendToActivityCreateFile(Json.AppLoginID,AllValue.sentToCreateFile!!)
                }
                btn_cancel.setOnClickListener()
                {
                    dialog5!!.cancel()
                }
            }
            else
            {
                dialog_bucking!!.show()
                var bnt_gtobucking= dialog_bucking!!.findViewById(R.id.btn_success)
                var tv_notication= dialog_bucking!!.findViewById(R.id.tv_noti) as TextView
                tv_notication!!.setText("Tiến hành đặt hồ sơ cho :" + file!!.getC3())
                bnt_gtobucking!!.setOnClickListener()
                {
                    sendToActivityBucking(doctor!!.getC0()!!,s,AllValue.sentToBucking!!)
                }
            }
        }
        else {
        }

    }
    fun  boolMom(list:ArrayList<File_Mom_Baby>):String
    {
        var f:String=""
        for(i in 0..list!!.size-1)
        {
            if(list!![i].getC1()=="Vợ" || list!![i].getC1()=="Tôi")
            {
                file=list!![i]
                return  list!![i].getC2().toString()
            }
        }
        return f
    }
    //hàm chuyển qua Sign In
    fun sendToActivityCreateFile(value: String,resultcode:Int) {
        var intent3 = Intent(applicationContext, Create_File_Mon::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
    fun sendToActivityBucking(value: String,value2:String,resultcode:Int) {
        var intent3 = Intent(applicationContext, BuckingMom::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        bundle.putString(AllValue.value2, value2)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }

}

