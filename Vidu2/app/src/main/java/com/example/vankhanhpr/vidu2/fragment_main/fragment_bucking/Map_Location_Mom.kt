package com.example.vankhanhpr.vidu2.fragment_main.fragment_bucking

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.adapter.adapter_diciamal.Adapter_File_Mom_Baby
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby.Create_File_Mon
import com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby.MyFragment
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.IsNumber
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.Doctor
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.File_Mom_Baby
import com.example.vankhanhpr.vidu2.json.MessageEvent
import com.example.vankhanhpr.vidu2.login_baby.CheckPassSignIn
import com.google.gson.Gson
import kotlinx.android.synthetic.main.map_location_doctor.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by VANKHANHPR on 7/22/2017.
 */

class Map_Location_Mom :AppCompatActivity() {
    var call = Call_Receive_Server.getIns()
    var tab_bucking: LinearLayout? = null
    var listFile:ArrayList<File_Mom_Baby>?=null
    var lisJson:ArrayList<JSONObject>?=null
    var dialog5:Dialog?=null
    var dialog_bucking22:Dialog?= null
    var file:File_Mom_Baby?=null
    var doctorS:String?=null
    var doctor:Doctor?=null
    var f_mom_baby:Boolean?=true

    var date_bucking:String=""
    var time_bucking:String?=""
    var tv_date4:TextView?=null
    var f:Boolean?=false
    var f_time:Boolean?=true
    var cal: Calendar? = Calendar.getInstance()
    var dateFinish: Date? = null
    var tv_time_bucket:TextView?=null
    var mCountDownTimer: CountDownTimer? = null
    var dialog_disconnect:Dialog?=null
    var dialog_error_bucking:Dialog?=null
    var hourFinish: Date? = null
    var back_map_location:LinearLayout?=null
    var dialog_list_baby:Dialog?=null
    var tab_bucking_file:ProgressBar?=null

    var baby:File_Mom_Baby?=null
    var progress2:ProgressBar?=null
    var tab_map:LinearLayout?=null
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    var fragment_map: Fragment?= null
    var disconnect:TextView?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_location_doctor)
        EventBus.getDefault().register(this)

        tab_map= findViewById(R.id.tab_map)as LinearLayout
        tab_bucking_file=findViewById(R.id.tab_bucking_file)as ProgressBar
        disconnect=findViewById(R.id.disconnect)as TextView

        if(Json.mom_baby==1)//kiem tra neu la be
        {
            f_mom_baby=false
        }
        if(Json.mom_baby==0)//neu la me
        {
            f_mom_baby=true
        }
        tv_date4=findViewById(R.id.tv_date) as TextView
        tv_time_bucket=findViewById(R.id.tv_time_bucket) as TextView
        back_map_location=findViewById(R.id.back_map_location) as LinearLayout

        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)
        doctorS= bundle.getString(AllValue.value)
        val gson = Gson()
        doctor = gson.fromJson(doctorS,Doctor::class.java)
        Json.doctor=doctor
        addFragment()
        listFile= ArrayList()
        lisJson= ArrayList()
        tab_bucking = findViewById(R.id.tab_bucking) as LinearLayout
        getDefaultInfor()//lấy thời gian hiện tại

        tab_bucking!!.setOnClickListener()
        {
            tab_bucking_file!!.visibility=View.VISIBLE
            dialog_error_bucking= Dialog(this)
            dialog_error_bucking!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_error_bucking!!.setContentView(R.layout.dialog_error)

            dialog_list_baby= Dialog(this)
            dialog_list_baby!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_list_baby!!.setContentView(R.layout.dialog_file_baby)

            dialog5= Dialog(this)
            dialog_bucking22= Dialog(this)
            dialog5!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog5!!.setContentView(R.layout.dialog_bucking)
            dialog_bucking22!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_bucking22!!.setContentView(R.layout.dialog_bucking)

            dialog_disconnect = Dialog(this)
            dialog_disconnect!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_disconnect!!.setContentView(R.layout.dialog_error_password)
            var tv_cancel = dialog_disconnect!!.findViewById(R.id.tv_error) as TextView
            var button_cancel = dialog_disconnect!!.findViewById(R.id.btn_cancel_error_pass)

            getListFile()

            var i=0
            mCountDownTimer = object : CountDownTimer(3000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    i++
                    Call_Receive_Server.getIns().Sevecie()
                    if (i == 1) {
                        for (i in 0..Call_Receive_Server.getIns().hasmap!!.size - 1) {
                            Call_Receive_Server.getIns().hasmap!![i].setStatus(0)
                        }
                    }
                    if (i == 3) {
                        Call_Receive_Server.getIns().Sevecie()
                        tab_bucking_file!!.visibility = View.GONE
                    }
                }

                override fun onFinish() {//Do what you want
                    i++
                    try {
                        Call_Receive_Server.getIns().Sevecie()
                        dialog_disconnect!!.show()
                        tv_cancel.setText("Vui lòng kiểm tra kết nối của bạn")
                        button_cancel.setOnClickListener()
                        {
                            dialog_disconnect!!.cancel()
                        }
                        tab_bucking_file!!.visibility = View.GONE
                    } catch (e: Exception) {
                    }
                }
            }
            mCountDownTimer!!.start()
        }
        back_map_location!!.setOnClickListener()
        {
            finish()
        }

        imv_calendar_map!!.setOnClickListener()
        {
            showDatePickerDialog()
        }
        imv_time_bucking_map.setOnClickListener()
        {
            showTimePickerDialog()
        }
    }

    fun getListFile() {
        var inval: Array<String> = arrayOf(Json.AppLoginID)
        call.CallEmit(AllValue.workername_search_file, AllValue.servicename_search_file, inval, AllValue.getlist_file_mom!!)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getTemp() == AllValue.getlist_file_mom && event.getService()!!.getData().toString() != Json.error) {

            tab_bucking_file!!.visibility=View.GONE
            dialog_disconnect!!.cancel()
            mCountDownTimer!!.cancel()
            if(event.getService()!!.getResult()=="1")
            {

                lisJson = event.getService()!!.getData()
                for (i in 0..lisJson!!.size - 1) {
                    var gson = Gson()
                    var tm: File_Mom_Baby = gson.fromJson(lisJson!![i].toString(), File_Mom_Baby::class.java)
                    listFile!!.add(tm)
                }

                var s:String=""
                if(f_mom_baby!!)//neu la me
                {
                    s = boolMom(listFile!!)
                }
                if(!f_mom_baby!!)//neu la be
                {
                    s=boolBaby(listFile!!)
                }
                if (s == "")
                {
                    dialog5!!.show()
                    var btn_ok = dialog5!!.findViewById(R.id.tv_agree)
                    var btn_cancel = dialog5!!.findViewById(R.id.tv_cancel)

                    btn_ok.setOnClickListener()
                    {
                        if(f_mom_baby!!) {
                            Json.bucking = 0
                        }
                        else{
                            Json.bucking = 1
                        }
                        sendToActivityCreateFile(Json.AppLoginID, AllValue.sentToCreateFile!!)
                    }
                    btn_cancel.setOnClickListener()
                    {
                        dialog5!!.cancel()
                    }
                }
                else {
                    if (f_mom_baby!!) {//neu la me

                       /* dialog_bucking22!!.show()
                        var bnt_gtobucking = dialog_bucking22!!.findViewById(R.id.tv_agree)
                        var tv_notication = dialog_bucking22!!.findViewById(R.id.tv_dialog_signin) as TextView
                        var tv_cancel=dialog_bucking22!!.findViewById(R.id.tv_cancel)as TextView


                        tv_notication!!.setText("Tiến hành đặt hồ sơ cho hồ sơ :" + file!!.getC3())

                        tv_cancel!!.setOnClickListener()
                        {
                            dialog_bucking22!!.cancel()
                        }*/
                       /* bnt_gtobucking!!.setOnClickListener()
                        {

                        }*/
                        callTime()//lay thoi gian du kien
                    }
                    else {//neu la be
                        var file:ArrayList<File_Mom_Baby> = getlistBaby(listFile!!)
                        var adapter_file=Adapter_File_Mom_Baby(applicationContext,file!!)
                        var lv_dialog_list_baby= dialog_list_baby!!.findViewById(R.id.lv_dialog_list_baby) as ListView
                        var tab_cancel_dialog_baby =dialog_list_baby!!.findViewById(R.id.tab_cancel_dialog_baby)as LinearLayout
                        lv_dialog_list_baby.adapter= adapter_file!!

                        dialog_list_baby!!.show()
                        tab_cancel_dialog_baby.setOnClickListener()
                        {
                            dialog_list_baby!!.cancel()
                        }
                        lv_dialog_list_baby!!.setOnItemClickListener{
                            parent, view, position, id ->
                            baby = listFile!![position]
                            callTime()


                            progress2 = dialog_list_baby!!.findViewById(R.id.load_file)as ProgressBar
                            progress2!!.visibility=View.VISIBLE
                            tab_bucking_file!!.visibility=View.VISIBLE
                            var tv_cancel = dialog_disconnect!!.findViewById(R.id.tv_error) as TextView
                            var button_cancel = dialog_disconnect!!.findViewById(R.id.btn_cancel_error_pass)
                            var i=0
                            mCountDownTimer = object : CountDownTimer(3000, 1000) {
                                override fun onTick(millisUntilFinished: Long) {
                                    i++
                                    Call_Receive_Server.getIns().Sevecie()
                                    if (i == 1) {
                                        for (i in 0..Call_Receive_Server.getIns().hasmap!!.size - 1) {
                                            Call_Receive_Server.getIns().hasmap!![i].setStatus(0)
                                        }
                                    }
                                    if (i == 3) {
                                        Call_Receive_Server.getIns().Sevecie()
                                        tab_bucking_file!!.visibility = View.GONE
                                        progress2!!.visibility=View.GONE
                                    }
                                }

                                override fun onFinish() {//Do what you want
                                    i++
                                    try {
                                        Call_Receive_Server.getIns().Sevecie()
                                        dialog_disconnect!!.show()
                                        tv_cancel.setText("Vui lòng kiểm tra kết nối của bạn")
                                        button_cancel.setOnClickListener()
                                        {
                                            dialog_disconnect!!.cancel()
                                        }
                                        tab_bucking_file!!.visibility = View.GONE
                                        progress2!!.visibility=View.GONE
                                    } catch (e: Exception) {
                                    }
                                }
                            }
                            mCountDownTimer!!.start()
                        }
                    }
                }
            }
            else
            {
                //Không có thông tin hồ sơ
            }
        }
        if(event.getTemp()==AllValue.get_time_expected)
        {
            tab_bucking_file!!.visibility= View.GONE
            mCountDownTimer!!.cancel()
            dialog_disconnect!!.cancel()
            dialog_list_baby!!.cancel()
            if(event.getService()!!.getResult()=="1")
            {
                var s2 = boolMom(listFile!!)
                var temp:ArrayList<JSONObject> = event.getService()!!.getData()!!
                var time_main:IsNumber= readJson1(temp!!)
                sendToActivityBucking(doctor!!.getC0()!!,s2,date_bucking,time_main!!.getSecC0()!!,doctor!!.getC1()!!, AllValue.sentToBucking!!)
                Log.d("DatLich","" + doctor!!.getC0()!!+ s2 +date_bucking + time_main!!.getSecC0()!!+ AllValue.sentToBucking!!)
            }
            else
            {
                dialog_error_bucking!!.show()
                var notical =dialog_error_bucking!!.findViewById(R.id.tv_error) as TextView
                var cancel=dialog_error_bucking!!.findViewById(R.id.btn_cancel_error)

                notical.setText(""+event.getService()!!.getMessage())
                cancel.setOnClickListener()
                {
                    dialog_error_bucking!!.cancel()
                }
            }
        }
        if (event.getTemp() == AllValue.disconnect) {

            disconnect!!.visibility= View.VISIBLE
        }
        if (event.getTemp() == AllValue.connect) {
            disconnect!!.visibility = View.GONE
        }
    }
    public override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    fun  callTime()//lay thoi gian du kien
    {
        Json.Operation="E"
        var inval: Array<String> = arrayOf(doctor!!.getC0()!!,date_bucking!!,time_bucking!!)
        call!!.CallEmit(AllValue.workername_set_time_expected,AllValue.servicename_set_time_expected,inval,AllValue.get_time_expected!!)
        Json.Operation="Q"
    }
    fun boolBaby(list:ArrayList<File_Mom_Baby>):String
    {
        var x:String=""
        for(i in 0..list!!.size-1)
        {
            if(list!![i].getC1()=="Con")
            {
                return list[i].getC1()!!
            }
        }
        return x
    }
    fun  getlistBaby(list:ArrayList<File_Mom_Baby>):ArrayList<File_Mom_Baby>
    {
        var f:ArrayList<File_Mom_Baby> = ArrayList()
        for(i in 0..list!!.size-1)
        {
            if(list!![i].getC1()=="Con")
            {
                f.add(list!![i])
            }
        }
        return f
    }
    fun  boolMom(list:ArrayList<File_Mom_Baby>):String
    {
        var f:String=""
        for(i in 0..list!!.size-1)
        {
            if(list!![i].getC1()=="Vợ" || list!![i].getC1()=="Tôi")
            {
                file=list!![i]
                return  list!![i].getC2().toString()
            }
        }
        return f
    }



    //hàm chuyển qua Sign In
    fun sendToActivityCreateFile(value: String,resultcode:Int) {
        var intent3 = Intent(applicationContext, Create_File_Mon::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
    fun sendToActivityBucking(value: String,value2:String,value3:String,value4:String,name:String,resultcode:Int) {
        var intent3 = Intent(applicationContext, BuckingMom::class.java)
        var bundle = Bundle()

        bundle.putString(AllValue.value, value)
        bundle.putString(AllValue.value2, value2)
        bundle.putString(AllValue.value3, value3)
        bundle.putString(AllValue.value4, value4)
        bundle.putString("name", name)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }

    fun showTimePickerDialog() {
        val callback = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            //Xử lý lưu giờ và AM,PM
            var s = hourOfDay.toString() + ":" + minute
            var hourTam = hourOfDay
            time_bucking = hourOfDay.toString() + minute
            if (hourTam > 12)
                hourTam = hourTam - 12
            f_time = false
            tv_time_bucket!!.setText(hourTam.toString() + ":" + minute + if (hourOfDay > 12) " PM" else " AM")
            //lưu giờ thực vào tag

            tv_time_bucket!!.setTag(s)
            //lưu vết lại giờ vào hourFinish
            cal!!.set(Calendar.HOUR_OF_DAY, hourOfDay)
            cal!!.set(Calendar.MINUTE, minute)
            //hourFinish = cal.getTime()
        }
        var s = tv_time_bucket!!.text
        var strArr = s.split(":")

        var gio = 12
        var phut = 12
        if(!f_time!!) {
            gio = Integer.parseInt(strArr[0])
            phut = Integer.parseInt(strArr[1].substring(0,2))
        }
        if(f_time!!) {
            gio = 12
            phut = 12
        }

        var time = TimePickerDialog(this, callback, gio, phut, true)
        time.setTitle("Chọn giờ khám")
        time.show()
    }

    fun getDefaultInfor()
    {
        //lấy ngày hiện tại của hệ thống
        cal=Calendar.getInstance();
        var dft: SimpleDateFormat? = null;
        //Định dạng ngày / tháng /năm
        dft= SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
        var strDate:String=dft.format(cal!!.getTime());
        //hiển thị lên giao diện
        tv_date4!!.setText(strDate)

        var dayOfMonthS: String? = strDate.substring(0,2)
        var monthOfYearS: String? = strDate.substring(3,5)
        var yearS: String? = strDate.substring(6)
        date_bucking= ""+yearS + monthOfYearS + dayOfMonthS

        //Định dạng giờ phút am/pm
        dft= SimpleDateFormat("hh:mm a",Locale.getDefault());
        var strTime:String=dft.format(cal!!.getTime());
        //đưa lên giao diện
        //tv_time_bucket!!.setText(strTime);
        //lấy giờ theo 24 để lập trình theo Tag
        dft=  SimpleDateFormat("HH:mm",Locale.getDefault());
        //tv_time_bucket!!.setTag(dft.format(cal!!.getTime()));
        //editCv.requestFocus();
        //gán cal.getTime() cho ngày hoàn thành và giờ hoàn thành
        dateFinish=cal!!.getTime();
        hourFinish=cal!!.getTime();
    }

    fun showDatePickerDialog() {
        val callback = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            var dayOfMonthS: String? = ""
            var monthOfYearS: String? = ""
            var yearS: String? = ""
            if (dayOfMonth < 10) {
                dayOfMonthS = "0" + dayOfMonth
            } else {
                dayOfMonthS = "" + dayOfMonth
            }
            if (monthOfYear + 1 < 10) {
                monthOfYearS = "0" + (monthOfYear + 1).toString()
            } else {
                monthOfYearS = "" + (monthOfYear + 1).toString()
            }
            f=false
            date_bucking= year.toString()+monthOfYearS+dayOfMonth
            tv_date4!!.setText(dayOfMonthS.toString() + "/" + monthOfYearS + "/" + year)
            //cal!!.set(year, monthOfYear, dayOfMonth)
            //dateFinish = cal!!.getTime()
        }
        var s = tv_date4!!.text
        var strArrtmp = s.split("/")
        var ngay = 1
        var thang = 1
        var nam = 2012
        if(!f!!) {
            ngay = Integer.parseInt(strArrtmp[0])
            thang = Integer.parseInt(strArrtmp[1])
            nam = Integer.parseInt(strArrtmp[2])
        }
        if(f!!) {
            ngay = 24
            thang = 7
            nam = 2017
        }
        var pic = DatePickerDialog(this, callback, nam, thang-1, ngay)
        pic.setTitle("Chọn ngày khám")
        pic.show()
    }
    fun readJson1(json: ArrayList<JSONObject>): IsNumber
    {
        var jsonO: JSONObject?=null
        if(json.size>0) {
            jsonO = json[0]
        }
        var c0: String? =jsonO!!.getString("c0")
        var ser1 : IsNumber = IsNumber()
        ser1.setSecC0(c0!!)
        return ser1
    }


    fun addFragment()
    {
        fragment_map = MyFragment()
        fragmentManager =getSupportFragmentManager();
        fragmentTransaction=fragmentManager!!.beginTransaction()

        fragmentTransaction!!.add(R.id.tab_map,fragment_map!!)
        fragmentTransaction!!.show(fragment_map)
        fragmentTransaction!!.commit()
    }
}

