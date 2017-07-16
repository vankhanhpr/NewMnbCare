package com.example.vankhanhpr.vidu2.fragment_main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.change_password.ChangePass
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Json

/**
 * Created by VANKHANHPR on 7/9/2017.
 */

class Fragment_Account :Fragment()
{
    var call = Call_Receive_Server.getIns()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var k: View = inflater!!.inflate(R.layout.fragment_manager_account, container, false)

        var tab_changepass1 = k.findViewById(R.id.tab_changepass)

        var inval: Array<String> = arrayOf(Json.AppLoginID)
        call.CallEmit(AllValue.workername_get_customer, AllValue.workername_get_customer,inval, AllValue.get_info_customer!!)

        //change password
        tab_changepass1.setOnClickListener()
        {
            sendToChangePass("", AllValue.changePass!!)
        }


        return k
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