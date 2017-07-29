package com.example.vankhanhpr.vidu2.fragment_main.fragment_medical

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.dicimal_getset.Schedule
import com.google.gson.Gson
import kotlinx.android.synthetic.main.shedule_information_detail.*

/**
 * Created by VANKHANHPR on 7/25/2017.
 */
class  Schedule_Infor_Detail:AppCompatActivity()
{
    var schedule:String?=""
    var scheduleCl:Schedule?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shedule_information_detail)

        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)
        schedule= bundle.getString(AllValue.value)
        var gson =Gson()
        scheduleCl= gson.fromJson(schedule,Schedule::class.java)
        tv_c0.setText(scheduleCl!!.getC0())
        tv_c1.setText(scheduleCl!!.getC1())//
        tv_c2.setText(scheduleCl!!.getC2())
        tv_c3.setText(scheduleCl!!.getC4())
        tv_c4.setText(scheduleCl!!.getC7())
        tv_c5.setText(scheduleCl!!.getC8()!!.substring(0,2)+scheduleCl!!.getC8()!!.substring(2,4)+scheduleCl!!.getC8()!!.substring(4))
        tv_c6.setText(scheduleCl!!.getC9()!!.substring(0,2)+":"+scheduleCl!!.getC9()!!.substring(2))
        tv_c7.setText(scheduleCl!!.getC10()!!.substring(0,2)+":"+scheduleCl!!.getC10()!!.substring(2))

        tv_c8.setText("" + (if(scheduleCl!!.getC11()!!.toString() =="N") "Chờ khám"
            else
            if(scheduleCl!!.getC11()!!.toString() =="Y") "Hoàn tất"
            else if(scheduleCl!!.getC11()!!.toString()=="D") "Hủy đặt lịch người dùng"
            else "Hủy đặt lịch hệ thống" ))

        back_schedule_detail.setOnClickListener()
        {
            finish()
        }
    }
}