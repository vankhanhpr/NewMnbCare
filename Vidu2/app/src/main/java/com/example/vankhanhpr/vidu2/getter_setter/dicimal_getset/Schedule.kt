package com.example.vankhanhpr.vidu2.getter_setter.dicimal_getset

/**
 * Created by VANKHANHPR on 7/18/2017.
 */
class Schedule
{
    private var Time: String?=null
    private var Doctor_Name: String?=null
    private var Mon_Baby_Name: String?=null
    private var Status: String?=null

    fun getTime(): String? {
        return Time
    }

    fun setTime(time: String) {
        Time = time
    }

    fun getDoctor_Name(): String? {
        return Doctor_Name
    }

    fun setDoctor_Name(doctor_Name: String) {
        Doctor_Name = doctor_Name
    }

    fun getMon_Baby_Name(): String? {
        return Mon_Baby_Name
    }

    fun setMon_Baby_Name(mon_Baby_Name: String) {
        Mon_Baby_Name = mon_Baby_Name
    }

    fun getStatus(): String? {
        return Status
    }

    fun setStatus(status: String) {
        Status = status
    }
}