package com.example.vankhanhpr.vidu2.fragment_main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.adapter.adapter_diciamal.Adapter_List_Doctor
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.fragment_main.fragment_bucking.Map_Location_Mom

import com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby.map.GpsTracker
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.Doctor
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.google.gson.Gson

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject








/**
 * Created by VANKHANHPR on 7/9/2017.
 */

class Fragment_Mom:Fragment(),SearchView.OnQueryTextListener,SwipeRefreshLayout.OnRefreshListener
{



    var listDoctor:ArrayList<Doctor>?=null
    var call= Call_Receive_Server.getIns()
    var lv_mom_chedule_bucked:ListView?=null
    var tab_list_doctor:LinearLayout?=null
    var tab_no_data_listdoctor:LinearLayout?=null
    var listdoctor:ArrayList<JSONObject>?=null
    var search_mom:SearchView?=null
    var adapter:Adapter_List_Doctor?=null
    var refres_mom:SwipeRefreshLayout?=null
    var lat:Double?=null
    var lon:Double?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        EventBus.getDefault().register(this)
        var k: View = inflater!!.inflate(R.layout.fragment_mom, container, false)

        listDoctor= ArrayList()
        listdoctor= ArrayList()
        search_mom=k.findViewById(R.id.search_mom) as SearchView
        search_mom!!.setIconifiedByDefault(true)
        search_mom!!.setOnQueryTextListener(this)
        search_mom!!.setSubmitButtonEnabled(false)

        lv_mom_chedule_bucked=k.findViewById(R.id.lv_mom_chedule_bucked) as ListView
        tab_list_doctor=k.findViewById(R.id.tab_list_doctor) as LinearLayout
        tab_no_data_listdoctor=k.findViewById(R.id.tab_no_data_listdoctor) as LinearLayout
        refres_mom=k.findViewById(R.id.refres_mom) as SwipeRefreshLayout
        refres_mom!!.setOnRefreshListener(this)

        var gt = GpsTracker(context)
        var l = gt.getLocation()

        if (l == null) {
            Toast.makeText(context, "GPS unable to get Value", Toast.LENGTH_SHORT).show()
        }
        else {
            lat = l!!.latitude
            lon = l!!.longitude
            Log.d("vitri","asasdf"+lat+"   "+lon)
        }
        getListDoctor()

        lv_mom_chedule_bucked!!.setOnItemClickListener{
            parent, view, position, id ->
            sendToActivityMapMom(listdoctor!![position].toString(),9090)
        }

        lv_mom_chedule_bucked!!.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

            }
            override fun onScroll(listView: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val topRowVerticalPosition = if (listView == null || listView.childCount === 0)
                    0
                else
                    lv_mom_chedule_bucked!!.getChildAt(0).getTop()
                refres_mom!!.setEnabled(topRowVerticalPosition >= 0)
            }
        })
        return k
    }
    fun getListDoctor()
    {
        var inval: Array<String> = arrayOf("2",lat.toString(),lon.toString(),"2000")
        call.CallEmit(AllValue.workername_get_listdoctor,AllValue.servicename_get_listdoctor,inval!!,AllValue.get_list_doctor_mom!!)
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        return  false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        try {
            adapter!!.getFilter().filter(newText)
        }
        catch (e:Exception){}
        return false
    }
    override fun onRefresh() {
        Handler().postDelayed(Runnable {
            getListDoctor()
            refres_mom!!.setRefreshing(false)
        }, 2000)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getTemp()==AllValue.get_list_doctor_mom)
        {
            if(event.getService()!!.getResult()=="0" || event.getService()!!.getData()!!.toString() == Json.error)
            {
                tab_no_data_listdoctor!!.visibility= View.VISIBLE
                tab_list_doctor!!.visibility=View.GONE
            }
            else {

                tab_no_data_listdoctor!!.visibility = View.GONE
                tab_list_doctor!!.visibility = View.VISIBLE
                listdoctor = event.getService()!!.getData()!!
                for (i in 0..listdoctor!!.size - 1) {
                    val gson = Gson()
                    var tm: Doctor = gson.fromJson(listdoctor!![i].toString(), Doctor::class.java)
                    listDoctor!!.add(tm)
                }
                adapter = Adapter_List_Doctor(context, listDoctor!!)
                lv_mom_chedule_bucked!!.adapter = adapter!!
            }
        }
    }
    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }


    fun sendToActivityMapMom(value: String,resultcode:Int) {
        var intent3 = Intent(context, Map_Location_Mom::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
    }
}