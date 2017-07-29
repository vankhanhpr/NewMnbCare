package com.example.vankhanhpr.vidu2.fragment_main

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.view.menu.ShowableListMenu
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.change_password.ChangePass
import com.example.vankhanhpr.vidu2.encode.Encode
import com.example.vankhanhpr.vidu2.fragment_main.information_Account.Account_Inf
import com.example.vankhanhpr.vidu2.fragment_main.information_Account.Show_List_File_MonBaby
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.IsCustomer
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.login_baby.Login
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject

/**
 * Created by VANKHANHPR on 7/9/2017.
 */

class Fragment_Account :Fragment()
{
    var call = Call_Receive_Server.getIns()
    var tab_show_list_file :LinearLayout?=null
    var thongtinkh_main : IsCustomer? = null
    var tvdiem_ : TextView? = null
    var tvtenkh_ : TextView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var k: View = inflater!!.inflate(R.layout.fragment_manager_account, container, false)

        var tab_changepass1 = k.findViewById(R.id.tab_changepass)
        tab_show_list_file=k.findViewById(R.id.tab_show_list_file) as LinearLayout

        var inval: Array<String> = arrayOf(Json.AppLoginID)
        //call.CallEmit(AllValue.workername_get_customer, AllValue.workername_get_customer,inval, AllValue.get_info_customer!!)

        //............. kiểm tra thông tin khách hàng
        //.............................................đọc dữ liệu C0 -> C9 B4
        var Shared_Preferences : String = "Inf_Account"//........ ten thu muc chua
        var sharedpreferences : SharedPreferences = this.activity.getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
        sharedpreferences.getString("C0","")
        var c1_ = sharedpreferences.getString("C1","")
        sharedpreferences.getString("C2","")
        sharedpreferences.getString("C3","")
        sharedpreferences.getString("C4","")
        sharedpreferences.getString("C5","")
        sharedpreferences.getString("C6","")
        sharedpreferences.getString("C7","")
        var c8_ = sharedpreferences.getInt("C8",0)
        sharedpreferences.getInt("C9",0)
        //........ gán dữ liệu nhận đc vào text_view
        tvdiem_ = k.findViewById(R.id.tvdiem) as TextView
        tvdiem_!!.text = c8_.toString()
        tvtenkh_ = k.findViewById(R.id.tv_tenkh) as TextView
        tvtenkh_!!.text = c1_.toString()
        //change password
        tab_changepass1.setOnClickListener()
        {
            sendToChangePass("2305", AllValue.changePass!!)
        }

        tab_show_list_file!!.setOnClickListener()
        {
            var inttt = Intent(context,Show_List_File_MonBaby::class.java)
            startActivity(inttt)
        }
        //...................... chỉnh sửa thông tin khách hàng
        var cv_thongtinkh = k.findViewById(R.id.cv_thongtinkh) as LinearLayout
        cv_thongtinkh.setOnClickListener() {
            var intent = Intent(context, Account_Inf::class.java)
            startActivity(intent)
        }

        var tab_logout1 = k.findViewById(R.id.tab_logout)
        tab_logout1.setOnClickListener {
            //............ chuyển đến formLogin B1_logout
            //........................... bạn có muốn đăng xuất
            var dialog6: Dialog = Dialog(context)
            dialog6!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog6!!.setContentView(R.layout.dialog_logout)
            var tv_cancel = dialog6!!.findViewById(R.id.tv_cancel_logout) as TextView
            var tv_agree = dialog6!!.findViewById(R.id.tv_agree_logout) as TextView
            dialog6.show()
            tv_agree.setOnClickListener()
            {
                var intent = Intent(activity, Login::class.java)
                startActivity(intent)
                send_Logout()
                call.Disconnect()
                dialog6!!.cancel()
                activity.finish()//........................ finish activity main B3_logout
            }
            tv_cancel.setOnClickListener {
                dialog6.cancel()
            }
        }
        return k
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        //var serve: Service_Response = event.getService()!!
        if (event.getTemp() == AllValue.get_info_customer ) { //.......... nhận dữ liệu thông tin khách hàng b2
            var thongtin1 : IsCustomer? = null //......... nhận 10 giá trị
            thongtin1 = readJson2(event.getService()!!.getData()!!)
            thongtinkh_main = thongtin1
            //............................................ lưu dữ liệu vào biến SharePreference b3
            var Shared_Preferences : String = "Inf_Account"//........ ten thu muc chua
            var sharedpreferences : SharedPreferences = this.activity.getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
            var editor : SharedPreferences.Editor? = sharedpreferences.edit()

            editor!!.putString("C0", thongtinkh_main!!.getIs_cust_id().toString() )//........... luu du lieu
            editor!!.putString("C1",thongtinkh_main!!.getOs_cust_nm().toString())
            editor!!.putString("C2",thongtinkh_main!!.getOs_nick_nm().toString())
            editor!!.putString("C3",thongtinkh_main!!.getOs_mobi_phone().toString())
            editor!!.putString("C4",thongtinkh_main!!.getOs_sex_tp().toString())
            editor!!.putString("C5",thongtinkh_main!!.getOs_bir_dt().toString())
            editor!!.putString("C6",thongtinkh_main!!.getOs_addr().toString())
            editor!!.putString("C7",thongtinkh_main!!.getOs_email().toString())
            editor!!.putInt("C8",thongtinkh_main!!.getOs_star_eval()!!.toInt())
            editor!!.putInt("C9",thongtinkh_main!!.getOs_point_eval()!!.toInt())
            //Log.d("thaydoithongtinkh",sharedpreferences.getInt("C8",0).toString())
            editor!!.commit()
            tvdiem_!!.text = thongtinkh_main!!.getOs_star_eval()!!.toString()
            tvtenkh_!!.text = thongtinkh_main!!.getOs_cust_nm().toString()
        }
        if (event.getTemp() == AllValue.get_change_infor){//...... nhận kết quả trả từ thay đổi thông tin kh B3_thaydoitt
            Log.d("luuketqua",event.getService()!!.getResult().toString())
            //..................................... Đọc dữ liệu nhận đc SharePreferce B4_thaydoitt
            var Shared_Preferences : String = "Inf_Account"//........ ten thu muc chua
            var sharedpreferences : SharedPreferences = this.activity.getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
            var c1_ = sharedpreferences.getString("C1","")
            tvtenkh_!!.text = c1_.toString()        //..... test
        }
    }

    //............ logout
    fun send_Logout(){
        var Shared_Preferences : String = "Inf_Account"
        var sharedpreferences : SharedPreferences = this.activity.getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor? = sharedpreferences.edit()
        editor!!.putString("C0","")//........... trả về null
        editor!!.putString("C1","")
        editor!!.putString("C2","")
        editor!!.putString("C3","")
        editor!!.putString("C4","")
        editor!!.putString("C5","")
        editor!!.putString("C6","")
        editor!!.putString("C7","")
        editor!!.putInt("C8",0)
        editor!!.putInt("C9",0)
        editor!!.commit()

        var Name_file : String = "landau"//........ ten thu muc chua
        var sharedpreferences2 : SharedPreferences = this.activity.getSharedPreferences(Name_file, Context.MODE_PRIVATE)
        var editor2 : SharedPreferences.Editor? = sharedpreferences2.edit()
        editor2!!.putString("id","")
        editor2!!.putString("password","")
        editor2!!.commit()
        Log.d("xoa_landau",sharedpreferences2.getString("id",""))
        Json.AppLoginID = ""
        Json.AppLoginPswd = ""


        var Shared_Preferences4 : String = "IDSYSTEM"//........ ten thu muc chua
        var sharedpreferences4:SharedPreferences = this.activity.getSharedPreferences(Shared_Preferences4, Context.MODE_PRIVATE)
        var editor4 : SharedPreferences.Editor?= sharedpreferences4.edit()
        editor4!!.putString("id_system", "")
        editor4.commit()
        //tvdiem_!!.text = sharedpreferences.getString("C1","").toString()
    }
    //change pass
    fun sendToChangePass(value: String,resultcode:Int) {

        var intent3 = Intent(context, ChangePass::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
    }

    // Đọc file Json để lấy kết quả
    fun readJson2(json1:ArrayList<JSONObject>) : IsCustomer
    {
        var json = json1[0]
        var C0: String? =json.getString("c0")
        var C1: String? =json.getString("c1")
        var C2: String? =json.getString("c2")
        var C3: String? =json.getString("c3")
        var C4: String? =json.getString("c4")
        var C5: String? =json.getString("c5")
        var C6: String? =json.getString("c6")
        var C7: String? =json.getString("c7")
        var C8: Int? =json.getInt("c8")
        var C9: Int? =json.getInt("c9")

        var value : IsCustomer = IsCustomer()

        value.setIs_cust_id(C0!!)
        value.setOs_cust_nm(C1!!)
        value.setOs_nick_nm(C2!!)
        value.setOs_mobi_phone(C3!!)
        value.setOs_sex_tp(C4!!)
        value.setOs_bir_dt(C5!!)
        value.setOs_addr(C6!!)
        value.setOs_email(C7!!)
        value.setOs_star_eval(C8!!)
        value.setOs_point_eval(C9!!)

        return value
    }
}