package com.example.vankhanhpr.vidu2.adapter.adapter_diciamal

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.Doctor
import com.example.vankhanhpr.vidu2.getter_setter.mom_baby.File_Mom_Baby

/**
 * Created by VANKHANHPR on 7/21/2017.
 */
class Adapter_File_Mom_Baby (context: Context,list_File:ArrayList<File_Mom_Baby>):BaseAdapter()
{
    private var mInflator: LayoutInflater
    private  var list_File:ArrayList<File_Mom_Baby>?=null
    init
    {
        this.mInflator = LayoutInflater.from(context)
        this.list_File = list_File
        /*this.mInflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater*/
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {

        var holder: ViewHolder
        val view: View?

        if(convertView == null){
            view = mInflator.inflate(R.layout.cardview_listview_file_mom_baby,parent,false) //error in this line
            holder = ViewHolder(view)
            view.tag=holder
        }
        else
        {
            view=convertView
            holder = convertView.tag as ViewHolder
        }
        holder.tv_name_relationship!!.text = list_File!![position].getC1()
        holder.tv_name_ralitives!!.text = list_File!![position].getC3()
        return view
    }

    override fun getItem(position: Int): File_Mom_Baby {
        return list_File!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int
    {
        return list_File!!.size
    }

    private class ViewHolder(row: View?) {
        var tv_name_relationship: TextView? = null
        var tv_name_ralitives: TextView? = null

        init {
            this.tv_name_relationship = row?.findViewById(R.id.tv_name_ralationship) as TextView
            this.tv_name_ralitives = row?.findViewById(R.id.tv_name_ralatives) as TextView
        }
    }

}