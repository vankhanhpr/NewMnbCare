package com.example.vankhanhpr.vidu2.fragment_main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vankhanhpr.vidu2.R

/**
 * Created by VANKHANHPR on 7/9/2017.
 */

class Fragment_Mom:Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var k: View = inflater!!.inflate(R.layout.fragment_mom, container, false)
        return k
    }

}