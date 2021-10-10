package com.example.logo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

//리스트 뷰 어댑터
class MainListAdapter(val context : Context, val mainList : ArrayList<MainList>) : BaseAdapter() {
    override fun getCount(): Int {
        return mainList.size
    }

    override fun getItem(position: Int): Any {
        return mainList[position]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.main_list_view,null)
        val img = view.findViewById<ImageView>(R.id.exerImgButton)
        val mainListVal = mainList[position]

        img.setImageResource(mainListVal.img)

        return view
    }

}
