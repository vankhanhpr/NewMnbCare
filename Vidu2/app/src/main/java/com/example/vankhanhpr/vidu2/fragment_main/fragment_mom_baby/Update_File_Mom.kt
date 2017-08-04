package com.example.vankhanhpr.vidu2.fragment_main.fragment_mom_baby

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.getter_setter.AllValue
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.File_Mom_Baby
import com.google.gson.Gson
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.widget.*
import com.example.vankhanhpr.vidu2.call_receive_service.Call_Receive_Server
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.json.MessageEvent
import kotlinx.android.synthetic.main.mom_update_file.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Created by VANKHANHPR on 7/21/2017.
 */
class Update_File_Mom:AppCompatActivity()
{
    var file_mom:File_Mom_Baby?=null
    var edt_name_customer:EditText?=null
   /* var spinner_relationship:Spinner?=null
    var spinner_sex:Spinner?=null*/
    var edt_address:EditText?=null
    var edt_phonenumber:EditText?=null
    var edt_email_cus:EditText?=null
    var bnt_save_file:Button?=null
    var tv_date:TextView?=null
    var date1: String? = ""
    var namemom1: String? = null
    var address1: String? = null
    var phone1: String? = null
    var email1: String? = null
    var ralationship:String?=""
    var sex:String?=""
    var call=Call_Receive_Server.getIns()
    var dialog_notication:Dialog?=null
    var dialog_success:Dialog?=null
    var dialog_disconnect: Dialog? = null
    var mCountDownTimer: CountDownTimer? = null
    var tab_insert_file:ProgressBar?=null
    var disconnect:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mom_update_file)
        EventBus.getDefault().register(this)
        try {
            edt_name_customer = findViewById(R.id.edt_name_customer) as EditText

            edt_address = findViewById(R.id.edt_address) as EditText
            edt_phonenumber = findViewById(R.id.edt_phonenumber) as EditText
            edt_email_cus = findViewById(R.id.edt_email_cus) as EditText
            bnt_save_file = findViewById(R.id.bnt_save_file) as Button
            disconnect=findViewById(R.id.disconnect)as TextView

            //spinner_relationship = findViewById(R.id.spinner_relationship) as Spinner
            //spinner_sex = findViewById(R.id.spinner_sex) as Spinner
            tv_date = findViewById(R.id.tv_date) as TextView

            tab_insert_file = findViewById(R.id.tab_insert_file) as ProgressBar




            var inte: Intent = intent
            var bundle: Bundle = inte.getBundleExtra(AllValue.key_bundle)
            var x = bundle.getString(AllValue.value)
            var gson = Gson()
            file_mom = gson.fromJson(x, File_Mom_Baby::class.java)

            edt_name_customer!!.setText(file_mom!!.getC3())
            edt_name_customer!!.setSelection(file_mom!!.getC3()!!.length)
            edt_address!!.setText(file_mom!!.getC7())
            edt_phonenumber!!.setText(file_mom!!.getC8())
            edt_email_cus!!.setText(file_mom!!.getC9())
            tv_date!!.setText(file_mom!!.getC6()!!.substring(0, 2) + "/" + file_mom!!.getC6()!!.substring(2, 4) + "/" + file_mom!!.getC6()!!.substring(4))


           /* if(file_mom!!.getC0()!! == "2") {

                var adapter_rel = ArrayAdapter.createFromResource(this,
                        R.array.relationship_mom, android.R.layout.simple_spinner_item)
                adapter_rel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                var adapter_sex = ArrayAdapter.createFromResource(this,
                        R.array.sex_mom, android.R.layout.simple_spinner_item)
                adapter_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_relationship!!.adapter = adapter_rel
                spinner_sex!!.adapter = adapter_sex
            }
            if(file_mom!!.getC0()!! == "3")
            {
                var adapter_rel = ArrayAdapter.createFromResource(this,
                        R.array.relationship_baby, android.R.layout.simple_spinner_item)
                adapter_rel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                var adapter_sex = ArrayAdapter.createFromResource(this,
                        R.array.sex_arrays, android.R.layout.simple_spinner_item)
                adapter_sex.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner_relationship!!.adapter = adapter_rel
                spinner_sex!!.adapter = adapter_sex

                val options = ArrayList<String>()
                spinner_sex!!.setSelection(options.indexOf("Nữ"))
            }*/

           /* spinner_relationship!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                    ralationship = parentView.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    var r = 0
                }
            })
            spinner_sex!!.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View?, position: Int, id: Long) {
                    sex = parentView.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parentView: AdapterView<*>) {
                    var r = 0
                }
            })
*/



            imv_calendar.setOnClickListener()
            {
                showDatePickerDialog()
            }
            back_update_file_mom.setOnClickListener()
            {
                finish()
            }
            bnt_save_file!!.setOnClickListener()
            {
                tab_insert_file!!.visibility = View.VISIBLE
                dialog_notication = Dialog(this)
                dialog_success = Dialog(this)
                dialog_disconnect = Dialog(this)
                var dialogerror=Dialog(this)

                dialogerror.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialogerror.setContentView(R.layout.dialog_error)

                dialog_disconnect!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog_disconnect!!.setContentView(R.layout.dialog_error_password)
                var tv_cancel = dialog_disconnect!!.findViewById(R.id.tv_error) as TextView
                var button_cancel = dialog_disconnect!!.findViewById(R.id.btn_cancel_error_pass)

                namemom1 = edt_name_customer!!.text.toString()
                address1 = edt_address!!.text.toString()
                email1 = edt_email_cus!!.text.toString()
                phone1 = edt_phonenumber!!.text.toString()

                var res: String? = file_mom!!.getC0()
                var se: String? = file_mom!!.getC2()


                if(!(boolEmail(email1!!) && boolPhone(phone1!!)))
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

                var my_date = tv_date!!.text.toString()
                date1 = my_date.substring(0, 2) + my_date.substring(3, 5) + my_date.substring(6)
                var inval: Array<String> = arrayOf(Json.AppLoginID!!.toString(), file_mom!!.getC2()!!, res!!, namemom1!!, se!!, date1!!, address1!!, phone1!!, email1!!)
                Json.Operation = "U"
                call.CallEmit(AllValue.workername_update_file, AllValue.servicename_update_file, inval, AllValue.update_file_mombaby!!)
                Json.Operation = "Q"

                var i = 0
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
        }
        catch (e:Exception){}

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: MessageEvent) {
        if (event.getTemp() == AllValue.update_file_mombaby) {
            tab_insert_file!!.visibility = View.GONE
            mCountDownTimer!!.cancel()
            dialog_disconnect!!.cancel()
            if (event.getService()!!.getResult() == "1") {
                dialog_success!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog_success!!.setContentView(R.layout.dialog_success)
                dialog_success!!.show()
                var btn_success = dialog_success!!.findViewById(R.id.btn_success) as Button
                var tv_error = dialog_success!!.findViewById(R.id.tv_error) as TextView
                tv_error.setText(event.getService()!!.getMessage())
                btn_success.setOnClickListener()
                {
                    sendToMain(1, AllValue.updateFile!!)
                }
            } else {
                dialog_notication!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog_notication!!.setContentView(R.layout.dialog_error)
                dialog_notication!!.show()
                var tv_error = dialog_notication!!.findViewById(R.id.tv_error) as TextView
                var btn_cancel_error = dialog_notication!!.findViewById(R.id.btn_cancel_error) as Button
                tv_error.setText(event.getService()!!.getMessage())
                btn_cancel_error.setOnClickListener()
                {
                    dialog_notication!!.cancel()
                }
            }
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

    fun showDatePickerDialog() {
        val callback = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            var dayOfMonthS:String?=""
            var monthOfYearS:String?=""
            if(dayOfMonth<10 )
            {
                dayOfMonthS="0"+dayOfMonth
            }
            else
            {
                dayOfMonthS=""+dayOfMonth
            }
            if(monthOfYear+1 < 10 )
            {
                monthOfYearS ="0"+(monthOfYear+1).toString()
            }
            else
            {
                monthOfYearS=""+(monthOfYear+1).toString()
            }
            tv_date!!.setText(dayOfMonthS.toString() + "/" + monthOfYearS  + "/" + year)


            //cal!!.set(year, monthOfYear, dayOfMonth)
            //dateFinish = cal!!.getTime()
        }
        var s = tv_date!!.text
        var strArrtmp = s.split("/")
        var ngay = 1
        var thang = 1
        var nam = 2012
        ngay = Integer.parseInt(strArrtmp[0])
        thang = Integer.parseInt(strArrtmp[1])
        nam = Integer.parseInt(strArrtmp[2])


        var pic = DatePickerDialog(this, callback, nam, thang-1, ngay)
        pic.setTitle("Chọn ngày sinh")
        pic.show()
    }

    fun sendToMain(value:Int,resultcode:Int) {
        var intent:Intent= getIntent();
        var bundle:Bundle =  Bundle();
        bundle.putInt(AllValue.value, value);
        intent.putExtra(AllValue.key_bundle, bundle);
        setResult(resultcode, intent); // phương thức này sẽ trả kết quả cho Activity1
        finish(); // Đóng Activity hiện tại
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