package com.example.vankhanhpr.vidu2.login

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment

import android.support.v4.app.FragmentActivity
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.MenuItem

import com.example.vankhanhpr.tabhost.PagerAdapter
import com.example.vankhanhpr.vidu2.R
import kotlinx.android.synthetic.main.login_commercial.*
import java.util.*


/**
 * Created by VANKHANHPR on 6/30/2017.
 */
class Login_Commercial :FragmentActivity(),ViewPager.OnAdapterChangeListener, ViewPager.OnPageChangeListener {

    private var mViewPager:ViewPager?=null
    private var mPageAdapter:PagerAdapter?=null
    var  prevMenuItem:MenuItem?=null

    override fun onPageScrollStateChanged(state: Int) {
       var d=0
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        var e=0
    }

    override fun onPageSelected(position: Int)
    {
        if (prevMenuItem != null) {
            prevMenuItem!!.setChecked(false);
        }
        else
        {
            bottom_navigation.getMenu().getItem(0).setChecked(false);
        }
        Log.d("page", "onPageSelected: "+position);
        bottom_navigation.getMenu().getItem(position).setChecked(true);
        prevMenuItem = bottom_navigation.getMenu().getItem(position);

    }

    override fun onAdapterChanged(viewPager: ViewPager, oldAdapter: android.support.v4.view.PagerAdapter?, newAdapter: android.support.v4.view.PagerAdapter?) {
        var d=0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_commercial)

        bottom_navigation.setOnNavigationItemSelectedListener (
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.menu1 -> mViewPager!!.setCurrentItem(0)
                        R.id.menu2 -> mViewPager!!.setCurrentItem(1)
                        R.id.menu3 -> mViewPager!!.setCurrentItem(2)
                    }
                    true
                })
        intialiseViewPager()

    }

    private fun getItem(i: Int): Int {
        return mViewPager!!.getCurrentItem()
    }
    //Xử lý với view pager của tabhost
    private fun intialiseViewPager() {

        var fragments = Vector<Fragment>()
        fragments.add(Fragment.instantiate(this, Tab1::class.java!!.getName()))
        fragments.add(Fragment.instantiate(this, Tab2::class.java!!.getName()))
        fragments.add(Fragment.instantiate(this, Tab3::class.java!!.getName()))

        this.mPageAdapter = PagerAdapter(getSupportFragmentManager(),fragments)

        this.mViewPager = super.findViewById(R.id.viewpager) as ViewPager
        this.mViewPager!!.setAdapter(this.mPageAdapter)
        this.mViewPager!!.setOnPageChangeListener(this)
    }
}
