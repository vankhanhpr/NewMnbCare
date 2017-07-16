package com.example.vankhanhpr.vidu2

import android.annotation.SuppressLint
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
import android.widget.TextView
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.fragment_main.*
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.json.Service_Response
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : AppCompatActivity() {

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
    var call= Call_Receive_Server.getIns()

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

        //......lần đầu đăng nhập
        var Shared_Preferences : String = "landau"//........ ten thu muc chua
        var sharedpreferences : SharedPreferences = getSharedPreferences(Shared_Preferences, Context.MODE_PRIVATE)
        //Đăng nhập một lần
        var editor : SharedPreferences.Editor? = sharedpreferences.edit()
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
                fragmentTransaction!!.addToBackStack(null).commit()
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
        if (event.getTemp() == AllValue.connect) {
            var  disconnect = findViewById(R.id.disconnect) as TextView
            disconnect.visibility= View.GONE
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
        val menuView = view.getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView.javaClass.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = true
            for (i in 0..menuView.childCount - 1) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShiftingMode(false)
                // set once again checked value, so view will be updated
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {
            //Timber.e(e, "Unable to get shift mode field");
        } catch (e: IllegalAccessException) {
            //Timber.e(e, "Unable to change value of shift mode");
        }
    }
}
