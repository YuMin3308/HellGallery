package com.example.logo.Community

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.logo.R

//커뮤니티의 어뎁터 부분
class CmListAdapter(val cmList : MutableList<Model>) : BaseAdapter() {

    override fun getCount(): Int {
        return cmList.size
    }

    override fun getItem(position: Int): Any {
        return cmList[position]
    }

    fun getTitle(position: Int) : String { // 유민_추가 리스트뷰 타이틀 이름 반환
        return cmList[position].title
    }

    fun getContent(position: Int) : String { // 유민_추가 리스트뷰 내용 반환
        return cmList[position].content
    }

    fun getCid(position: Int) : String {
        return cmList[position].cid
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