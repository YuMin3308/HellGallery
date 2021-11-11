package com.example.logo.Community

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.example.logo.MainListAdapter
import com.example.logo.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class Community_main : AppCompatActivity() {

    private val TAG = Community_main::class.java.simpleName

    private val boardDataList = mutableListOf<Model>()

    private lateinit var Adapter : CmListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_main)

        val listView = findViewById<ListView>(R.id.BoardlistView)
        Adapter = CmListAdapter(boardDataList)
        listView.adapter = Adapter

        val writeBtn : View = findViewById(R.id.writeBtn) //게시글 작성 버튼
        writeBtn.setOnClickListener{
            val intent = Intent(this, Community_PostActivity::class.java)
            startActivity(intent)
        }
        getFBBoardData()
    }
    private fun getFBBoardData(){
        val postListener = object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()

                for(dataModel in dataSnapshot.children){
                    Log.d(TAG, dataModel.toString())

                    val item = dataModel.getValue(Model::class.java)
                    boardDataList.add(item!!)
                }
                Adapter.notifyDataSetChanged()

                Log.d(TAG, boardDataList.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBrtbe.noticeboard.addValueEventListener(postListener)
    }
}