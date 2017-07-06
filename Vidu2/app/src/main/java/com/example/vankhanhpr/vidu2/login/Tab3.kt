package com.example.vankhanhpr.vidu2.login

import android.content.Intent
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
import kotlinx.android.synthetic.main.the_first3.view.*

/**
 * Created by VANKHANHPR on 6/30/2017.
 */
class Tab3 :Fragment()
{
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var x:View = inflater!!.inflate(R.layout.the_first3, container, false)
        var animation: Animation = AnimationUtils.loadAnimation(getContext(),R.anim.sideup)
        x.imv_mnccare3.startAnimation(animation)
        x.tv_nextpage.setOnClickListener()
        {
            var intent =Intent(context,Login::class.java)
            startActivity(intent)
        }

        Log.d("Tab3","Tab3")
        return x
    }
}