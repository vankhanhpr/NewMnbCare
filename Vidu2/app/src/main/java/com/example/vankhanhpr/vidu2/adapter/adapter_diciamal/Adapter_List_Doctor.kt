package com.example.vankhanhpr.vidu2.adapter.adapter_diciamal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.Doctor
import com.example.vankhanhpr.vidu2.adapter.adapter_diciamal.Adapter_List_Doctor.ValueFilter



/**
 * Created by VANKHANHPR on 7/20/2017.
 */
public class Adapter_List_Doctor(context: Context,listDoctor:ArrayList<Doctor>):BaseAdapter(),Filterable
{
    private var mInflator: LayoutInflater
    private  var doctor:ArrayList<Doctor>?=null
    var mStringFilterList:ArrayList<Doctor>?=null
    private var valueFilter: ValueFilter = ValueFilter()

    init
    {
        this.mInflator = LayoutInflater.from(context)
        this.doctor = listDoctor
        this.mStringFilterList= listDoctor
        /*this.mInflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater*/
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var holder: ViewHolder
        val view: View?

        if(convertView == null){
            view = mInflator.inflate(R.layout.cardview_in_listview_file_doctor,parent,false) //error in this line
            holder = ViewHolder(view)
            view.tag=holder
        }
        else
        {
            view=convertView
            holder = convertView.tag as ViewHolder
        }
        holder.tv_time_doctor!!.text = doctor!![position].getC1()
        holder.tv_palce_work!!.text = doctor!![position].getC7()
        holder.tv_address_doctor!!.text = doctor!![position].getC6()
        holder.tv_contact!!.text = doctor!![position].getC3()
        return view
    }

    override fun getItem(position: Int): Doctor {
        return doctor!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return doctor!!.size
    }

    private class ViewHolder(row: View?) {
        var tv_time_doctor: TextView? = null
        var tv_palce_work: TextView? = null
        var tv_address_doctor: TextView? = null
        var tv_contact: TextView? = null

        init {
            this.tv_time_doctor = row?.findViewById(R.id.tv_name_doctor) as TextView
            this.tv_palce_work = row?.findViewById(R.id.tv_place_work) as TextView
            this.tv_address_doctor = row?.findViewById(R.id.tv_adddresss_doctor) as TextView
            this.tv_contact = row?.findViewById(R.id.tv_contact) as TextView

        }
    }

    override fun getFilter(): Filter {
        if (valueFilter == null) {
            valueFilter = ValueFilter()
        }
        return valueFilter
    }
    var s=0
    inner class  ValueFilter : Filter()
    {
        override fun performFiltering(constraint: CharSequence?): Filter.FilterResults
        {
            var results = Filter.FilterResults()

            if (constraint != null && constraint.length > 0)
            {
                var filterList:ArrayList<Doctor>  = ArrayList()

                for (i in 0..mStringFilterList!!.size - 1)
                {
                    var doct:Doctor=mStringFilterList!!.get(i)

                    if (doct!!.getC1()!!.toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
                        filterList.add(mStringFilterList!!.get(i))
                    }
                }
                results.count = filterList.size
                results.values = filterList
            }
            else
            {
                results.count = mStringFilterList!!.size
                results.values = mStringFilterList
            }
            return results

        }
        override fun publishResults(constraint: CharSequence,results: Filter.FilterResults) {
            doctor = results.values as ArrayList<Doctor>
            notifyDataSetChanged()
        }

    }
}