package com.example.vankhanhpr.vidu2.fragment_main.information_Account

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.adapter.adapter_diciamal.Adapter_File_Mom_Baby
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby.Create_File_Mon
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.File_Mom_Baby
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.login_baby.Import_Code
import com.google.gson.Gson
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import android.widget.AdapterView.OnItemClickListener
import com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby.Update_File_Mom
import com.example.vankhanhpr.vidu2.getter_setter.dicimal_getset.Information_Schedule


/**
 * Created by VANKHANHPR on 7/21/2017.
 */
class Show_List_File_MonBaby:AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener
{
    var call= Call_Receive_Server.getIns()
    var listFile_Mom:ArrayList<File_Mom_Baby>?=null
    var lv_show_list_file:ListView?=null
    var back_file_mom:LinearLayout?=null
    var tab_create_file_mom:LinearLayout?=null
    var tab_no_data_manage_file:LinearLayout?=null
    var tab_list_doctor_baby:LinearLayout?=null
    var adapter_search: ListAdapter? = null
    var listFile:ArrayList<JSONObject>?=null
    var disconnect:TextView?=null
    var refres_file:SwipeRefreshLayout?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.file_list_mom_baby)
        EventBus.getDefault().register(this)


        listFile_Mom= ArrayList()
        listFile= ArrayList()
        back_file_mom= findViewById(R.id.back_file_mom) as LinearLayout
        tab_create_file_mom=findViewById(R.id.tab_create_file_mom) as LinearLayout
        tab_no_data_manage_file=findViewById(R.id.tab_no_data_manage_file) as LinearLayout
        tab_list_doctor_baby=findViewById(R.id.tab_list_doctor_baby)  as LinearLayout
        disconnect=findViewById(R.id.disconnect)as TextView
        refres_file=findViewById(R.id.refres_file) as SwipeRefreshLayout
        refres_file!!.setOnRefreshListener(this)

        lv_show_list_file= findViewById(R.id.lv_show_list_file) as ListView
        getListFile()


        back_file_mom!!.setOnClickListener()
        {
            finish()
        }
        tab_create_file_mom!!.setOnClickListener()
        {
            sendToActivityCreateFile(Json.AppLoginID,AllValue.createFile!!)
        }
        lv_show_list_file!!.setOnItemClickListener{
            parent, view, position, id ->
            var mom_baby:JSONObject= listFile!![position]
            sendToActivityUpdateFile(mom_baby.toString(),2233)
        }
        lv_show_list_file!!.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

            }
            override fun onScroll(listView: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val topRowVerticalPosition = if (listView == null || listView.childCount === 0)
                    0
                else
                    lv_show_list_file!!.getChildAt(0).getTop()
                refres_file!!.setEnabled(topRowVerticalPosition >= 0)

            }
        })
    }

    //Insert data success
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         if (data == null) {
            return
        }

        if (requestCode == AllValue.createFile) {
          getListFile()
        }

    }
    //call Load data
    fun  getListFile()
    {
        listFile!!.clear()
        listFile_Mom!!.clear()
        var inval: Array<String> = arrayOf( Json.AppLoginID)
        call.CallEmit(AllValue.workername_search_file,AllValue.servicename_search_file,inval,AllValue.seach_list_file!!)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if(event.getTemp()==AllValue.seach_list_file && event.getService()!!.getData().toString()!=null)
        {

            tab_no_data_manage_file!!.visibility= View.GONE
            tab_list_doctor_baby!!.visibility=View.VISIBLE
            listFile = event.getService()!!.getData()!!
            Log.d("Show_List_File_MonBaby",listFile!!.size.toString())
            for(i in 0..listFile!!.size-1)
            {
                val gson = Gson()
                var tm: File_Mom_Baby = gson.fromJson(listFile!![i].toString(),File_Mom_Baby::class.java)
                listFile_Mom!!.add(tm)
            }
            Log.d("Show_List_File_MonMom",listFile_Mom!!.size.toString())
            var adapter=Adapter_File_Mom_Baby(applicationContext,listFile_Mom!!)
            lv_show_list_file!!.adapter= adapter
        }
        if (event.getTemp() == AllValue.disconnect) {

            disconnect!!.visibility = View.VISIBLE
        }
        if (event.getTemp() == AllValue.connect) {
            disconnect!!.visibility = View.GONE
        }
    }
    public override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }


    fun sendToActivityCreateFile(value: String,resultcode:Int) {

        var intent3 = Intent(applicationContext, Create_File_Mon::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
    }
    fun sendToActivityUpdateFile(value: String,resultcode:Int) {

        var intent3 = Intent(applicationContext, Update_File_Mom::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
    }

    override fun onRefresh() {
        Handler().postDelayed(Runnable {
            getListFile()
            refres_file!!.setRefreshing(false)
        }, 2000)
    }
}