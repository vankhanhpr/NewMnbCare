package com.example.vankhanhpr.vidu2.getter_setter

/**
 * Created by VANKHANHPR on 7/6/2017.
 */


object  AllValue
{

    //Truyền và lấy giá trị trong bundle cái này không được sửa nah
    var value:String ="Resuilt"
    var value2:String="Resuilt2"
    var value3:String="Resuilt3"
    var value4:String="Resuilt4"
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
    var changePass:Int?=777
    var createFile:Int?=888 //tạo hồ sơ cho mẹ và bé
    var updateFile:Int?=999
    var sentToCreateFile:Int?=1122
    var sentToBucking:Int?=2233
    var senToSheduleDetail:Int?= 3344
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
    var workername_verification_code= "ALTqMNB"
    var servicename_verification_code="ALTqMNB_0101_1"
    //Resetpass work
    var workername_restartpass= "ALTxMNB"
    var servicename_restartpass="ALTxMNB_0101_1"
    //get ID with phone number
    var workername_getID="ALTqCommon"
    var servicename_getID="ALTqCommon_GetID"
    //change passwork before restart password
    var workername_change_pass="ALTxMNB"
    var servicename_change_pass="ALTxMNB_0101_2"

    //check password before get code
    var workername_checkpass_signin:String?= "ALTqMNB"
    var servicename_checkpas_sign:String?="ALTqMNB_0101_1"
    //signin success
    var workername_get_customer="ALTqMNB"
    var servicename_get_customer="ALTqMNB_0101_2"
    //get schedule customer
    var workername_getschedule_cus="ALTqMNB"
    var servicename_getschedule_cus="ALTqMNB_0103_1"
    //create file mom
    var workername_create_file_mom="ALTxMNB"
    var servicename_create_file_mom="ALTxMNB_0102_1"
    //put schedule bucket
    var  workername_put_bucket="ALTxMNB"
    var servicename_put_bucket="ALTxMNB_0103_1"
    //lấy danh sách bác sĩ gần vị trí hiện tại
    var workername_get_listdoctor="ALTqCommon01"
    var servicename_get_listdoctor="ALTqCommon01_0821_1"
    //Tra cứu hồ sơ
    var workername_search_file="ALTqMNB"
    var servicename_search_file="ALTqMNB_0102_1"
    //Chỉnh sữa hồ sơ
    var workername_update_file="ALTxMNB"
    var servicename_update_file="ALTxMNB_0102_1"
    //lấy thời gian khám dự kiến
    var workername_set_time_expected="ALTxMNB"
    var servicename_set_time_expected="ALTxMNB_0103_1"
    //thay đổi thông tin khách hàng
    //thay dổi thông tin khách hàng
    var workername_change_account="ALTxMNB"
    var servicename_change_account="ALTxMNB_0101_2"
    //Hủy lịch khám
    var workername_cancel_schedule="ALTxMNB"
    var servicename_cancel_schedule="ALTxMNB_0102_1"


    //nơi chứa các hàm gọi
    //kiểm tra số điện thoại
    var checknumber:String?= "isNumber"
    var checkpass:String?= "isPass"
    var checkpass1:String?= "isPass1"
    var checkpass_disconnect:String?="CheckPass_Disconnet"


    var checpass_getpass:String?="isPassGetPass"
    var signin_baby:String?="baby_Signin"
    var verification:String?="veri_Code"
    var verification2:String?="veri_Code2"

    var disconnect:String?="disconnectSystem"
    var connect:String?="connect"

    var getId:String?="getID"
    var getId_Main:String?="getID2"
    var getId_Login:String?="getID_Login"
    var getId_Signin:String?="getId_Signin"

    var restart_passwork:String?="restart_Pass"
    var change_password:String?="changePass"
    var checkid:String?= "isId"
    var check_Pass:String?="checkPass"
    var insetCustomer:String?="insertCustomer"
    var restartPassAgain:String?="restartPassAgain"
    var get_info_customer:String?="getCustomer"
    var get_schedule_custommer:String?="getSchedule_Cus"//lấy danh sách lịch khám của khách hàng
    var insert_file_mom_baby:String?="insert_file"//tao ho so cho me va be
    var get_list_doctor_mom:String?="getlist_doctor_mom"//lay danh sach bac si ben tab me
    var get_list_doctor_baby:String?="getlist_doctor_baby"//lay danh sach bac si ben tab be
    var seach_list_file:String?="seaerch_file_mom_baby"//lay danh sach ho so trong quan ly ho so
    var update_file_mombaby:String?="update_file_mom_baby"//update ho so me va be
    var getlist_file_mom:String?="get_list_file_mom"//lay danh sach me va be
    var get_time_expected:String?="get_time_expected"//lấy thời gian khám dự kiến cho mẹ
    var bucking:String?="bucking"//đặt lịch
    var get_change_infor:String?="getInfor"//thay dổi thông tin khách hàng
    var cancel_schedule:String?="cancel_schedule"
}
