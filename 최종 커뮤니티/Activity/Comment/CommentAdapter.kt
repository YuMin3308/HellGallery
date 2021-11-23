package com.example.logo.Community.Comment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.logo.R

class CommentAdapter(val CommentList : MutableList<CommentModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return CommentList.size
    }

    override fun getItem(position: Int): Any {
        return CommentList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun getCid(position: Int) : String {
        return CommentList[position].cid
    }

    fun getEmail(position : Int) : String {
        return CommentList[position].uid
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view = convertView

        if (view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.comment_item_list, parent,false)
        }

        val email = view?.findViewById<TextView>(R.id.userEmail)
        val content = view?.findViewById<TextView>(R.id.commentArea)
        val time = view?.findViewById<TextView>(R.id.timeAreaComment)

        email!!.text = CommentList[position].uid
        content!!.text = CommentList[position].content
        time!!.text = CommentList[position].time

        return view!!
    }
}
