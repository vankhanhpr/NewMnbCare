package com.example.vankhanhpr.vidu2.json

import org.json.JSONObject
import java.util.ArrayList

/**
 * Created by VANKHANHPR on 7/2/2017.
 */

class Service_Response
{
    private var MdmTp: String? = null

    private var TransId: String? = null
    private var ClientSeq: Int? = 0
    private  var Packet: String? = null

    private var Code :String? =null
    private  var Message: String? = null
    private var Result :String? =null

    private var Data: ArrayList<JSONObject>? =  null

    fun getData(): ArrayList<JSONObject>?{
        return Data
    }

    fun setData(data: ArrayList<JSONObject>) {
        Data = data
    }

    fun getTransId(): String? {
        return TransId
    }

    fun setTransId(transId: String) {
        TransId = transId
    }

    fun getClientSeq():Int?{
        return ClientSeq
    }
    fun setClientSeq(clientSeq: Int) {
        ClientSeq = clientSeq
    }

    fun getPacket(): String? {
        return Packet
    }

    fun setPacket(packet: String) {
        Packet = packet
    }


    fun getCode(): String? {
        return Code
    }

    fun setCode(code: String) {
        Code = code
    }

    fun getMessage(): String? {
        return Message
    }

    fun setMessage(message: String) {
        Message = message
    }

    fun getResult(): String? {
        return Result
    }

    fun setResult(result: String) {
        Result = result
    }
}