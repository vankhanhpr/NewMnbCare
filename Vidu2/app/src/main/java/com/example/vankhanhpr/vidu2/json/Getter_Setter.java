package com.example.vankhanhpr.vidu2.json;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by VANKHANHPR on 7/1/2017.
 */

public class Getter_Setter {

    //Client seq
    public String SecCode;     	//Sec code
    public String WorkerName;  	//Worker name values
    public String ServiceName; 	//Service name values
    public int TimeOut;			//TimeOut values
    public String MWLoginID;  		//MWLoginID values
    public String MWLoginPswd;  	//MWLoginPswd values
    public String AppLoginID;   	//AppLoginID values
    public String AppLoginPswd;   	//AppLoginPswd values
    public String IPPrivate;   	//IPPrivate values
    public String IPPublic;   		//IPPublic values
    public String ClientSentTime;    //ClientSentTime values default = ‘0’
    public String Lang;     		//Client language: V-Tiếng Việt, E-Tiếng Anh
    public String MdmTp;     		//Media type: 03-Android, 04-IOS, 02-WEB, 01-Application Window
    public String AprStat;    		//Approve status
    public String Operation;		//Q: Query, I: Insert, U: Update, D: Delete, E: Export, P: Print

    private String  Otp;
    private String AcntNo;
    private String SubNo;
    private String BankCd;

    String temp;

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String[] InVal;  //List of input values
    public int TotInVal;    //Total input values


    public String getSecCode() {
        return SecCode;
    }

    public void setSecCode(String secCode) {
        SecCode = secCode;
    }

    public String getWorkerName() {
        return WorkerName;
    }

    public void setWorkerName(String workerName) {
        WorkerName = workerName;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public int getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(int timeOut) {
        TimeOut = timeOut;
    }

    public String getMWLoginID() {
        return MWLoginID;
    }

    public void setMWLoginID(String MWLoginID) {
        this.MWLoginID = MWLoginID;
    }

    public String getMWLoginPswd() {
        return MWLoginPswd;
    }

    public void setMWLoginPswd(String MWLoginPswd) {
        this.MWLoginPswd = MWLoginPswd;
    }

    public String getAppLoginID() {
        return AppLoginID;
    }

    public void setAppLoginID(String appLoginID) {
        AppLoginID = appLoginID;
    }

    public String getAppLoginPswd() {
        return AppLoginPswd;
    }

    public void setAppLoginPswd(String appLoginPswd) {
        AppLoginPswd = appLoginPswd;
    }

    public String getIPPrivate() {
        return IPPrivate;
    }

    public void setIPPrivate(String IPPrivate) {
        this.IPPrivate = IPPrivate;
    }

    public String getIPPublic() {
        return IPPublic;
    }

    public void setIPPublic(String IPPublic) {
        this.IPPublic = IPPublic;
    }

    public String getClientSentTime() {
        return ClientSentTime;
    }

    public void setClientSentTime(String clientSentTime) {
        ClientSentTime = clientSentTime;
    }

    public String getLang() {
        return Lang;
    }

    public void setLang(String lang) {
        Lang = lang;
    }

    public String getMdmTp() {
        return MdmTp;
    }

    public void setMdmTp(String mdmTp) {
        MdmTp = mdmTp;
    }

    public String getAprStat() {
        return AprStat;
    }

    public void setAprStat(String aprStat) {
        AprStat = aprStat;
    }

    public String getOperation() {
        return Operation;
    }

    public void setOperation(String operation) {
        Operation = operation;
    }

    public String[] getInVal() {
        return InVal;
    }

    public void setInVal(String[] inVal) {
        InVal = inVal;
    }

    public int getTotInVal() {
        return TotInVal;
    }

    public void setTotInVal(int totInVal) {
        TotInVal = totInVal;
    }

    public String getOtp() {
        return Otp;
    }

    public void setOtp(String otp) {
        Otp = otp;
    }

    public String getAcntNo() {
        return AcntNo;
    }

    public void setAcntNo(String acntNo) {
        AcntNo = acntNo;
    }

    public String getSubNo() {
        return SubNo;
    }

    public void setSubNo(String subNo) {
        SubNo = subNo;
    }

    public String getBankCd() {
        return BankCd;
    }

    public void setBankCd(String bankCd) {
        BankCd = bankCd;
    }



    private  String TransId;
    private  String ClientSeq;
    private  String Packet;
    private  String[] Data;
    private  String Code;
    private  String  Message;
    private String Result;

    public String getTransId() {
        return TransId;
    }

    public void setTransId(String transId) {
        TransId = transId;
    }

    public void setClientSeq(String clientSeq) {
        ClientSeq = clientSeq;
    }

    public String getPacket() {
        return Packet;
    }

    public void setPacket(String packet) {
        Packet = packet;
    }

    public String[] getData() {
        return Data;
    }

    public void setData(String[] data) {
        Data = data;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }
}
