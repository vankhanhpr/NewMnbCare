package com.example.vankhanhpr.vidu2.fragment_main.fragment_bucking

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import kotlinx.android.synthetic.main.mom_bucking.*
import java.util.*
import com.example.vankhanhpr.vidu2.MainActivity
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.widget.TimePicker
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import com.example.vankhanhpr.vidu2.getter_setter.IsNumber
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.json.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

/**
 * Created by VANKHANHPR on 7/22/2017.
 */
class BuckingMom:AppCompatActivity()
{
    var call=Call_Receive_Server.getIns()
    var id_doctor:String=""
    var id_mom:String?=""
    var date_bucking:String?=""
    var time_bucking:String?=""
    var name_doctor:String?=""

    var progress_bucking:ProgressBar?=null
    var mCountDownTimer: CountDownTimer? = null
    var dialog_disconnect:Dialog?=null


    var tv_name_file:TextView?=null
    var tv_name_doctor:TextView?=null
    var tv_date_bucking:TextView?=null
    var tv_time_bucking:TextView?=null
    var dialog_no_bucking:Dialog?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mom_bucking)
        EventBus.getDefault().register(this)


         tv_name_file= findViewById(R.id.tv_name_file) as TextView
         tv_name_doctor= findViewById(R.id.tv_name_doctor) as TextView
         tv_date_bucking= findViewById(R.id.tv_date_bucking) as TextView
         tv_time_bucking= findViewById(R.id.tv_time_bucking) as TextView



        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)

        id_doctor= bundle.getString(AllValue.value)
        id_mom=bundle.getString(AllValue.value2)
        date_bucking= bundle.getString(AllValue.value3)
        time_bucking=bundle.getString(AllValue.value4)
        name_doctor=bundle.getString("name")
        progress_bucking=  findViewById(R.id.progress_bucking) as ProgressBar


        tv_name_file!!.setText(id_mom.toString())
        tv_name_doctor!!.setText(name_doctor)
        tv_date_bucking!!.setText(date_bucking)
        tv_time_bucking!!.setText(time_bucking)

        tab_expect_bucking.setOnClickListener()
        {
            dialog_no_bucking= Dialog(this)
            dialog_no_bucking!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_no_bucking!!.setContentView(R.layout.dialog_error)

            var inval: Array<String> = arrayOf(id_doctor!!,Json.AppLoginID!!,id_mom!!,date_bucking!!,"",time_bucking!!)
            call.CallEmit(AllValue.workername_put_bucket,AllValue.servicename_put_bucket,inval,AllValue.bucking!!)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getTemp() == AllValue.bucking) {
            if(event.getService()!!.getResult()=="1")
            {
                //đặt lịch thành công
            }
            else
            {//đặt lịch không thành công
                dialog_no_bucking!!.show()
                var text=dialog_no_bucking!!.findViewById(R.id.tv_error) as TextView
                var bnt_ok= dialog_no_bucking!!.findViewById(R.id.btn_cancel_error)
                text.setText(event.getService()!!.getMessage()!!)
                bnt_ok.setOnClickListener()
                {
                    dialog_no_bucking!!.cancel()
                }
            }
        }
    }

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


}