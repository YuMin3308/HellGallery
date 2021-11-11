package com.example.logo.Community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.logo.R

class CmListAdapter(val cmList : MutableList<Model>) : BaseAdapter() {

    override fun getCount(): Int {
        return cmList.size
    }

    override fun getItem(position: Int): Any {
        return cmList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.community_item_list, parent,false)
        }

        val title = view?.findViewById<TextView>(R.id.titleArea)
        val content = view?.findViewById<TextView>(R.id.contentArea)
        val time = view?.findViewById<TextView>(R.id.timeArea)

        title!!.text = cmList[position].title
        content!!.text = cmList[position].content
        time!!.text = cmList[position].time

        return view!!
    }
}