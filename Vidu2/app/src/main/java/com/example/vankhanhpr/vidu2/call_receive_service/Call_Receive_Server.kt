package com.example.vankhanhpr.vidu2.call_receive_service

import android.content.Context
import android.content.SharedPreferences
import android.util.JsonWriter
import android.util.Log
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Hasmap
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.json.ALTMW_Protocol
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.json.Service_Response
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.github.nkzawa.socketio.client.IO;

import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.io.StringWriter
import java.io.Writer

import kotlin.concurrent.thread


/**
 * Created by VANKHANHPR on 7/1/2017.
 */

class Call_Receive_Server private constructor()
{
    //Dùng mẫu thiết kế singleton cho phép gọi một lần
    //private object Holder { val INSTANCE = Call_Receive_Server() }
    companion object {
        private var instance : Call_Receive_Server? = null
        fun getIns(): Call_Receive_Server {

            if (instance == null) {
                Log.d("Call_Receive_Server", "getIns")
                instance = Call_Receive_Server()
            }
            return instance!!
        }
    }
    //khai báo các giá trị cần thiết
    /*var mSocket: Socket?= null*/
    var mSocket: Socket? = IO.socket(AllValue.address.toString())

    var output : StringWriter?=null
    var temp2: ALTMW_Protocol?= null
    var hmap : HashMap<Int,String>?= HashMap()

    var stnumber:Int?= 0
    var hasmap:ArrayList<Hasmap>?= ArrayList()
    var f:Boolean=true

    fun Sevecie()
    {
        Log.d("connect","Connect")
        mSocket=IO.socket(AllValue.address.toString())
        mSocket!!.connect()
    }
    fun ListenEvent()
    {
        mSocket!!.on("RES_MSG",onNewMessage)
        mSocket!!.on("error",systemError)

        mSocket!!.on("connect",onConnect)
        mSocket!!.on("disconnect",onDisconnect)
    }
    //Sự kiện on về
    var onNewMessage  =
            object : Emitter.Listener {
                override fun call(vararg args: Any) {
                    var json: JSONObject = args[0] as JSONObject
                    thread{
                        var x: Service_Response
                        Log.d("Call_Receive_Server","onNewMessage result: "+json.toString())
                        x= readJson(json)
                        var message: MessageEvent = MessageEvent()
                        var tm:Int?= x.getClientSeq()
                        var ttt:String?=null

                        for (i in 0..getIns().hasmap!!.size-1)
                        {
                            if(getIns().hasmap!![i].getKeySystem()== tm.toString())
                            {
                                Log.d("hass",hasmap!!.size!!.toString()+" "+ getIns().hasmap!![i].getKeyString()+x.getMessage())
                                ttt= getIns().hasmap!![i].getKeyString()
                                getIns().hasmap!![i].setStatus(0)
                            }
                        }
                        message.setTemp(ttt!!)
                        message.setService(x)
                        //truyền data đi
                        EventBus.getDefault().post(message)
                    }
                }
            }
    var onConnect  =
            object : Emitter.Listener {
                override fun call(vararg args: Any) {
                    thread{
                        f=true
                        Log.d("onConnect","onConnect")
                        var connect: MessageEvent = MessageEvent()
                        connect.setTemp(AllValue.connect!!)
                        //truyền data đi
                        EventBus.getDefault().post(connect)
                    }
                }
            }
    //connect fail
    var systemError  =
            object : Emitter.Listener {
                override fun call(vararg args: Any) {
                    // var json: JSONObject = args[0] as JSONObject
                    thread {
                        Log.d("SystemError","SystemError")
                        Call_Receive_Server.getIns()!!.Sevecie()
                    }
                }
            }
    //disconnect
    var onDisconnect  =
            object : Emitter.Listener {
                override fun call(vararg args: Any) {
                    // var json: JSONObject = args[0] as JSONObject
                    thread {
                        Log.d("Disconnect","Disconnect")
                        var error: MessageEvent = MessageEvent()
                        error.setTemp(AllValue.disconnect!!)
                        EventBus.getDefault().post(error)
                        if(getIns().hasmap!!.size > 0) {
                            for (i in 0..getIns().hasmap!!.size-1)
                            {
                                hasmap!![i].setStatus(0)
                            }
                        }
                    }
                }
            }
    // hàm gọi emit dùng chung
    fun CallEmit(workerName:String,serviceName:String,input:Array<String>,key:String)
    {
        if(getIns().hasmap!!.size > 0)
        {
            for (i in 0..getIns().hasmap!!.size-1)
            {
                if(getIns().hasmap!![i].getKeyString()==key && getIns().hasmap!![i].getStatus()==1)
                {
                    Log.d("hass","Da gui mot lan vui long cho")
                   return
                }
            }
        }
        //map 1 key String với 1 số Int
        getIns().stnumber = getIns().stnumber!! + 1
       // hmap!!.put(stnumber!!,key)
        //this.hmap=hmap
        var hasmap= getIns().hasmap

        var tem:Hasmap= Hasmap()


        tem.setKeySystem(getIns().stnumber!!.toString())
        tem.setKeyString(key)
        tem.setStatus(1)
        Log.d("Call_Receive_Server",tem.getKeyString()+tem.getKeySystem()+tem.getStatus())
        hasmap!!.add(tem)
        /*hasmap!![stnumber!!].setKeySystem(stnumber!!.toString())
        hasmap!![stnumber!!].setKeyString(key)
        hasmap!![stnumber!!].setStatus(1)*/

        //khai bái cái biến json
        output=null
        output= StringWriter()
        temp2= CallService(stnumber!!,workerName,serviceName,input)

        try
        {
            writeJsonStream(output!!,temp2!!)
            mSocket!!.emit("REQ_MSG",output)
            Log.d("Call_Receive_Server",output.toString())
        }
        catch (e:Exception)
        {
            Log.d("Error",e.toString())
        }
    }

    //Add data vào trong class
    fun CallService(clientSeq:Int, workerName:String,serviceName:String,input:Array<String>): ALTMW_Protocol
    {
        var x: ALTMW_Protocol = ALTMW_Protocol()

        x.setClientSeq(clientSeq)
        x.setWorkerName(workerName)
        x.setServiceName(serviceName)
        x.setTimeOut(15)
        x.setClientSentTime("0")
        x.setLang("V")
        x.setMdmTp("02")
        x.setAprStat("N")
        x.setOperation(Json.Operation!!)
        x.setOtp("")
        x.setAcntNo("888FIS1234")
        x.setSubNo("00")
        x.setBankCd("0000")
        x.setInVal(input)
        x.setTotInVal(input.size)
        x.setAppLoginID(Json.AppLoginID)
        x.setAppLoginPswd(Json.AppLoginPswd!!)
        x.setIPPrivate("192.168.0.113")
        return x
    }
    //đọc file class thành file Json
    @Throws(IOException::class)
    public fun writeJsonStream(output1: Writer, json: ALTMW_Protocol)
    {

        var jsonWriter = JsonWriter(output1)

        jsonWriter.beginObject()// begin root

        jsonWriter.name("ClientSeq").value(json.getClientSeq())
        jsonWriter.name("WorkerName").value(json.getWorkerName())
        jsonWriter.name("ServiceName").value(json.getServiceName())
        jsonWriter.name("TimeOut").value(json.getTimeOut())
        jsonWriter.name("ClientSentTime").value(json.getClientSentTime())
        jsonWriter.name("Lang").value(json.getLang())
        jsonWriter.name("MdmTp").value(json.getMdmTp())
        jsonWriter.name("AprStat").value(json.getAprStat())
        jsonWriter.name("Operation").value(json.getOperation())
        jsonWriter.name("Otp").value(json.getOtp())
        jsonWriter.name("AcntNo").value(json.getAcntNo())
        jsonWriter.name("SubNo").value(json.getSubNo())
        jsonWriter.name("BankCd").value(json.getBankCd())


        var inval = json.getInVal()
        jsonWriter.name("InVal").beginArray()
        for (iv in inval) {
            jsonWriter.value(iv)
        }

        jsonWriter.endArray()

        jsonWriter.name("TotInVal").value(json.getTotInVal())
        jsonWriter.name("AppLoginID").value(json.getAppLoginID())
        jsonWriter.name("AppLoginPswd").value(json.getAppLoginPswd())
        jsonWriter.name("IPPrivate").value(json.getIPPrivate())

        jsonWriter.endObject()// end address
    }
    //doc file Json
    fun readJson(json: JSONObject): Service_Response
    {
        var TransId: String? =json.getString("TransId")
        var ClientSeq:Int? =json.getInt("ClientSeq")
        var Packet: String? =json.getString("Packet")

        //jsonArray
        var jsonOj:String?= json.getString("Data")
        var s:String ="{c0:N}"
        var js2 = JSONObject(s)
        var list2:ArrayList<JSONObject> = ArrayList()
        list2.add(js2)
        if(jsonOj!="") {
            var js2 = JSONArray(jsonOj)
            list2.clear()
            for (i in 0..js2!!.length() - 1)
            {
                list2.add(js2.getJSONObject(i))
            }
        }
        var Code :String? =json.getString("Code")
        var Message:String? =json.getString("Message")
        var Result :String? =json.getString("Result")

        var ser : Service_Response = Service_Response()

        ser.setTransId(TransId!!)
        ser.setClientSeq(ClientSeq!!)
        ser.setPacket(Packet!!)
        ser.setData(list2)
        ser.setCode(Code!!)
        ser.setMessage(Message!!)
        ser.setResult(Result!!)
        return ser
    }
}
