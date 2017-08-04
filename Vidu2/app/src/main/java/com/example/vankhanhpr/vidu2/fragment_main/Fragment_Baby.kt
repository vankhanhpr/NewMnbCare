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
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.adapter.adapter_diciamal.Adapter_List_Doctor
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.change_password.ChangePass
import com.example.vankhanhpr.vidu2.fragment_main.fragment_bucking.Map_Location_Mom
import com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby.map.GpsTracker
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.Doctor
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.json.Service_Response
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import android.widget.AbsListView



/**
 * Created by VANKHANHPR on 7/9/2017.
 */

class Fragment_Baby:Fragment(),SearchView.OnQueryTextListener,SwipeRefreshLayout.OnRefreshListener
{
    var call= Call_Receive_Server.getIns()
    var tab_no_data_listdoctor_baby:LinearLayout?=null
    var tab_list_doctor_baby:LinearLayout?=null
    var listDoctor:ArrayList<Doctor>?=null
    var lv_baby_chedule_bucked:ListView?=null
    var list:ArrayList<JSONObject>?=null
    var adapter:Adapter_List_Doctor?=null
    var search_baby:SearchView?=null
    var refres_baby:SwipeRefreshLayout?=null
    var lat:Double?=null
    var lon:Double?=null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var k: View = inflater!!.inflate(R.layout.fragment_baby, container, false)
        tab_no_data_listdoctor_baby= k.findViewById(R.id.tab_no_data_listdoctor_baby) as LinearLayout
        tab_list_doctor_baby=k.findViewById(R.id.tab_list_doctor_baby) as LinearLayout
        lv_baby_chedule_bucked=k.findViewById(R.id.lv_baby_chedule_bucked) as ListView
        refres_baby=k.findViewById(R.id.refres_baby) as SwipeRefreshLayout
        EventBus.getDefault().register(this)
        refres_baby!!.setOnRefreshListener(this)

        list= ArrayList()
        listDoctor= ArrayList()
        search_baby=k.findViewById(R.id.search_baby) as SearchView
        search_baby!!.setIconifiedByDefault(true)//mo tab search
        search_baby!!.setOnQueryTextListener(this)
        search_baby!!.setSubmitButtonEnabled(false)

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

        lv_baby_chedule_bucked!!.setOnItemClickListener{
            parent, view, position, id ->
            sendToActivityMapMom(list!![position].toString(),9090)
            Json.mom_baby=1
        }
        lv_baby_chedule_bucked!!.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

            }
            override fun onScroll(listView: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val topRowVerticalPosition = if (listView == null || listView.childCount === 0)
                    0
                else
                    lv_baby_chedule_bucked!!.getChildAt(0).getTop()
                refres_baby!!.setEnabled(topRowVerticalPosition >= 0)

            }
        })

        return k
    }
    fun getListDoctor()//lấy danh sách bác sĩ
    {
        var inval: Array<String> = arrayOf("2",lat.toString(),lon.toString(),"2000")
        call.CallEmit(AllValue.workername_get_listdoctor,AllValue.servicename_get_listdoctor,inval,AllValue.get_list_doctor_baby!!)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getTemp()==AllValue.get_list_doctor_baby && event.getService()!!.getData()!!.toString()!=Json.error)
        {
            tab_no_data_listdoctor_baby!!.visibility= View.GONE
            tab_list_doctor_baby!!.visibility= View.VISIBLE
             list =event.getService()!!.getData()!!
            for(i in 0..list!!.size-1)
            {
                val gson = Gson()
                var tm: Doctor = gson.fromJson(list!![i].toString(),Doctor::class.java)
                listDoctor!!.add(tm)
            }
             adapter= Adapter_List_Doctor(context,listDoctor!!)
            lv_baby_chedule_bucked!!.adapter= adapter

        }
    }
    public override fun onStop() {
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


    override fun onRefresh() {
        Handler().postDelayed(Runnable {
            getListDoctor()
            refres_baby!!.setRefreshing(false)
        }, 2000)
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
}
