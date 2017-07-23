package com.example.vankhanhpr.vidu2.fragment_main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.adapter.adapter_diciamal.Adapter_List_Doctor
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.change_password.ChangePass
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.Doctor
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.json.Service_Response
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 7/9/2017.
 */

class Fragment_Baby:Fragment()
{
    var call= Call_Receive_Server.getIns()
    var tab_no_data_listdoctor_baby:LinearLayout?=null
    var tab_list_doctor_baby:LinearLayout?=null
    var listDoctor:ArrayList<Doctor>?=null
    var lv_baby_chedule_bucked:ListView?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var k: View = inflater!!.inflate(R.layout.fragment_baby, container, false)
        tab_no_data_listdoctor_baby= k.findViewById(R.id.tab_no_data_listdoctor_baby) as LinearLayout
        tab_list_doctor_baby=k.findViewById(R.id.tab_list_doctor_baby) as LinearLayout
        lv_baby_chedule_bucked=k.findViewById(R.id.lv_baby_chedule_bucked) as ListView
        EventBus.getDefault().register(this)

        listDoctor= ArrayList()


        var inval: Array<String> = arrayOf("1","10.768830", "106.697720","2000")
        call.CallEmit(AllValue.workername_get_listdoctor,AllValue.servicename_get_listdoctor,inval,AllValue.get_list_doctor_baby!!)
        return k
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getTemp()==AllValue.get_list_doctor_baby && event.getService()!!.getData()!!.toArray()!=null)
        {
            tab_no_data_listdoctor_baby!!.visibility= View.GONE
            tab_list_doctor_baby!!.visibility= View.VISIBLE
            var list:ArrayList<JSONObject> =event.getService()!!.getData()!!
            for(i in 0..list.size-1)
            {
                val gson = Gson()
                var tm: Doctor = gson.fromJson(list[i].toString(),Doctor::class.java)
                listDoctor!!.add(tm)
            }
            var adapter= Adapter_List_Doctor(context,listDoctor!!)
            lv_baby_chedule_bucked!!.adapter= adapter

        }
    }


}
