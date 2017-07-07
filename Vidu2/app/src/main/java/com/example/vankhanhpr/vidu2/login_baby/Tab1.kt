package com.example.vankhanhpr.vidu2.login_baby

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.vankhanhpr.vidu2.R
import kotlinx.android.synthetic.main.the_first1.view.*

/**
 * Created by VANKHANHPR on 6/30/2017.
 */
class Tab1 :Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var k:View = inflater!!.inflate(R.layout.the_first1, container, false)
        var animation:Animation= AnimationUtils.loadAnimation(getContext(),R.anim.sideup)
        k.imv_mnccare.startAnimation(animation)
        Log.d("Tab1","kandkasc")
        return k
    }
}