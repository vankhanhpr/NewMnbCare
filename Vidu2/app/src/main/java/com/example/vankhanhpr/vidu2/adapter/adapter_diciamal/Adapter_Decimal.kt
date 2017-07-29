package com.example.vankhanhpr.vidu2.adapter.adapter_diciamal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.getter_setter.dicimal_getset.Schedule

/**
 * Created by VANKHANHPR on 7/18/2017.
 */
class Adapter_Decima(context:Context,schedule:ArrayList<Schedule>):BaseAdapter()
{
    private var mInflator: LayoutInflater
    private  var schedule:ArrayList<Schedule>?=null
    init
    {
        this.mInflator = LayoutInflater.from(context)
        this.schedule=schedule
        /*this.mInflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater*/
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var holder: ViewHolder
        val view: View?

        if(convertView == null){
            view = mInflator.inflate(R.layout.layout_listview_medical,parent,false) //error in this line
            holder = ViewHolder(view)
            view.tag=holder
        }
        else
        {
            view=convertView
            holder = convertView.tag as ViewHolder
        }
        holder.tv_doctor_name!!.text = schedule!![position].getC4()
        holder.tv_time!!.text =  schedule!![position].getC10()!!.substring(0,2)+"h:"+schedule!![position].getC10()!!.substring(2)
        holder.tv_mon_baby_name!!.text = schedule!![position].getC7()
        holder.tv_status!!.text =""+ if(schedule!![position].getC11().toString()=="N")"Chờ khám" else
            if(schedule!![position].getC11().toString()=="Y")"Hoàn tất"
            else if(schedule!![position].getC11().toString()=="D") "Hủy lịch đặt do người dùng"
            else "Hủy lịch đặt hệ thống"
        return view
    }

    override fun getItem(position: Int): Schedule {
        return schedule!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return schedule!!.size
    }

    private class ViewHolder(row: View?) {
        var tv_time:TextView? = null
        var tv_doctor_name:TextView? = null
        var tv_mon_baby_name:TextView? = null
        var tv_status:TextView? = null

        init {
            this.tv_time = row?.findViewById(R.id.time_sche) as TextView
            this.tv_doctor_name = row?.findViewById(R.id.doctor_name) as TextView
            this.tv_mon_baby_name = row?.findViewById(R.id.mom_baby_name) as TextView
            this.tv_status = row?.findViewById(R.id.status) as TextView

        }
    }
}