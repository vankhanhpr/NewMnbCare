package com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.View
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
import java.util.*
import android.widget.ArrayAdapter
import android.widget.AdapterView.OnItemSelectedListener

import android.app.DatePickerDialog
import android.util.Log
import com.example.vankhanhpr.vidu2.fragment_main.fragment_bucking.BuckingMom
import com.example.vankhanhpr.vidu2.getter_setter.IsNumber
import org.json.JSONObject


/**
 * Created by VANKHANHPR on 7/19/2017.
 */

class Create_File_Mon:AppCompatActivity() {

    var call = Call_Receive_Server.getIns()
    var tv_date: TextView? = null
    var f:Boolean=true
    var flag:Int=2

    var relationship_spinner: Spinner? = null
    var sex_spinner: Spinner? = null
    var date: String? = ""
    var relationship: String? = null
    var name: String? = null
    var address: String? = null
    var phone: String? = null
    var email: String? = null
    var sex: String? = null
    var tab_insert_file: ProgressBar? = null
    var mCountDownTimer: CountDownTimer? = null
    var dialog_disconnect: Dialog? = null
    var dialog_notication: Dialog?= null
    var dialog_success:Dialog?=null
    var id_mom:String?=""
    var id_doctor:String?=""
    var flag_se:Boolean? = true
    var disconnect:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        setContentView(R.layout.mom_create_file)
        tv_date = findViewById(R.id.tv_date) as TextView
        tab_insert_file = findViewById(R.id.tab_insert_file) as ProgressBar
        relationship_spinner = findViewById(R.id.spinner_relationship) as Spinner
        sex_spinner = findViewById(R.id.spinner_sex) as Spinner
        disconnect=findViewById(R.id.disconnect)as TextView


        if(Json.bucking==0)
        {
            flag=0
        }
        if(Json.bucking==1)
        {
            flag=1
        }
        else
        {
            flag=2
        }


        if(flag!! == 2) {
            var adapter = ArrayAdapter.createFromResource(this, R.array.relationship_arrays, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            var adapter_sex = ArrayAdapter.createFromResource(this, R.array.sex_arrays, android.R.layout.simple_spinner_item)
            adapter_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            relationship_spinner!!.adapter = adapter
            sex_spinner!!.adapter = adapter_sex
        }
        if(flag!! == 0)
        {
            var adapter = ArrayAdapter.createFromResource(this, R.array.relationship_mom, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            var adapter_sex = ArrayAdapter.createFromResource(this, R.array.sex_mom, android.R.layout.simple_spinner_item)
            adapter_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            relationship_spinner!!.adapter = adapter
            sex_spinner!!.adapter = adapter_sex
        }
        if(flag!! == 1)
        {
            var adapter = ArrayAdapter.createFromResource(applicationContext, R.array.relationship_baby, android.R.layout.simple_spinner_item)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            var adapter_sex = ArrayAdapter.createFromResource(applicationContext, R.array.sex_arrays, android.R.layout.simple_spinner_item)
            adapter_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            relationship_spinner!!.adapter = adapter
            sex_spinner!!.adapter = adapter_sex
        }

        var adapter_sex5 = ArrayAdapter.createFromResource(applicationContext, R.array.sex_mom, android.R.layout.simple_spinner_item)
        adapter_sex5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        var adapter_sex6 = ArrayAdapter.createFromResource(applicationContext,R.array.sex_arrays, android.R.layout.simple_spinner_item)
        adapter_sex6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        relationship_spinner!!.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long)
            {
                relationship = parentView.getItemAtPosition(position).toString()
                if(!flag_se!!) {
                    if (flag!! == 2) {
                        if (relationship!! == "Vợ") {

                            sex_spinner!!.adapter = adapter_sex5
                        }
                        else {

                            sex_spinner!!.adapter = adapter_sex6
                        }
                    }
                }
                flag_se=false
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                var r = 0
            }
        })
        sex_spinner!!.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                sex = parentView.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parentView: AdapterView<*>) {
                var r = 0
            }
        })

        back_create_file_mom.setOnClickListener()
        {
            finish()
        }
        btn_create_fiel_mom.setOnClickListener()
        {
            dialog_disconnect = Dialog(this)
            dialog_notication= Dialog(this)
            dialog_success= Dialog(this)
            var dialogerror=Dialog(this)
            dialogerror.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogerror.setContentView(R.layout.dialog_error)

            dialog_success!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_success!!.setContentView(R.layout.dialog_success)

            dialog_disconnect!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog_disconnect!!.setContentView(R.layout.dialog_error_password)
            var tv_cancel = dialog_disconnect!!.findViewById(R.id.tv_error) as TextView
            var button_cancel = dialog_disconnect!!.findViewById(R.id.btn_cancel_error_pass)




            tab_insert_file!!.visibility = View.VISIBLE
            name = edt_name_customer.text.toString()
            address = edt_address.text.toString()
            phone = edt_phonenumber.text.toString()
            email = edt_email_cus.text.toString()

            //kiểm tra số điện thoại và email
            if(!(boolPhone(phone!!) && boolEmail(email!!)))
            {
                dialogerror.show()
                var text=dialogerror.findViewById(R.id.tv_error) as TextView
                var cancel=dialogerror.findViewById(R.id.btn_cancel_error)
                text.setText("Email hoặc số điện thoại không hợp lệ!")
                cancel.setOnClickListener()
                {
                    dialogerror.cancel()
                }
                tab_insert_file!!.visibility=View.GONE
                return@setOnClickListener
            }

            var i = 0
            date= tv_date!!.text.toString()
            var date_string: String? = ""
            if (date!!.length > 0) {date_string = date!!.substring(6) + date!!.subSequence(3, 5) + date!!.subSequence(0, 2)
            }
            var res: String? = "1"
            var se: String? = "M"
            when (relationship) {
                "Tôi" -> {
                    res = "1"
                }
                "Vợ" -> {

                    res = "2"
                }
                "Con" -> {
                    res = "3"
                }
            }
            true
            when (sex) {
                "Nam" -> {
                    se = "M"
                }
                "Nữ" -> {
                    se = "F"
                }
                "Khác" -> {
                    se = "O"
                }
            }
            true


            Json.Operation = "I"
            var inval: Array<String> = arrayOf(Json.AppLoginID, res!!, name!!, se!!, date_string!!, address!!, phone!!, email!!)
            call.CallEmit(AllValue.workername_create_file_mom, AllValue.servicename_create_file_mom, inval, AllValue.insert_file_mom_baby!!)
            Json.Operation = "Q"

            mCountDownTimer = object : CountDownTimer(10000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    i++
                    Call_Receive_Server.getIns().Sevecie()
                    if (i == 5) {
                        for (i in 0..Call_Receive_Server.getIns().hasmap!!.size - 1) {
                            Call_Receive_Server.getIns().hasmap!![i].setStatus(0)
                        }
                    }
                    if (i == 10) {
                        Call_Receive_Server.getIns().Sevecie()
                        tab_insert_file!!.visibility = View.GONE
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
                        tab_insert_file!!.visibility = View.GONE
                    } catch (e: Exception) {
                    }
                }
            }
            mCountDownTimer!!.start()
        }
         imv_calendar.setOnClickListener()//su dung clendar he thong
        {
            showDatePickerDialog()
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getTemp() == AllValue.insert_file_mom_baby) {
            tab_insert_file!!.visibility = View.GONE
            mCountDownTimer!!.cancel()
            dialog_disconnect!!.cancel()
            if(event.getService()!!.getResult()=="1")
            {
                dialog_success!!.show()
                var temp:IsNumber=readJson1(event.getService()!!.getData()!!)
                var btn_success= dialog_success!!.findViewById(R.id.btn_success) as Button
                var tv_error=dialog_success!!.findViewById(R.id.tv_error) as TextView
                tv_error.setText(event.getService()!!.getMessage())
                btn_success.setOnClickListener()
                {
                    if(flag==2)
                    {
                        sendToMain(1, AllValue.createFile!!)
                    }
                    else
                    {
                        sendToActivityBucking(id_doctor!!,temp!!.getSecC0()!!,1234)
                    }
                }
            }
            else
            {
                dialog_notication!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog_notication!!.setContentView(R.layout.dialog_error)
                dialog_notication!!.show()
                var tv_error= dialog_notication!!.findViewById(R.id.tv_error) as TextView
                var btn_cancel_error= dialog_notication!!.findViewById(R.id.btn_cancel_error) as Button
                tv_error.setText(event.getService()!!.getMessage())
                btn_cancel_error.setOnClickListener()
                {
                    dialog_notication!!.cancel()
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


    fun sendToMain(value:Int,resultcode:Int) {
        var intent:Intent= getIntent();
        var bundle:Bundle =  Bundle();
        bundle.putInt(AllValue.value, value);
        intent.putExtra(AllValue.key_bundle, bundle);
        setResult(resultcode, intent); // phương thức này sẽ trả kết quả cho Activity1
        finish(); // Đóng Activity hiện tại
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
            tv_date!!.setText(dayOfMonthS.toString() + "/" + monthOfYearS + "/" + year)


            //cal!!.set(year, monthOfYear, dayOfMonth)
            //dateFinish = cal!!.getTime()
        }
        var s = tv_date!!.text
        var strArrtmp = s.split("/")
        var ngay = 1
        var thang = 1
        var nam = 2012
        if(!f) {
            ngay = Integer.parseInt(strArrtmp[0])
            thang = Integer.parseInt(strArrtmp[1])
            nam = Integer.parseInt(strArrtmp[2])
        }
        if(f) {
                ngay = 1
                thang = 1
                nam = 2012

        }
        var pic = DatePickerDialog(this, callback, nam, thang-1, ngay)
        pic.setTitle("Chọn ngày sinh")
        pic.show()
    }
    fun sendToActivityBucking(value: String,value2:String,resultcode:Int) {
        var intent3 = Intent(applicationContext, BuckingMom::class.java)
        var bundle = Bundle()
        bundle.putString(AllValue.value, value)
        bundle.putString(AllValue.value2, value2)
        intent3.putExtra(AllValue.key_bundle, bundle)
        startActivityForResult(intent3,resultcode!!)
        finish()
    }
    // Đọc file Json để lấy kết quả
    fun readJson1(json1: ArrayList<JSONObject>): IsNumber
    {
        var jsonO: JSONObject?=null
        if(json1.size>0) {
            jsonO = json1[0]
        }
        var c0: String? =jsonO!!.getString("c0")
        var ser1 : IsNumber = IsNumber()
        ser1.setSecC0(c0!!)
        return ser1
    }
    //Kiểm tra số điện thoại
    fun boolPhone(phone2:String):Boolean {
        if(phone2=="")
        {
            return true
        }
        if (phone2.length == 10 && (phone2.substring(1,2) == "9" || phone2.substring(1,2) == "8" )) {
            return true
        }
        if (phone2.length == 11 && phone2.substring(1,2) == "1") {
            return true
        }
        return  false
    }
    //kiểm tra email
    fun boolEmail(email:String):Boolean
    {
        if(email=="")
        {
            return true
        }
        var tem=0
        for(i in 0..email.length-1)
        {
            if(email.get(i).toString() == "@")
            {
                tem++
            }
        }
        if(tem==1)
        {
            return true
        }
        return false
    }
}

