package com.example.vankhanhpr.vidu2.fragment_main.fragment_medical.spinner

import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.example.vankhanhpr.vidu2.R
import com.example.vankhanhpr.vidu2.fragment_main.Fragment_Medical
import com.example.vankhanhpr.vidu2.getter_setter.Json
import com.example.vankhanhpr.vidu2.myinterface.IFilter_Scheduler


/**
 * Created by VANKHANHPR on 8/1/2017.
 */
class MyAdapter(private val mContext: Context, resource: Int, objects: ArrayList<StateVO>,interfa:IFilter_Scheduler) : ArrayAdapter<StateVO>(mContext, resource, objects){
    private var listState: ArrayList<StateVO>
    private var myAdapter: MyAdapter
    private var isFromView = false
    var possi:Int?=0
    var interfa:IFilter_Scheduler?=null

    init
    {
        this.interfa=interfa
        this.listState = objects
        this.myAdapter = this
    }

    override fun getDropDownView(position: Int, convertView: View?,parent: ViewGroup): View? {
        return getCustomView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return getCustomView(position, convertView, parent)
    }

    fun getCustomView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var convertView = convertView

        var holder: ViewHolder
        if (convertView == null) {
            var layoutInflator = LayoutInflater.from(mContext)
            convertView = layoutInflator.inflate(R.layout.bucker_spinner_item, null)
            holder = ViewHolder()
            holder.mTextView = convertView!!.findViewById(R.id.text) as TextView
            holder.mCheckBox = convertView.findViewById(R.id.checkbox) as CheckBox
            convertView.tag = holder
        }
        else {
            holder = convertView.tag as ViewHolder
        }

        holder.mTextView!!.text = listState[position].getTitle()
        isFromView = true
        try {
            //holder.mCheckBox!!.isChecked = listState[position].isSelected()
        }catch (E:Exception){}

        isFromView = false

        if (position == 0  ) {
            holder.mCheckBox!!.visibility = View.INVISIBLE
        } else {
            holder.mCheckBox!!.visibility = View.VISIBLE
        }
        holder.mCheckBox!!.tag = position

        holder.mCheckBox!!?.setOnCheckedChangeListener (object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                var getPosition = buttonView!!.getTag() as Int
                possi=position

                var chec1: CheckBox? = null
                var chec2: CheckBox? = null
                var chec3: CheckBox? = null
                var chec4:CheckBox?=null

                chec1 = parent.getChildAt(1).findViewById(R.id.checkbox) as CheckBox
                chec2 = parent.getChildAt(2).findViewById(R.id.checkbox) as CheckBox
                chec3 = parent.getChildAt(3).findViewById(R.id.checkbox) as CheckBox
                chec4 = parent.getChildAt(4).findViewById(R.id.checkbox) as CheckBox

                if(getPosition == 4) //kich vao tat ca
                {
                    Log.d("khanh122323","vitri cuoi tren:"+position)
                    if(Json.filter4==false)
                    {
                        Json.filter4=true
                        return
                    }
                    Log.d("khanh122323","vitri cuoi duoi:"+position)
                    Json.filter=false
                    if(isChecked)
                    {
                        Log.d("khanh122323","vitri cuoi duoi dung :"+position)
                        listState[getPosition].setSelected(true)
                        interfa!!.Call_filter(listState)//filter lại danh sách

                        chec1.isChecked=true
                        chec2.isChecked=true
                        chec3.isChecked=true
                        for (i in 1..parent.childCount - 1)
                        {
                            listState[i].setSelected(true)
                        }

                    }
                    else if(!isChecked)
                    {
                        listState[getPosition].setSelected(false)

                        chec1.isChecked=false
                        chec2.isChecked=false
                        chec3.isChecked=false
                        for (i in 1..parent.childCount - 1)
                        {
                            if(listState[i].isSelected())
                            {
                                listState[i].setSelected(false)
                            }
                        }
                        Log.d("khanh122323","call filter:")
                        interfa!!.Call_filter(listState)//filter lại danh sách
                    }
                }
                    //----------------------------------------------------------------------------------
                else//kich vao cac vi tri khac
                if(position!=0)
                {
                    var temr=0
                    var temt=0
                    if(Json.filter==false)//kiem tra lan kich tiep theo
                    {
                        for(j in 1..listState.size-1)
                        {
                            if(listState.get(j).isSelected()==true)
                            {
                                temr++
                            }
                        }
                        for(j in 1..listState.size-1)
                        {
                            if(listState.get(j).isSelected()==false)
                            {
                                temt++
                            }
                        }
                        Json.number  = Json.number!! + 1
                        if(listState.get(4).isSelected())
                        {
                            if (Json.number == temt)
                            {
                                Json.filter = true
                                Json.number = 0
                            }
                        }
                        else
                        if(!listState.get(4).isSelected()) {
                            if (Json.number == temr)
                            {
                                Json.filter = true
                                Json.number = 0
                            }
                        }
                        temr=0
                        temt=0
                        return
                    }
                    else
                    if(!isChecked)
                    {
                        listState.get(position).setSelected(false)
                        if(chec4.isChecked==true)
                        {
                            Log.d("khanh122323","truong hop dac biet:"+position)
                            Json.filter4=false
                            chec4.isChecked=false
                            listState.get(4).setSelected(false)
                        }
                        else
                        {
                            Json.filter4=true
                        }
                        //Log.d("khanh122323","call filter:")
                        interfa!!.Call_filter(listState)//filter lại danh sách
                    }//click vao
                    else{
                        listState.get(position).setSelected(true)
                        Log.d("khanh122323","chua chon:"+position)
                        var fl=true
                        for(k in 1..3)
                        {
                            if(!listState.get(k).isSelected())
                            {
                                fl=false
                            }
                        }
                        if(fl)
                        {
                            interfa!!.Call_filter(listState)//filter lại danh sách
                            Json.filter4=false
                            chec4.isChecked=true
                            listState.get(4).setSelected(true)
                        }
                        else {
                            Json.filter4 = true
                        }
                        Log.d("khanh122323","filter theo :"+position)
                        interfa!!.Call_filter(listState)//filter lại danh sách
                    }
                }
            }
        })
        return convertView
    }

    private inner class ViewHolder
    {
        var mTextView: TextView? = null
        var mCheckBox: CheckBox? = null
    }
}