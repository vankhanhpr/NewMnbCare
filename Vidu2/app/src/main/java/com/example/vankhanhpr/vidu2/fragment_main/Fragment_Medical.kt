package com.example.vankhanhpr.vidu2.fragment_main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.RelativeLayout
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.adapter.adapter_diciamal.Adapter_Decima
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.change_password.ChangePass
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.getter_setter.dicimal_getset.Schedule
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.json.Service_Response
import kotlinx.android.synthetic.main.fragment_medical.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by VANKHANHPR on 7/9/2017.
 */

class Fragment_Medical: Fragment()
{
    var call =Call_Receive_Server.getIns()
    var lv_Medical:ListView?=null
    var list_Schedule: ArrayList<Schedule>? = ArrayList()
    var tab_error_medical:LinearLayout?=null
    var tab_lv_medical:LinearLayout?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var k: View = inflater!!.inflate(R.layout.fragment_medical, container, false)
        EventBus.getDefault().register(this)
        lv_Medical= k.findViewById(R.id.id_medical) as ListView
        tab_error_medical=k.findViewById(R.id.tab_error_medical) as LinearLayout
        tab_lv_medical=k.findViewById(R.id.tab_lv_medical)as LinearLayout


        tab_lv_medical!!.visibility=View.VISIBLE
        tab_error_medical!!.visibility=View.GONE
        var temp:Schedule= Schedule()
        temp.setTime("afsadf")
        temp.setDoctor_Name("Khanh Nguyen")
        temp.setMon_Baby_Name("Ba Ban")
        temp.setStatus("Bi chui hoai")


        list_Schedule!!.add(temp)
        list_Schedule!!.add(temp)
        var adapter:Adapter_Decima= Adapter_Decima(context,list_Schedule!!)
        lv_Medical!!.adapter= adapter

        var inval: Array<String> = arrayOf(Json.AppLoginID,"1")
        call.CallEmit(AllValue.workername_getschedule_cus,AllValue.servicename_getschedule_cus,inval,AllValue.get_schedule_custommer!!)

        return  k
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        var serve: Service_Response = event.getService()!!
        if(event.getTemp()==AllValue.get_schedule_custommer!!)
        {
           if(serve.getResult()=="0")
           {
               tab_error_medical!!.visibility=View.GONE
               tab_lv_medical!!.visibility=View.VISIBLE
           }
            if(serve.getResult()=="1")
            {
                tab_error_medical!!.visibility=View.GONE
                tab_lv_medical!!.visibility=View.VISIBLE
            }
        }
    }
    //change pass
    fun sendToChangePass(value: String,resultcode:Int) {

        var intent3 = Intent(context, ChangePass::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
    }
}