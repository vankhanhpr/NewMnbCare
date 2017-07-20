package com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Window
import android.widget.*
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.json.MessageEvent
import kotlinx.android.synthetic.main.mom_create_file.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.github.ik024.calendar_lib.custom.MonthView
import com.github.ik024.calendar_lib.listeners.MonthViewClickListeners
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by VANKHANHPR on 7/19/2017.
 */

class Create_File_Mon:AppCompatActivity(),MonthViewClickListeners {
    override fun dateClicked(dateClicked: Date?) {

        var s: DateFormat = SimpleDateFormat("MM/dd/yyyy")
        date=s.format(dateClicked)
        Log.d("date",date)
        tv_date!!.setText(date)
        dialog!!.cancel()
    }
    var call = Call_Receive_Server.getIns()
    var radioGroup: RadioGroup? = null
    var tv_date:TextView?=null

    var radioButton: RadioButton? = null
    var btnDisplay: Button? = null
    var calendar_month_view: MonthView? = null
    var date: String? = ""
    var relationship: Int? = 1
    var name: String? = null
    var address: String? = null
    var phone: String? = null
    var email: String? = null
    var sex:String?=null
    var dialog:Dialog?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        setContentView(R.layout.mom_create_file)
        tv_date= findViewById(R.id.tv_date) as TextView

        btn_create_fiel_mom.setOnClickListener()
        {
            name = edt_name_customer.text.toString()
            address = edt_address.text.toString()
            phone = edt_phonenumber.text.toString()
            email = edt_email_cus.text.toString()
            addListenerOnButton_Relationship()
            addListenerOnButton_Sex()
            var date_string:String?=""
            if(date!!.length>0)
            {
                date_string= date!!.substring(6)+date!!.subSequence(3,5)+date!!.subSequence(0,2)

            }

             Json.Operation="I"
            var inval: Array<String> = arrayOf(Json.AppLoginID,relationship.toString(),name!!,sex!!,date_string!!,address!!,phone!!,email!!)

            call.CallEmit(AllValue.workername_create_file_mom, AllValue.servicename_create_file_mom, inval, AllValue.insert_file_mom_baby!!)
            Json.Operation="Q"
        }
        imv_calendar.setOnClickListener()
        {
            dialog = Dialog(this)
            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.setContentView(R.layout.calendar_dialog)
            calendar_month_view = dialog!!.findViewById(R.id.calendar_month_view) as MonthView
            dialog!!.show()
            calendar_month_view!!.registerClickListener(this)
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getTemp() == AllValue.insert_file_mom_baby)
        {

        }
            /*Json.Operation = "I"
        var inval: Array<String> = arrayOf("tan.vuong", Json.AppLoginID, "0000000007", "20170719", "1420", "1430")
        //call.CallEmit(AllValue.workername_put_bucket, AllValue.servicename_put_bucket,inval, "text3")
        Json.Operation = "Q"*/
    }

    fun addListenerOnButton_Relationship() {

        radioGroup = findViewById(R.id.radio_relationaship) as RadioGroup
        var item: Int = radioGroup!!.checkedRadioButtonId
        when (item) {
            R.id.me -> {
                relationship= 1
            }
            R.id.wife -> {
                relationship= 2
            }
            R.id.sonny -> {
                relationship= 3
            }

        }
    }
    fun addListenerOnButton_Sex() {

        var radio_relati = findViewById(R.id.radio_sex) as RadioGroup
        var item: Int = radio_relati!!.checkedRadioButtonId
        when (item) {
            R.id.radioMale -> {
                sex="M"
            }
            R.id.radioFemale -> {
                sex="F"
            }
        }
    }
}