package com.example.vankhanhpr.vidu2.fragment_main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.change_password.ChangePass
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.json.Service_Response
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by VANKHANHPR on 7/9/2017.
 */

class Fragment_Baby:Fragment()
{
    //var call= Call_Receive_Server.instance
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var k: View = inflater!!.inflate(R.layout.fragment_baby, container, false)
        //call.Sevecie()
        EventBus.getDefault().register(this)
       /* var inval: Array<String> = arrayOf("khanh.nguyen")
        call.CallEmit(AllValue.workername_getID!!.toString(), AllValue.servicename_getID!!.toString(),inval ,"kkj")*/

        return k
    }

    override fun onStart()
    {
        super.onStart()


        //EventBus.getDefault().register(this)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        var serve: Service_Response = event.getService()!!
        if(event.getTemp()=="kkj")
        {
            Log.d("kkhkhk","kasdfjkasdjf")
        }
        Log.d("kkhkhk","kasdfjkasdjf")
    }


}
