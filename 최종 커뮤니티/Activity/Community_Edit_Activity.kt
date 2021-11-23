package com.example.logo.Community

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.view.isVisible
import com.example.logo.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

class Community_Edit_Activity : AppCompatActivity() {

    private lateinit var key:String

    private lateinit var writerUid : String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_community_edit)

        val editbtn = findViewById<Button>(R.id.editbtn)

        //key를 통해 해당 게시물에 접근
        key = intent.getStringExtra("key").toString()
        getBoardData(key)

        editbtn.setOnClickListener{
            editBoardData(key)
        }
    }

    //수정구현
    private fun editBoardData(key : String){

        val titleArea = findViewById<EditText>(R.id.titleArea)
        val contentArea = findViewById<EditText>(R.id.contentArea)

        //수정하고자 하는 게시글에 접근해 수정
        FBrtbe.noticeboard
            .child(key)
            .setValue(
                Model(titleArea.text.toString(),
                    contentArea.text.toString(),
                    writerUid,
                    FBAuth.getTime())
            )

        Toast.makeText(this, "수정완료", Toast.LENGTH_LONG).show()

        finish()

    }

    //클릭한 게시글의 정보를 받아옴
    private fun getBoardData(key : String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val titleArea = findViewById<EditText>(R.id.titleArea)
                val contentArea = findViewById<EditText>(R.id.contentArea)

                val dataModel = dataSnapshot.getValue(Model::class.java)

                    titleArea.setText(dataModel?.title)
                    contentArea.setText(dataModel?.content)
                    writerUid = dataModel!!.uid

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBrtbe.noticeboard.child(key).addValueEventListener(postListener)
    }
}