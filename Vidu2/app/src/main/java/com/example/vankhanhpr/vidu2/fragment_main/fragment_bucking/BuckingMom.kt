package com.example.vankhanhpr.vidu2.fragment_main.fragment_bucking

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import kotlinx.android.synthetic.main.mom_bucking.*
import java.util.*
import com.example.vankhanhpr.vidu2.MainActivity
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.widget.TimePicker
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Context
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import com.example.vankhanhpr.vidu2.getter_setter.IsNumber
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.json.MessageEvent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

/**
 * Created by VANKHANHPR on 7/22/2017.
 */
class BuckingMom:AppCompatActivity()
{
    var call=Call_Receive_Server.getIns()
    var id_doctor:String=""
    var id_mom:String?=""
    var date_bucking:String=""
    var time_bucking:String?=""
    var tv_date4:TextView?=null
    var f:Boolean?=false
    var f_time:Boolean?=false
    var cal: Calendar? = Calendar.getInstance()
    var dateFinish: Date? = null
    var tv_time_bucket:TextView?=null
    var dialog_error1: Dialog?=null
    var dialog_bucking:Dialog?=null
    var progress_bucking:ProgressBar?=null
    var mCountDownTimer: CountDownTimer? = null
    var dialog_disconnect:Dialog?=null
    var dialog_error_bucking:Dialog?=null
    var hourFinish: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mom_bucking)
        EventBus.getDefault().register(this)

        var inte: Intent = intent
        var bundle:Bundle=inte.getBundleExtra(AllValue.key_bundle)
        id_doctor= bundle.getString(AllValue.value)
        id_mom=bundle.getString(AllValue.value2)
        tv_date4=findViewById(R.id.tv_date) as TextView
        tv_time_bucket=findViewById(R.id.tv_time_bucket) as TextView
        progress_bucking=  findViewById(R.id.progress_bucking) as ProgressBar
        getDefaultInfor()
        imv_calendar.setOnClickListener()
        {
            showDatePickerDialog()
        }
        imv_time_bucking.setOnClickListener()
        {
            showTimePickerDialog()
        }
        btn_bucking.setOnClickListener()
        {
            progress_bucking!!.visibility=View.VISIBLE
            dialog_error1= Dialog(this)
            dialog_bucking= Dialog(this)

            dialog_bucking!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_bucking!!.setContentView(R.layout.dialog_bucking)
            dialog_error1!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_error1!!.setContentView(R.layout.dialog_error)

            dialog_disconnect= Dialog(this)
            dialog_disconnect!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_disconnect!!.setContentView(R.layout.dialog_error_password)
            var tv_cancel = dialog_disconnect!!.findViewById(R.id.tv_error) as TextView
            var button_cancel = dialog_disconnect!!.findViewById(R.id.btn_cancel_error_pass)

            //set time bucked expected
            Json.Operation="E"
            var inval: Array<String> = arrayOf(id_doctor,date_bucking!!,time_bucking!!)
            call!!.CallEmit(AllValue.workername_set_time_expected,AllValue.servicename_set_time_expected,inval,AllValue.get_time_expected!!)
            Json.Operation="Q"
            mCountDownTimer = object : CountDownTimer(15000, 1000) {
                var i = 0
                override fun onTick(millisUntilFinished: Long) {
                    i++
                    Call_Receive_Server.getIns().Sevecie()
                    if (i == 5) {

                        for (i in 0..Call_Receive_Server.getIns().hasmap!!.size - 1) {
                            Call_Receive_Server.getIns().hasmap!![i].setStatus(0)
                        }
                    }
                    //mProgressBar.progress = i
                    if (i == 15) {
                        Call_Receive_Server.getIns().Sevecie()
                        progress_bucking!!.visibility = View.GONE
                    }
                }
                override fun onFinish() {
                    //Do what you want
                    i++
                    try {
                        dialog_disconnect!!.show()
                        tv_cancel.setText("Vui lòng kiểm tra kết nối của bạn")
                        button_cancel.setOnClickListener()
                        {
                            dialog_disconnect!!.cancel()
                        }
                        progress_bucking!!.visibility = View.GONE
                    } catch (e: Exception) {
                    }
                    /*}*/
                }
            }
            mCountDownTimer!!.start()

        }
        back_bucking.setOnClickListener()
        {
            finish()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getTemp() == AllValue.get_time_expected) {
            progress_bucking!!.visibility=View.GONE

            mCountDownTimer!!.cancel()
            if(event.getService()!!.getResult()=="1")
            {
                dialog_bucking!!.show()
                var temp :ArrayList<JSONObject> = event.getService()!!.getData()!!
                var time_kham :IsNumber= readJson1(temp)
                var tv_notica= dialog_bucking!!.findViewById(R.id.tv_dialog_signin) as TextView
                var bnt_canc=dialog_bucking!!.findViewById(R.id.tv_cancel) as Button
                var bnt_ok= dialog_bucking!!.findViewById(R.id.tv_agree) as Button

                tv_notica.setText("Thời gian khám dự kiến là: "+time_kham.getSecC0()!!.substring(0,2)+"giờ"+":"+time_kham.getSecC0()!!.substring(2))
                bnt_canc.setOnClickListener()
                {
                    dialog_error1!!.cancel()
                }
                bnt_ok.setOnClickListener()
                {
                    dialog_error_bucking=Dialog(this)
                    progress_bucking!!.visibility= View.VISIBLE
                    //bucking
                    Json.Operation="I"
                    var inval: Array<String> = arrayOf(id_doctor,date_bucking!!,time_bucking!!)
                    call!!.CallEmit(AllValue.workername_set_time_expected,AllValue.servicename_set_time_expected,inval,AllValue.bucking!!)
                    Json.Operation="Q"

                    //disconnect
                    dialog_disconnect=Dialog(this)
                    var tv_cancel = dialog_disconnect!!.findViewById(R.id.tv_error) as TextView
                    var button_cancel = dialog_disconnect!!.findViewById(R.id.btn_cancel_error_pass)

                    mCountDownTimer = object : CountDownTimer(15000, 1000) {
                        var i = 0
                        override fun onTick(millisUntilFinished: Long) {
                            i++
                            Call_Receive_Server.getIns().Sevecie()
                            if (i == 5) {

                                for (i in 0..Call_Receive_Server.getIns().hasmap!!.size - 1) {
                                    Call_Receive_Server.getIns().hasmap!![i].setStatus(0)
                                }
                            }
                            //mProgressBar.progress = i
                            if (i == 15) {
                                Call_Receive_Server.getIns().Sevecie()
                                progress_bucking!!.visibility = View.GONE
                            }
                        }

                        override fun onFinish() {
                            //Do what you want
                            i++
                            try {
                                dialog_disconnect!!.show()
                                tv_cancel.setText("Vui lòng kiểm tra kết nối của bạn")
                                button_cancel.setOnClickListener()
                                {
                                    dialog_disconnect!!.cancel()
                                }
                                progress_bucking!!.visibility = View.GONE
                            } catch (e: Exception) {
                            }
                            /*}*/
                        }
                    }
                    mCountDownTimer!!.start()
                }
            }
            else
            {
                dialog_error1!!.show()
                var tv_error=dialog_error1!!.findViewById(R.id.tv_error) as TextView
                var bnt_cancel=dialog_error1!!.findViewById(R.id.btn_cancel_error)

                tv_error.setText("Không thể lấy thời gian dự kiến vui lòng thử lại!")
                bnt_cancel.setOnClickListener()
                {
                    dialog_error1!!.cancel()
                }

            }
        }
        if (event.getTemp() == AllValue.bucking)
        {
            mCountDownTimer!!.cancel()
            dialog_disconnect!!.cancel()

            progress_bucking!!.visibility= View.GONE
            if(event.getService()!!.getResult()=="1")
            {
                //bucking success
            }
            else
            {
                dialog_error_bucking!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog_error_bucking!!.setContentView(R.layout.dialog_error)
                dialog_error_bucking!!.show()
                var text_err= dialog_error_bucking!!.findViewById(R.id.tv_error) as TextView
                var bnt_errrr= dialog_error_bucking!!.findViewById(R.id.btn_cancel_error) as Button
                text_err.setText("Đặt lịch không thành công vui lòng kiểm tra lại!")
                bnt_errrr.setOnClickListener()
                {
                    dialog_error_bucking!!.cancel()
                }
            }
        }
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

    fun showTimePickerDialog() {
        val callback = OnTimeSetListener { view, hourOfDay, minute ->
            //Xử lý lưu giờ và AM,PM
            var s = hourOfDay.toString() + ":" + minute
            var hourTam = hourOfDay
            time_bucking=hourOfDay.toString()+minute
            if (hourTam > 12)
                hourTam = hourTam - 12
            f_time=false
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

    public fun getDefaultInfor()
 {
     //lấy ngày hiện tại của hệ thống
     cal=Calendar.getInstance();
      var dft:SimpleDateFormat? = null;
     //Định dạng ngày / tháng /năm
     dft= SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
     var strDate:String=dft.format(cal!!.getTime());
     //hiển thị lên giao diện
     tv_date4!!.setText(strDate);
     //Định dạng giờ phút am/pm
     dft= SimpleDateFormat("hh:mm a",Locale.getDefault());
     var strTime:String=dft.format(cal!!.getTime());
     //đưa lên giao diện
     tv_time_bucket!!.setText(strTime);
     //lấy giờ theo 24 để lập trình theo Tag
     dft=  SimpleDateFormat("HH:mm",Locale.getDefault());
     tv_time_bucket!!.setTag(dft.format(cal!!.getTime()));
     //editCv.requestFocus();
     //gán cal.getTime() cho ngày hoàn thành và giờ hoàn thành
     dateFinish=cal!!.getTime();
     hourFinish=cal!!.getTime();
 }
}