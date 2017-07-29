package com.example.vankhanhpr.vidu2.getter_setter

import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.Doctor

/**
 * Created by VANKHANHPR on 7/11/2017.
 */
object Json
{
    public  var ID_Main:String? = ""
    public var Operation:String?= "Q"
    public  var AppLoginID:String ="0000"
    var  AppLoginPswd:String? =""
   // var x:Call_Receive_Server= Call_Receive_Server()
    var bucking:Int?= 2
    var mom_baby:Int?= 0
    val error="[{\"c0\":\"N\"}]"
    var doctor:Doctor?=null
    var phone:String?=null
}