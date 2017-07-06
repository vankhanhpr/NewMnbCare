package com.example.vankhanhpr.vidu2.json

/**
 * Created by VANKHANHPR on 7/1/2017.
 */
class ALTMW_Protocol {

    private var ClientSeq : Int = 0            //Client seq
    private var SecCode: String? = null        //Sec code
    private var WorkerName: String? = null    //Worker name values
    private var ServiceName: String? = null    //Service name values
    private var TimeOut: Int = 0            //TimeOut values
    private var MWLoginID: String? = null        //MWLoginID values
    private var MWLoginPswd: String? = null    //MWLoginPswd values
    private var AppLoginID: String? = null    //AppLoginID values
    private var AppLoginPswd: String? = null    //AppLoginPswd values
    private var IPPrivate: String? = null    //IPPrivate values
    private var IPPublic: String? = null        //IPPublic values
    private var ClientSentTime: String? = null    //ClientSentTime values default = ‘0’
    private var Lang: String? = null            //Client language: V-Tiếng Việt, E-Tiếng Anh
    private var MdmTp: String? = null            //Media type: 03-Android, 04-IOS, 02-WEB, 01-Application Window
    private var AprStat: String? = null            //Approve status
    private var Operation: String? = null        //Q: Query, I: Insert, U: Update, D: Delete, E: Export, P: Print
    private var InVal: Array<String>? = null  //List of input values
    private  var TotInVal: Int = 0//Total input values
    private var Otp :String? =null
    private var AcntNo:String? =null
    private var SubNo:String?=null
    private var BankCd:String?=null

    fun getClientSeq(): Int {
        return ClientSeq
    }

    fun setClientSeq(clientSeq: Int) {
        ClientSeq = clientSeq
    }

    fun getSecCode(): String {
        return SecCode!!
    }

    fun setSecCode(secCode: String) {
        SecCode = secCode
    }

    fun getWorkerName(): String {
        return WorkerName!!
    }

    fun setWorkerName(workerName: String) {
        WorkerName = workerName
    }

    fun getServiceName(): String {
        return ServiceName!!
    }

    fun setServiceName(serviceName: String) {
        ServiceName = serviceName
    }

    fun getTimeOut(): Int {
        return TimeOut
    }

    fun setTimeOut(timeOut: Int) {
        TimeOut = timeOut
    }

    fun getMWLoginID(): String {
        return MWLoginID!!
    }

    fun setMWLoginID(MWLoginID: String) {
        this.MWLoginID = MWLoginID
    }

    fun getMWLoginPswd(): String {
        return MWLoginPswd!!
    }

    fun setMWLoginPswd(MWLoginPswd: String) {
        this.MWLoginPswd = MWLoginPswd
    }

    fun getAppLoginID(): String {
        return AppLoginID!!
    }

    fun setAppLoginID(appLoginID: String) {
        AppLoginID = appLoginID
    }

    fun getAppLoginPswd(): String {
        return AppLoginPswd!!
    }

    fun setAppLoginPswd(appLoginPswd: String) {
        AppLoginPswd = appLoginPswd
    }

    fun getIPPrivate(): String {
        return IPPrivate!!
    }

    fun setIPPrivate(IPPrivate: String) {
        this.IPPrivate = IPPrivate
    }

    fun getIPPublic(): String {
        return IPPublic!!
    }

    fun setIPPublic(IPPublic: String) {
        this.IPPublic = IPPublic
    }

    fun getClientSentTime(): String? {
        return ClientSentTime!!
    }

    fun setClientSentTime(clientSentTime: String) {
        ClientSentTime = clientSentTime
    }

    fun getLang(): String {
        return Lang!!
    }

    fun setLang(lang: String) {
        Lang = lang
    }

    fun getMdmTp(): String {
        return MdmTp!!
    }

    fun setMdmTp(mdmTp: String) {
        MdmTp = mdmTp
    }

    fun getAprStat(): String {
        return AprStat!!
    }

    fun setAprStat(aprStat: String) {
        AprStat = aprStat
    }

    fun getOperation(): String {
        return Operation!!
    }

    fun setOperation(operation: String) {
        Operation = operation
    }

    fun getInVal(): Array<String> {
        return InVal!!
    }

    fun setInVal(inVal: Array<String>) {
        InVal = inVal
    }

    fun getTotInVal(): Int {
        return TotInVal
    }

    fun setTotInVal(totInVal: Int) {
        TotInVal = totInVal
    }

    fun getOtp(): String? {
        return Otp
    }

    fun setOtp(otp: String) {
        Otp = otp
    }

    fun getAcntNo(): String? {
        return AcntNo
    }

    fun setAcntNo(acntNo: String) {
        AcntNo = acntNo
    }

    fun getSubNo(): String? {
        return SubNo
    }

    fun setSubNo(subNo: String) {
        SubNo = subNo
    }

    fun getBankCd(): String? {
        return BankCd
    }

    fun setBankCd(bankCd: String) {
        BankCd = bankCd
    }

}
