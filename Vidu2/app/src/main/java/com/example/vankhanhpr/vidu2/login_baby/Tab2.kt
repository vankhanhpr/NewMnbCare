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

/**
 * Created by VANKHANHPR on 6/30/2017.
 */

class Tab2 : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var temp2:View= inflater!!.inflate(R.layout.the_first2, container, false)
        var animation: Animation = AnimationUtils.loadAnimation(getContext(),R.anim.sideup)
        /*temp2.imv_mnccare2.startAnimation(animation)*/
        Log.d("Tab2","Tab2")
        return temp2
    }
}