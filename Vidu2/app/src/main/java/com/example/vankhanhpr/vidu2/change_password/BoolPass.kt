package com.example.vankhanhpr.vidu2.change_password

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.json.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by VANKHANHPR on 7/12/2017.
 */
class BoolPass:AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    var call= Call_Receive_Server.instance
    fun checkPass(id:String,oldPass:String,newPass:String):Boolean
    {
        var inval: Array<String> = arrayOf(id,oldPass,newPass)
        call.CallEmit(AllValue.workername_change_pass,AllValue.servicename_change_pass,inval,AllValue.change_password!!)
        return  false
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getTemp() == AllValue.change_password)
        {
            if(event.getService()!!.getResult()=="1")
            {


            }
            else
            {


            }

        }
    }
}