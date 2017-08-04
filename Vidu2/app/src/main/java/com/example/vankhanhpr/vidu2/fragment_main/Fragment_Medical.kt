package com.example.vankhanhpr.vidu2.fragment_main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.baoyz.swipemenulistview.SwipeMenuListView
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.adapter.adapter_diciamal.Adapter_Decima
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.change_password.ChangePass
import com.example.vankhanhpr.vidu2.fragment_main.fragment_medical.Schedule_Infor_Detail
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.getter_setter.dicimal_getset.Information_Schedule
import com.example.vankhanhpr.vidu2.getter_setter.dicimal_getset.Schedule
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.json.Service_Response
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_medical.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import com.baoyz.swipemenulistview.SwipeMenu
import android.R.drawable.ic_delete
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import com.example.vankhanhpr.vidu2.library_bottomnagi.BottomNavigationViewEx.dp2px
import android.graphics.Color.rgb
import android.graphics.drawable.ColorDrawable

import android.os.Build
import android.support.annotation.RequiresApi
import android.view.Window
import android.widget.*
import com.baoyz.swipemenulistview.SwipeMenuItem
import com.baoyz.swipemenulistview.SwipeMenuCreator
import com.example.vankhanhpr.vidu2.MainActivity
import com.example.vankhanhpr.vidu2.fragment_main.fragment_medical.spinner.MyAdapter
import com.example.vankhanhpr.vidu2.fragment_main.fragment_medical.spinner.StateVO
import com.example.vankhanhpr.vidu2.myinterface.IFilter_Scheduler
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by VANKHANHPR on 7/9/2017.
 */

class Fragment_Medical() : Fragment(), SwipeRefreshLayout.OnRefreshListener,View.OnClickListener,IFilter_Scheduler
{
    var call =Call_Receive_Server.getIns()
    var lv_Medical: SwipeMenuListView?=null
    var list_Schedule: ArrayList<Schedule>? = ArrayList()
    var list :ArrayList<JSONObject> = ArrayList()

    var tab_error_medical:LinearLayout?=null
    var tab_lv_medical:LinearLayout?=null
    var srlLayout:SwipeRefreshLayout?=null
    var dialog_cancel_schedule:Dialog?=null
    var today = Date(System.currentTimeMillis())
    var dialog_ok:Dialog?=null

    var month_one:LinearLayout?=null
    var month_three:LinearLayout?=null
    var month_six:LinearLayout?=null
    var month_twelve:LinearLayout?=null
    var month_all:LinearLayout?=null
    var date_schedule:String=""
    var now =Calendar.getInstance()
    var timeFormat2: SimpleDateFormat =  SimpleDateFormat("yyyyMMdd")
    var spinner_bucker:Spinner?=null
    var vivo:ArrayList<StateVO> ?= ArrayList()



    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var k: View = inflater!!.inflate(R.layout.fragment_medical, container, false)
        EventBus.getDefault().register(this)
        lv_Medical= k.findViewById(R.id.id_medical) as SwipeMenuListView
        tab_error_medical=k.findViewById(R.id.tab_error_medical) as LinearLayout
        tab_lv_medical=k.findViewById(R.id.tab_lv_medical)as LinearLayout
        srlLayout=k.findViewById(R.id.srlLayout)as SwipeRefreshLayout
        spinner_bucker =k.findViewById(R.id.spinner_bucker)as Spinner
        srlLayout!!.setOnRefreshListener(this)

        month_one= k.findViewById(R.id.month_one) as LinearLayout//1thang
        month_three=k.findViewById(R.id.month_three) as LinearLayout//3 thang
        month_six=k.findViewById(R.id.month_six) as LinearLayout//6 thang
        month_twelve=k.findViewById(R.id.month_twelve) as LinearLayout//12 thang
        month_all=k.findViewById(R.id.month_all) as LinearLayout//set all


        runFilter()

        now.add(Calendar.DATE,-30).toString()
        date_schedule = timeFormat2.format(now.getTime())
        Log.d("ngayhientailala",date_schedule)
        getSchedule()

        var creator = SwipeMenuCreator { menu ->
            // create "open" item
            var openItem = SwipeMenuItem(context)
            // set item background
            openItem.background = ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25))
            // set item width
            openItem.width=200
            // set item title
            openItem.title = "Hủy"
            // set item title fontsize
            openItem.titleSize = 10
            // set item title font color
            openItem.titleColor = Color.WHITE
            // add to menu
            menu.addMenuItem(openItem)
        }

        lv_Medical!!.setMenuCreator(creator)//set tab khi vuốt ngang
        lv_Medical!!.setOnItemClickListener{
            parent, view, position, id ->
            sendToScheduleDetail(list[position]!!.toString(),AllValue.senToSheduleDetail!!)
        }
        lv_Medical!!.setOnMenuItemClickListener(object : SwipeMenuListView.OnMenuItemClickListener{
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onMenuItemClick(position: Int, menu: SwipeMenu, index: Int): Boolean {

                dialog_cancel_schedule = Dialog(context)
                dialog_ok = Dialog(context)
                dialog_ok!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog_ok!!.setContentView(R.layout.dialog_impot_code)

                when (index) {
                    0 -> {
                        dialog_cancel_schedule = Dialog(context)
                        dialog_cancel_schedule!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialog_cancel_schedule!!.setContentView(R.layout.dialog_bucking)
                        var text= dialog_cancel_schedule!!.findViewById(R.id.tv_dialog_signin)as TextView
                        var bntok=dialog_cancel_schedule!!.findViewById(R.id.tv_agree)
                        var bntcancel=dialog_cancel_schedule!!.findViewById(R.id.tv_cancel)
                        dialog_cancel_schedule!!.show()
                        text.setText("Bạn có chắc chắn muốn hủy lịch khám?")
                        bntok.setOnClickListener()
                        {
                           var timeFormat: SimpleDateFormat =  SimpleDateFormat("yyyyMMdd")

                           var s: String=timeFormat.format(today.getTime())
                            Log.d("ngayhientai",""+s.toString())

                            Json.Operation="D"
                            var inval: Array<String> = arrayOf(s,list_Schedule!![position].getC1()!!)
                            call.CallEmit(AllValue.workername_cancel_schedule,AllValue.servicename_cancel_schedule,inval,AllValue.cancel_schedule!!)
                            Json.Operation="Q"
                            dialog_cancel_schedule!!.cancel()
                        }
                        bntcancel.setOnClickListener()
                        {
                            dialog_cancel_schedule!!.cancel()
                        }

                    }
                    1 -> {
                        Toast.makeText(context,"Action 2 for", Toast.LENGTH_SHORT).show();
                    }
                }
                return false
            }
        })
        month_one!!.setOnClickListener(this)
        month_three!!.setOnClickListener(this)
        month_six!!.setOnClickListener(this)
        month_twelve!!.setOnClickListener(this)
        month_all!!.setOnClickListener(this)

        lv_Medical!!.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {

            }
            override fun onScroll(listView: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                val topRowVerticalPosition = if (listView == null || listView.childCount === 0)
                    0
                else
                    lv_Medical!!.getChildAt(0).getTop()
                srlLayout!!.setEnabled(topRowVerticalPosition >= 0)

            }
        })

        var spinnerAdapter= MyAdapter(context,0,vivo!!,this)
        spinner_bucker!!.adapter= spinnerAdapter


        spinner_bucker!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long)
            {
               if(position==0)
               {
                   Toast.makeText(context,"anhkhanhday roi",Toast.LENGTH_SHORT).show()
               }
            }
            override fun onNothingSelected(parentView: AdapterView<*>) {
                var r = 0
            }
        })
        return  k
    }
    fun getSchedule()
    {
        var inval: Array<String> = arrayOf(Json.AppLoginID,date_schedule!!)
        call.CallEmit(AllValue.workername_getschedule_cus,AllValue.servicename_getschedule_cus,inval,AllValue.get_schedule_custommer!!)
    }

    override fun onClick(v: View?) {
        when(v!!.id)
        {
            R.id.month_one ->
            {
                now =Calendar.getInstance()
                now.add(Calendar.DATE,-30).toString()
                date_schedule=timeFormat2.format(now.getTime())
                Log.d("ngayhientailala",date_schedule)
                month_one!!.background= resources.getDrawable(R.drawable.border_button_month)
                month_three!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_six!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_twelve!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_all!!.background= resources.getDrawable(R.drawable.border_layout_dark)

                getSchedule()
            }
            R.id.month_three->
            {
                now =Calendar.getInstance()
                now.add(Calendar.DATE,-90).toString()
                date_schedule=timeFormat2.format(now.getTime())
                getSchedule()
                month_one!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_three!!.background= resources.getDrawable(R.drawable.border_button_month)
                month_six!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_twelve!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_all!!.background= resources.getDrawable(R.drawable.border_layout_dark)
            }
            R.id.month_six ->{
                now =Calendar.getInstance()
                now.add(Calendar.DATE,-120).toString()
                date_schedule=timeFormat2.format(now.getTime())
                getSchedule()
                month_one!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_three!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_six!!.background= resources.getDrawable(R.drawable.border_button_month)
                month_twelve!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_all!!.background= resources.getDrawable(R.drawable.border_layout_dark)
            }
            R.id.month_twelve ->{
                now =Calendar.getInstance()
                now.add(Calendar.DATE,-365).toString()
                date_schedule=timeFormat2.format(now.getTime())
                getSchedule()
                month_one!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_three!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_six!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_twelve!!.background= resources.getDrawable(R.drawable.border_button_month)
                month_all!!.background= resources.getDrawable(R.drawable.border_layout_dark)
            }
            R.id.month_all->{
                now =Calendar.getInstance()
                now.add(Calendar.DATE,-730).toString()
                date_schedule=timeFormat2.format(now.getTime())
                getSchedule()
                month_one!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_three!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_six!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_twelve!!.background= resources.getDrawable(R.drawable.border_layout_dark)
                month_all!!.background= resources.getDrawable(R.drawable.border_button_month)
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        var serve: Service_Response = event.getService()!!
        if(event.getTemp()==AllValue.get_schedule_custommer!!)
        {
           if(serve.getResult()=="0"|| (event.getService()!!.getData()!!.toString()==Json.error))
           {
              tab_error_medical!!.visibility=View.VISIBLE
              tab_lv_medical!!.visibility=View.GONE
           }
            else
            if(serve.getResult()=="1")
            {
                Log.d("mydata",event.getService()!!.getData()!!.toString())
                tab_error_medical!!.visibility=View.GONE
                tab_lv_medical!!.visibility=View.VISIBLE
                list= event.getService()!!.getData()!!
                list_Schedule!!.clear()
                var listtemp:ArrayList<Schedule> = ArrayList()
                for (i in 0..list!!.size - 1) {
                    var gson = Gson()
                    var tm: Schedule = gson.fromJson(list!![i].toString(), Schedule::class.java)
                    listtemp!!.add(tm)
                }
                //fitter
                for(i in 0..listtemp!!.size - 1)
                {
                    if(listtemp[i].getC11()=="N")
                    {
                        list_Schedule!!.add(listtemp!![i])
                    }
                }
                for(i in 0..listtemp!!.size - 1)
                {
                    if(listtemp[i].getC11()=="Y")
                    {
                        list_Schedule!!.add(listtemp!![i])
                    }
                }
                for(i in 0..listtemp!!.size - 1)
                {
                    if(listtemp[i].getC11()=="D"||listtemp[i].getC11()=="C")
                    {
                        list_Schedule!!.add(listtemp!![i])
                    }
                }


                var adapter:Adapter_Decima= Adapter_Decima(context,list_Schedule!!)
                Log.d("lissss",listtemp.toString())
                lv_Medical!!.adapter= adapter
            }
        }
        if(event.getTemp()==AllValue.cancel_schedule)
        {//Xử lý khi hủy đặt lịch
            if(event.getService()!!.getResult()=="1")
            {
                Toast.makeText(context,"Hủy lịch thanh công",Toast.LENGTH_SHORT).show()
                var inval: Array<String> = arrayOf(Json.AppLoginID,"20120101")
                call.CallEmit(AllValue.workername_getschedule_cus,AllValue.servicename_getschedule_cus,inval,AllValue.get_schedule_custommer!!)
            }
            else
            {
                dialog_ok!!.show()
                var tv_set_import_code=dialog_ok!!.findViewById(R.id.tv_set_import_code) as TextView
                var btn_cancel_import_code= dialog_ok!!.findViewById(R.id.btn_cancel_import_code)
                tv_set_import_code.setText(event.getService()!!.getMessage())
                btn_cancel_import_code.setOnClickListener()
                {
                    dialog_ok!!.cancel()
                }
            }
        }
    }
    public override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }
    //change pass
    fun sendToScheduleDetail(value: String,resultcode:Int) {
        var intent3 = Intent(context, Schedule_Infor_Detail::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
    }
    override fun onRefresh() {
        Handler().postDelayed(Runnable {
            var inval: Array<String> = arrayOf(Json.AppLoginID,date_schedule!!)
            call.CallEmit(AllValue.workername_getschedule_cus,AllValue.servicename_getschedule_cus,inval,AllValue.get_schedule_custommer!!)
            srlLayout!!.setRefreshing(false)
        }, 2000)
    }


    override fun Call_filter(arr: ArrayList<StateVO>) {
        super.Call_filter(arr)
        if(list_Schedule!=null)
        {
            var listTemp:ArrayList<Schedule> = ArrayList()
            if(arr!!.get(4).isSelected())
            {

                var adapter1:Adapter_Decima= Adapter_Decima(context,list_Schedule!!)
                lv_Medical!!.adapter= adapter1
                return
            }
            else {
                Log.d("khanh12_","thong tin la 1:"+arr!!.get(1).isSelected().toString()+ " 2:"+arr!!.get(2).isSelected().toString()+" 3:"+arr!!.get(3).isSelected().toString()+" 4:"+ arr!!.get(4).isSelected().toString())
                if (arr!!.get(1).isSelected()) {
                    for (i in 0..list_Schedule!!.size - 1) {
                        if (list_Schedule!!.get(i).getC11() == resources.getString(R.string.wait)) {
                            listTemp.add(list_Schedule!!.get(i))
                        }
                    }
                }
                if (arr!!.get(2).isSelected()) {

                    for (i in 0..list_Schedule!!.size - 1) {
                        if (list_Schedule!!.get(i).getC11() == resources.getString(R.string.succsess_bucker)) {
                            listTemp.add(list_Schedule!!.get(i))
                        }
                    }
                    Log.d("khanh12trangthaicua2la","xx"+listTemp.size)
                }
                if (arr!!.get(3).isSelected()) {
                    for (i in 0..list_Schedule!!.size - 1) {
                        if (list_Schedule!!.get(i).getC11() == resources.getString(R.string.cancel_buker) || list_Schedule!!.get(i).getC12() == resources.getString(R.string.cancel_bucker_system)) {
                            listTemp.add(list_Schedule!!.get(i))
                        }
                    }
                }
            }
            var adapter3:Adapter_Decima= Adapter_Decima(context,listTemp)
            lv_Medical!!.adapter= adapter3
        }
    }
    fun runFilter()
    {
        vivo=ArrayList()
        var tem0:StateVO= StateVO()
        tem0.setTitle("Chọn")
        tem0.setSelected(false)
        vivo!!.add(tem0)

        var tem2:StateVO= StateVO()
        tem2.setTitle("Chưa khám")
        tem2.setSelected(false)
        vivo!!.add(tem2)

        var tem3:StateVO= StateVO()
        tem3.setTitle("Khám xong")
        tem3.setSelected(false)
        vivo!!.add(tem3)

        var tem4:StateVO= StateVO()
        tem4.setTitle("Hủy lịch")
        tem4.setSelected(false)
        vivo!!.add(tem4)

        var tem:StateVO= StateVO()
        tem.setSelected(false)
        tem.setTitle("Tất cả")
        vivo!!.add(tem)
    }
}
