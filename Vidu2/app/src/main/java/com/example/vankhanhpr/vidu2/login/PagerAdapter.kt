package com.example.vankhanhpr.tabhost

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.*

/**
 * Created by VANKHANHPR on 6/22/2017.
 */
public  class  PagerAdapter(fm: FragmentManager?, fragments: Vector<Fragment>) : FragmentPagerAdapter(fm)
{
    var fragments : List<Fragment>?=null

    init {
        this.fragments = fragments
    }

    override fun getItem(position: Int): Fragment
    {
        return fragments!!.get(position)
    }

    override fun getCount(): Int {
        return this.fragments!!.size
    }

}