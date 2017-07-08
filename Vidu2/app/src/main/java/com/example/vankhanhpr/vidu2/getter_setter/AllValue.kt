package com.example.vankhanhpr.vidu2.getter_setter

/**
 * Created by VANKHANHPR on 7/6/2017.
 */


object  AllValue
{

    //Truyền và lấy giá trị trong bundle cái này không được sửa nah
    var value:String ="Resuilt"
    var value2:String="Resuilt2"
    var key_bundle:String?="Document"

    //hết----------------

    //Nơi chứa các tham số truyền và nhận của bundle
    var address :String?= "http://124.158.14.36:8080"
    var login:Int?=111
    var signin:Int?=222
    var gotomain:Int?=333
    var gotomain_signin:Int?=444
    var gotomain_changepass:Int?=555
    var login_doctor:Int?=666

    //hết ---------------------

    //wokername and servicename check number
    var isBaby:String?="PHONE_CHK"
    var isDoctor:String?="ID_CHK"
    var workername_checknumber:String?= "ALTqCommon"
    var servicename_checknumber:String?="ALTqCommon_CheckExists"
    //worker and servicename check pass work when system login
    var workername_checkpass:String?="ALTxCommon"
    var servicename_checkpass:String?="ALTxCommon_Login"
    //Sign in with mother and baby
    var workername_signinbay:String?=""
    var servicename_signinbaby:String=""
    //send code sign in
    var workername_sendcode= "ALTxMNB"
    var servicename_sendcode="ALTxMNB_0101_1"
    //verification code so go to main before singin
    var workername_verification_code= "ALT1MNB"
    var servicename_verification_code="ALTqMNB_0101_1"
    //Resetpass work
    var workername_restartpass= "ALTxMNB"
    var servicename_restartpass="ALTxMNB_0101_2"
    //get ID with phone number
    var workername_getID="ALTqCommon"
    var servicename_getID="ALTqCommon_GetID"
    //change passwork before restart password
    var workername_change_pass="ALTxMNB"
    var servicename_change_pass="ALTxMNB_0101_2"





    //nơi chứa các hàm gọi
    //kiểm tra số điện thoại
    var checknumber:String?= "isNumber"
    var checkpass:String?= "isPass"
    var checpass_getpass:String?="isPassGetPass"
    var signin_baby:String?="baby_Signin"
    var verification:String?="veri_Code"
    var getId:String?="getID"
    var restart_passwork:String?="restart_Pass"
    var change_password:String?="changePass"
    var checkid:String?= "isId"

}
