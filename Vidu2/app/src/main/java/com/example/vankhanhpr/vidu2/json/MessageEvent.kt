package com.example.vankhanhpr.vidu2.json

/**
 * Created by VANKHANHPR on 7/3/2017.
 */

class  MessageEvent

{
    private var temp:String?=null

    private var service:Service_Response?= Service_Response()

    fun MessageEvent(temp: String,service: Service_Response)
    {
        this.temp   =temp
        this.service=service

    }

    fun getTemp(): String? {
        return temp
    }

    fun setTemp(temp: String) {
        this.temp = temp
    }
    fun getService(): Service_Response? {
        return service
    }

    fun setService(service: Service_Response) {
        this.service = service
    }

}