package com.example.vankhanhpr.vidu2.getter_setter;

import java.util.ArrayList;

/**
 * Created by VANKHANHPR on 7/17/2017.
 */

public class Examble {

    String Date_Perform;
    int Ordinal_Perform;
    String Code_Hospital;
    String Code_Doctor;
    String Code_Customer;
    String ID_Mom_Baby;
    String Name_Mom_Baby;
    String Date_Set_Calendar;
    String Hours_Put_Checkup;
    String Hours_Checkup_Expected;
    String Status;
    String Status_Name;

    public String getDate_Perform() {
        return Date_Perform;
    }

    public void setDate_Perform(String date_Perform) {
        Date_Perform = date_Perform;
    }

    public int getOrdinal_Perform() {
        return Ordinal_Perform;
    }

    public void setOrdinal_Perform(int ordinal_Perform) {
        Ordinal_Perform = ordinal_Perform;
    }

    public String getCode_Hospital() {
        return Code_Hospital;
    }

    public void setCode_Hospital(String code_Hospital) {
        Code_Hospital = code_Hospital;
    }

    public String getCode_Doctor() {
        return Code_Doctor;
    }

    public void setCode_Doctor(String code_Doctor) {
        Code_Doctor = code_Doctor;
    }

    public String getCode_Customer() {
        return Code_Customer;
    }

    public void setCode_Customer(String code_Customer) {
        Code_Customer = code_Customer;
    }

    public String getID_Mom_Baby() {
        return ID_Mom_Baby;
    }

    public void setID_Mom_Baby(String ID_Mom_Baby) {
        this.ID_Mom_Baby = ID_Mom_Baby;
    }

    public String getName_Mom_Baby() {
        return Name_Mom_Baby;
    }

    public void setName_Mom_Baby(String name_Mom_Baby) {
        Name_Mom_Baby = name_Mom_Baby;
    }

    public String getDate_Set_Calendar() {
        return Date_Set_Calendar;
    }

    public void setDate_Set_Calendar(String date_Set_Calendar) {
        Date_Set_Calendar = date_Set_Calendar;
    }

    public String getHours_Put_Checkup() {
        return Hours_Put_Checkup;
    }

    public void setHours_Put_Checkup(String hours_Put_Checkup) {
        Hours_Put_Checkup = hours_Put_Checkup;
    }

    public String getHours_Checkup_Expected() {
        return Hours_Checkup_Expected;
    }

    public void setHours_Checkup_Expected(String hours_Checkup_Expected) {
        Hours_Checkup_Expected = hours_Checkup_Expected;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatus_Name() {
        return Status_Name;
    }

    public void setStatus_Name(String status_Name) {
        Status_Name = status_Name;
    }

    private ArrayList<String> Data;

    public ArrayList<String> getData() {
        return Data;
    }

    public void setData(ArrayList<String> data) {
        Data = data;
    }
}
