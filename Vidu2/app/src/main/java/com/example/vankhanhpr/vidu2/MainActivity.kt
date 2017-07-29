package com.example.vankhanhpr.vidu2

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.widget.LinearLayout
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.encode.Encode
import com.example.vankhanhpr.vidu2.fragment_main.*
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.IsNumber
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.json.Service_Response
import com.example.vankhanhpr.vidu2.login_baby.Login
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject


class MainActivity :AppCompatActivity() {

    var fragment_Medical:Fragment?=null
    var fragment_Mom: Fragment? = null
    var fragment_Baby: Fragment? = null
    var fragment_Group: Fragment? = null
    var fragment_Manager_Account: Fragment? = null


    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    var fragment_main:LinearLayout? = null
    var tab_medical:LinearLayout?=null
    var user_id:String?=null
    var pass:String?=null
    var id_system:String?=null
    var call= Call_Receive_Server.getIns()
    var dialog:Dialog?=null
    var editor : SharedPreferences.Editor?=null
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        var bottom_navigation_main1 =findViewById(R.id.bottom_navigation_main) as BottomNavigationView

        disableShiftMode(bottom_navigation_main1)

        //Lấy dữ liệu truyền về
        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)
        user_id= bundle.getString(AllValue.value)
        pass=bundle.getString(AllValue.value2)
        Json.AppLoginPswd=pass

        //Lấy mã khách hàng khi đăng nhập đăng kí thành công
        var Shared_Preferences2 : String = "IDSYSTEM"
        var sharedpreferences2 : SharedPreferences = getSharedPreferences(Shared_Preferences2, Context.MODE_PRIVATE)
        var id123= sharedpreferences2.getString("id_system","")
        id_system= Encode().decryptString(id123!!)
        Json.AppLoginID=id_system!!

        Log.d("MainActivity","Code : "+id_system.toString())
        //......lần đầu đăng nhập
        var Shared_Preferences : String = "landau"//........ ten thu muc chua
        var sharedpreferences : SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
        //Đăng nhập một lần lưu thông tin tài khoản và mật khẩu
        editor = sharedpreferences.edit()
        editor!!.putString("thefirst","1")//........... luu du lieu
        editor!!.putString("id",user_id!!)
        editor!!.putString("password",pass!!)
        editor!!.commit()

        fragment_main = findViewById(R.id.fragment_main) as LinearLayout
        //tab_medical=findViewById(R.id.tab_medical) as LinearLayout


        //add the fragment
        addFragment()

        bottom_navigation_main1.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                var fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.hide(fragment_Medical)
                fragmentTransaction.hide(fragment_Mom)
                fragmentTransaction.hide(fragment_Baby)
                fragmentTransaction.hide(fragment_Group)
                fragmentTransaction.hide(fragment_Manager_Account)

                    when (item.itemId)
                    {
                        R.id.mmenu1 ->
                        {
                            fragmentTransaction!!.show(fragment_Medical)
                            fragmentTransaction!!.hide(fragment_Mom)
                            fragmentTransaction!!.hide(fragment_Baby)
                            fragmentTransaction!!.hide(fragment_Group)
                            fragmentTransaction!!.hide(fragment_Manager_Account)
                        }
                    R.id.mmenu2 ->
                    {
                        fragmentTransaction!!.hide(fragment_Medical)
                        fragmentTransaction!!.hide(fragment_Mom)
                        fragmentTransaction!!.hide(fragment_Baby)
                        fragmentTransaction!!.hide(fragment_Group)
                        fragmentTransaction!!.hide(fragment_Manager_Account)
                        fragmentTransaction!!.show(fragment_Mom)
                    }
                    R.id.mmenu3 ->
                    {
                        fragmentTransaction!!.hide(fragment_Medical)
                        fragmentTransaction!!.hide(fragment_Mom)
                        fragmentTransaction!!.hide(fragment_Baby)
                        fragmentTransaction!!.hide(fragment_Group)
                        fragmentTransaction!!.hide(fragment_Manager_Account)
                        fragmentTransaction!!.show(fragment_Baby)
                    }
                    R.id.mmenu4 ->
                    {
                        fragmentTransaction!!.hide(fragment_Medical)
                        fragmentTransaction!!.hide(fragment_Mom)
                        fragmentTransaction!!.hide(fragment_Baby)
                        fragmentTransaction!!.hide(fragment_Group)
                        fragmentTransaction!!.hide(fragment_Manager_Account)
                        fragmentTransaction!!.show(fragment_Group)
                    }
                    R.id.mmenu5 ->
                    {
                        fragmentTransaction!!.hide(fragment_Medical)
                        fragmentTransaction!!.hide(fragment_Mom)
                        fragmentTransaction!!.hide(fragment_Baby)
                        fragmentTransaction!!.hide(fragment_Group)
                        fragmentTransaction!!.hide(fragment_Manager_Account)
                        fragmentTransaction!!.show(fragment_Manager_Account)
                    }
                }
                true
                fragmentTransaction!!.addToBackStack(null)
                fragmentTransaction!!.commit()
                return true
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        var serve: Service_Response = event.getService()!!
        if (event.getTemp() == AllValue.disconnect) {
            var  disconnect = findViewById(R.id.disconnect) as TextView
            disconnect.visibility= View.VISIBLE
        }
        if (event.getTemp() == AllValue.connect)
        {
            disconnect.visibility= View.GONE

            //......lần đầu đăng nhập
            var Shared_Preferences : String = "landau"//........ ten thu muc chua
            var sharedpreferences : SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)

            var inval: Array<String> = arrayOf(sharedpreferences.getString("id",""),sharedpreferences.getString( "password",""))
            call.CallEmit(AllValue.workername_checkpass!!.toString(), AllValue.servicename_checkpass!!.toString(), inval, AllValue.checkpass_disconnect!!.toString())
        }
        if(event.getTemp()==AllValue.checkpass_disconnect)
        {
           var x:IsNumber=readJson1(event.getService()!!.getData()!!)
            if(x.getSecC0()=="Y")
            {

            }
            if(x.getSecC0()=="N"){//truong hop thay da thay doi mat khau
                dialog= Dialog(this)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.setContentView(R.layout.dialog_error_password)
                var tv_error =dialog!!.findViewById(R.id.tv_error) as TextView
                var btn_cancel_error_pass= dialog!!.findViewById(R.id.btn_cancel_error_pass)
                dialog!!.show()
                tv_error.setText(resources.getString(R.string.change_account))
                //Logout
                //send_Logout()

               /* btn_cancel_error_pass.setOnClickListener()
                {
                    var inten= Intent(applicationContext,Login::class.java)
                    startActivity(inten)
                    finish()
                }*/
            }
        }
    }

    //back system
    override fun onBackPressed() {
        super.onBackPressed()
        val count = fragmentManager!!.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
            //additional code
        } else {
            fragmentManager!!.popBackStack()
        }
        finish()
    }

    //add fragments
    fun addFragment()
    {
        fragment_Medical= Fragment_Medical()
        fragment_Mom= Fragment_Mom()
        fragment_Baby = Fragment_Baby()
        fragment_Group= Fragment_Group()
        fragment_Manager_Account= Fragment_Account()

        fragmentManager =getSupportFragmentManager();
        fragmentTransaction=fragmentManager!!.beginTransaction()

        fragmentTransaction!!.add(R.id.fragment_main,fragment_Medical)
        fragmentTransaction!!.add(R.id.fragment_main,fragment_Mom)
        fragmentTransaction!!.add(R.id.fragment_main,fragment_Baby)
        fragmentTransaction!!.add(R.id.fragment_main,fragment_Group)
        fragmentTransaction!!.add(R.id.fragment_main,fragment_Manager_Account)


        fragmentTransaction!!.show(fragment_Medical)
        fragmentTransaction!!.hide(fragment_Mom)
        fragmentTransaction!!.hide(fragment_Baby)
        fragmentTransaction!!.hide(fragment_Group)
        fragmentTransaction!!.hide(fragment_Manager_Account)
        fragmentTransaction!!.commit()
    }

    //settting bottom nagivation
    @SuppressLint("RestrictedApi")
    fun disableShiftMode(view: BottomNavigationView) {
        var menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            var shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true

            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = true


            for (i in 0..menuView.childCount - 1) {
                var item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShiftingMode(false)
                // set once again checked value, so view will be updated
                item.setChecked(item.itemData.isChecked)
                item.setIcon(resources.getDrawable(R.drawable.icon_heart))
            }
        } catch (e: NoSuchFieldException) {
            //Timber.e(e, "Unable to get shift mode field");
        } catch (e: IllegalAccessException) {
            //Timber.e(e, "Unable to change value of shift mode");
        }
    }
    //Đọc json gửi về
    fun readJson1(json: ArrayList<JSONObject>): IsNumber
    {
        var jsonO: JSONObject?=null
        if(json.size>0) {
            jsonO = json[0]
        }
        var c0: String? =jsonO!!.getString("c0")
        var ser1 : IsNumber = IsNumber()
        ser1.setSecC0(c0!!)
        return ser1
    }
    /*fun send_Logout(){
        var Shared_Preferences : String = "Inf_Account"
        var sharedpreferences : SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
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
        Log.d("xoa_Inf_Acc",sharedpreferences.getString("C0","").toString())

        var Name_file : String = "landau"//........ ten thu muc chua
        var sharedpreferences2 : SharedPreferences = getSharedPreferences(Name_file, Context.MODE_PRIVATE)
        var editor2 : SharedPreferences.Editor? = sharedpreferences2.edit()
        editor2!!.putString("id","")
        editor2!!.putString("password","")
        editor2!!.commit()
        Log.d("xoa_landau",sharedpreferences2.getString("id",""))
        Json.AppLoginID = ""
        Json.AppLoginPswd = ""


        var Shared_Preferences4 : String = "IDSYSTEM"//........ ten thu muc chua
        var sharedpreferences4:SharedPreferences = getSharedPreferences(Shared_Preferences4, Context.MODE_PRIVATE)
        var editor4 : SharedPreferences.Editor?= sharedpreferences4.edit()
        editor4!!.putString("id_system", "")
        editor4.commit()
        //tvdiem_!!.text = sharedpreferences.getString("C1","").toString()
    }*/
}
