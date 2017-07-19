package com.example.vankhanhpr.vidu2.getter_setter.dicimal_getset

/**
 * Created by VANKHANHPR on 7/18/2017.
 */
class Information_Schedule
{
    private var Date_Perform: String = ""
    private var Ordinal_Perform: Int = 0
    private var Code_Hospital: String = ""
    private var Code_Doctor: String = ""
    private var Code_Customer: String = ""
    private var ID_Mom_Baby: String = ""
    private var Name_Mom_Baby: String = ""
    private var Date_Set_Calendar: String = ""
    private var Hours_Put_Checkup: String = ""
    private var Hours_Checkup_Expected: String = ""
    private var Status: String = ""
    private var Status_Name: String = ""

    fun getDate_Perform(): String {
        return Date_Perform
    }

    fun setDate_Perform(date_Perform: String) {
        Date_Perform = date_Perform
    }

    fun getOrdinal_Perform(): Int {
        return Ordinal_Perform
    }

    fun setOrdinal_Perform(ordinal_Perform: Int) {
        Ordinal_Perform = ordinal_Perform
    }

    fun getCode_Hospital(): String {
        return Code_Hospital
    }

    fun setCode_Hospital(code_Hospital: String) {
        Code_Hospital = code_Hospital
    }

    fun getCode_Doctor(): String {
        return Code_Doctor
    }

    fun setCode_Doctor(code_Doctor: String) {
        Code_Doctor = code_Doctor
    }

    fun getCode_Customer(): String {
        return Code_Customer
    }

    fun setCode_Customer(code_Customer: String) {
        Code_Customer = code_Customer
    }

    fun getID_Mom_Baby(): String {
        return ID_Mom_Baby
    }

    fun setID_Mom_Baby(ID_Mom_Baby: String) {
        this.ID_Mom_Baby = ID_Mom_Baby
    }

    fun getName_Mom_Baby(): String {
        return Name_Mom_Baby
    }

    fun setName_Mom_Baby(name_Mom_Baby: String) {
        Name_Mom_Baby = name_Mom_Baby
    }

    fun getDate_Set_Calendar(): String {
        return Date_Set_Calendar
    }

    fun setDate_Set_Calendar(date_Set_Calendar: String) {
        Date_Set_Calendar = date_Set_Calendar
    }

    fun getHours_Put_Checkup(): String {
        return Hours_Put_Checkup
    }

    fun setHours_Put_Checkup(hours_Put_Checkup: String) {
        Hours_Put_Checkup = hours_Put_Checkup
    }

    fun getHours_Checkup_Expected(): String {
        return Hours_Checkup_Expected
    }

    fun setHours_Checkup_Expected(hours_Checkup_Expected: String) {
        Hours_Checkup_Expected = hours_Checkup_Expected
    }

    fun getStatus(): String {
        return Status
    }

    fun setStatus(status: String) {
        Status = status
    }

    fun getStatus_Name(): String {
        return Status_Name
    }

    fun setStatus_Name(status_Name: String) {
        Status_Name = status_Name
    }
}