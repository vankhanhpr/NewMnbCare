package com.example.vankhanhpr.vidu2

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.support.design.internal.BottomNavigationItemView
import java.lang.reflect.AccessibleObject.setAccessible
import java.lang.reflect.Array.setBoolean
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.widget.LinearLayout
import android.widget.Toast
import com.example.vankhanhpr.vidu2.fragment_main.Fragment_Baby
import com.example.vankhanhpr.vidu2.fragment_main.Fragment_Group
import com.example.vankhanhpr.vidu2.fragment_main.Fragment_Manager_Acount
import com.example.vankhanhpr.vidu2.fragment_main.Fragment_Mom
import android.support.annotation.NonNull
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE


class MainActivity : AppCompatActivity() {

    private var fragment_Mom: Fragment? = null
    var fragment_Baby: Fragment? = null
    var fragment_Group: Fragment? = null
    var fragment_Manager_Account: Fragment? = null

    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    var fragment_main:LinearLayout? = null
    var tab_medical:LinearLayout?=null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var bottom_navigation_main1 =findViewById(R.id.bottom_navigation_main) as BottomNavigationView
        disableShiftMode(bottom_navigation_main1)

        fragment_main = findViewById(R.id.fragment_main) as LinearLayout
        tab_medical=findViewById(R.id.tab_medical) as LinearLayout

        //add the fragment
        addFragment()



        bottom_navigation_main1.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {


                var fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction.hide(fragment_Mom)
                fragmentTransaction.hide(fragment_Baby)
                fragmentTransaction.hide(fragment_Group)
                fragmentTransaction.hide(fragment_Manager_Account)

                when (item.itemId)
                {
                    R.id.mmenu1 ->
                    {
                        fragmentTransaction!!.hide(fragment_Mom)
                        fragmentTransaction!!.hide(fragment_Baby)
                        fragmentTransaction!!.hide(fragment_Group)
                        fragmentTransaction!!.hide(fragment_Manager_Account)
                        tab_medical!!.visibility = View.VISIBLE
                        fragment_main!!.visibility= GONE
                    }
                    R.id.mmenu2 ->
                    {
                        fragmentTransaction!!.hide(fragment_Mom)
                        fragmentTransaction!!.hide(fragment_Baby)
                        fragmentTransaction!!.hide(fragment_Group)
                        fragmentTransaction!!.hide(fragment_Manager_Account)
                        fragmentTransaction!!.show(fragment_Mom)
                        tab_medical!!.visibility = View.GONE
                        fragment_main!!.visibility= View.VISIBLE

                    }
                    R.id.mmenu3 ->
                    {
                        fragmentTransaction!!.hide(fragment_Mom)
                        fragmentTransaction!!.hide(fragment_Baby)
                        fragmentTransaction!!.hide(fragment_Group)
                        fragmentTransaction!!.hide(fragment_Manager_Account)
                        fragmentTransaction!!.show(fragment_Baby)
                        tab_medical!!.visibility = View.GONE
                        fragment_main!!.visibility= View.VISIBLE
                    }
                    R.id.mmenu4 ->
                    {
                        fragmentTransaction!!.hide(fragment_Mom)
                        fragmentTransaction!!.hide(fragment_Baby)
                        fragmentTransaction!!.hide(fragment_Group)
                        fragmentTransaction!!.hide(fragment_Manager_Account)
                        fragmentTransaction!!.show(fragment_Group)
                        tab_medical!!.visibility = View.GONE
                        fragment_main!!.visibility= View.VISIBLE
                    }
                    R.id.mmenu5 ->
                    {
                        fragmentTransaction!!.hide(fragment_Mom)
                        fragmentTransaction!!.hide(fragment_Baby)
                        fragmentTransaction!!.hide(fragment_Group)
                        fragmentTransaction!!.hide(fragment_Manager_Account)
                        fragmentTransaction!!.show(fragment_Manager_Account)
                        tab_medical!!.visibility = View.GONE
                        fragment_main!!.visibility= View.VISIBLE
                    }
                }
                true
                fragmentTransaction!!.addToBackStack(null).commit()
                /*fragmentTransaction!!.commit()*/
                return true
            }
        })
    }


    //add fragments
    fun addFragment()
    {
        fragment_Mom= Fragment_Mom()
        fragment_Baby = Fragment_Baby()
        fragment_Group= Fragment_Group()
        fragment_Manager_Account= Fragment_Manager_Acount()

        fragmentManager =getSupportFragmentManager();
        fragmentTransaction=fragmentManager!!.beginTransaction()

        fragmentTransaction!!.add(R.id.fragment_main,fragment_Mom)
        fragmentTransaction!!.add(R.id.fragment_main,fragment_Baby)
        fragmentTransaction!!.add(R.id.fragment_main,fragment_Group)
        fragmentTransaction!!.add(R.id.fragment_main,fragment_Manager_Account)


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
