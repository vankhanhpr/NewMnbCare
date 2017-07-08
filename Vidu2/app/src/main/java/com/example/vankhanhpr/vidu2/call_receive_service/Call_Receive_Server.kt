package com.example.vankhanhpr.vidu2.call_receive_service

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.JsonWriter
import android.util.Log
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.json.ALTMW_Protocol
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.json.Service_Response
import com.github.nkzawa.emitter.Emitter
import com.github.nkzawa.socketio.client.Socket
import com.github.nkzawa.socketio.client.IO;

import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.io.IOException
import java.io.StringWriter
import java.io.Writer

import java.net.URISyntaxException


/**
 * Created by VANKHANHPR on 7/1/2017.
 */

class Call_Receive_Server : AppCompatActivity()
{

    //Dùng mẫu thiết kế singleton cho phép gọi một lần
    companion object
    {
        private var mInstance: Call_Receive_Server? = null

        val instance: Call_Receive_Server
            get()
            {
                if (mInstance == null)
                {
                    mInstance = Call_Receive_Server()
                }
                return mInstance!!
            }
    }

    //khai báo các giá trị cần thiết
    /*var mSocket: Socket?= null*/
    var mSocket: Socket? = IO.socket(AllValue.address.toString())

    var output : StringWriter?=null
    var temp2: ALTMW_Protocol?= null
    var hmap : HashMap<Int,String>?= HashMap()
    var stnumber:Int?= 0

    //Hàm khởi tạo chính
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_select_phonenumber)

        try
        {



        }
        catch(e: URISyntaxException)
        {
            Log.d("Loi dia chi","Loi")
        }
       /* btn_login.setOnClickListener()
        {
            var inten= Intent(this, Login::class.java)
            startActivity(inten)
        }*/
    }
    fun Sevecie()
    {
        mSocket=IO.socket(AllValue.address.toString())
        mSocket!!.connect()
        mSocket!!.on("RES_MSG",onNewMessage)
    }
    // hàm gọi emit dùng chung
    fun CallEmit(workerName:String,serviceName:String,input:Array<String>,key:String)
    {
        //map 1 key String với 1 số Int
        instance.stnumber = instance.stnumber!! + 1
        hmap!!.put(stnumber!!,key)
        this.hmap=hmap

        //khai bái cái biến json
        output=null
        output= StringWriter()
        temp2= CallService(stnumber!!,workerName,serviceName,input)

        try
        {
            writeJsonStream(output!!,temp2!!)
            Log.d("khong loi dau",output.toString())
            mSocket!!.emit("REQ_MSG",output)

        }
        catch (e:Exception)
        {
            Log.d("Error",e.toString())
        }
    }

    //Sự kiện on về
     var onNewMessage  =
            object : Emitter.Listener {
                override fun call(vararg args: Any) {
                    var json: JSONObject = args[0] as JSONObject

                    runOnUiThread(Runnable
                    {
                        var x: Service_Response
                        Log.d("result","cmmmm"+json.toString())
                        x= readJson(json)

                        var message: MessageEvent = MessageEvent()
                        var tm:Int?= x.getClientSeq()
                        var ttt:String = instance.hmap!![tm!!].toString()
                        message.setTemp(ttt)
                        message.setService(x)
                        //truyền data đi
                        EventBus.getDefault().post(message)
                    })
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
        x.setOperation("Q")
        x.setOtp("")
        x.setAcntNo("888FIS1234")
        x.setSubNo("00")
        x.setBankCd("0000")
        x.setInVal(input)
        x.setTotInVal(input.size)
        x.setAppLoginID("082c121293")
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
        var s :String? ="{c0:N}"
        if(jsonOj!!.length>2) {
            s = jsonOj!!.substring(1, jsonOj!!.length - 1)
        }
        Log.d("aaaaa",s.toString())

        var obj = JSONObject(s) as JSONObject


        var Code :String? =json.getString("Code")
        var Message:String? =json.getString("Message")
        var Result :String? =json.getString("Result")

        var ser : Service_Response = Service_Response()

        ser.setTransId(TransId!!)
        ser.setClientSeq(ClientSeq!!)
        ser.setPacket(Packet!!)

        ser.setData(obj)

        ser.setCode(Code!!)
        ser.setMessage(Message!!)
        ser.setResult(Result!!)
        return ser
    }
}
