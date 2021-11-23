package com.example.logo.Community

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.logo.Community.Comment.Comment
import com.example.logo.Community.Comment.CommentModel
import com.example.logo.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception

//main에서 하지않은 수정 삭제 게시글자세히 보기 구현
class Community_Deatil_PostActivity : AppCompatActivity() {

    private lateinit var key:String
    private val mDatabase = FirebaseDatabase.getInstance()
    private val commentReference = mDatabase.getReference("comment") // 댓글 트리 경로

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_details_post)

        val title_et = findViewById<TextView>(R.id.title_et)
        val content_et = findViewById<TextView>(R.id.content_et)
        val commentTextView = findViewById<TextView>(R.id.commentTextView)

        title_et.setText(intent.getStringExtra("title").toString())
        content_et.setText(intent.getStringExtra("content").toString())

        commentTextView.setOnClickListener {
            val intentComment = Intent(this, Comment::class.java)
            intentComment.putExtra("id", intent.getStringExtra("id"))
            intentComment.putExtra("cid", intent.getStringExtra("cid"))
            startActivity(intentComment)
        }

        //key를 통해 게시글 확인
        key = intent.getStringExtra("key").toString()
        getBoardData(key)

        val settingBtn: View = findViewById(R.id.boardSettingIcon)
        settingBtn.setOnClickListener{
            showDialog()
        }
    }

    //게시글 수정 and 삭제 선택 함수, dialog를 통해 구성
    private fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.community_setting_btninside, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editbtn)?.setOnClickListener {
            Toast.makeText(this, "수정 버튼을 눌렀습니다", Toast.LENGTH_LONG).show()

            //intent를 통해 수정부분의 경우 edit_activity로 넘김 => 이때 key를 통해 넘겨줌
            val Intent = Intent(this, Community_Edit_Activity::class.java)
            Intent.putExtra("key",key)
            startActivity(Intent)
            alertDialog.dismiss() //오류방지
            finish()
        }

        alertDialog.findViewById<Button>(R.id.removebtn)?.setOnClickListener {
            //해당 게시글 데이터베이스에서 삭제
            FBrtbe.noticeboard.child(key).removeValue()

            //게시글 삭제시 database의 그 안의 댓글 정보 삭제
            commentReference.addValueEventListener(object : ValueEventListener {  // 데베에서 데이터 읽어오기
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val dataRef = commentReference
                        .child(intent.getStringExtra("cid").toString())
                        dataRef.setValue(null) // 그것을 삭제

                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
            alertDialog.dismiss()
            finish()
        }
    }

    //게시글 정보를 가져옴 + 만약 자신의 글이 아닐경우 if문의 equals를 통해 수정 삭제 버튼이 보이지 않음
    private fun getBoardData(key : String){
        val postListener = object : ValueEventListener {

            val title_et = findViewById<TextView>(R.id.title_et)
            val content_et = findViewById<TextView>(R.id.content_et)

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try {

                    val dataModel = dataSnapshot.getValue(Model::class.java)

                    Log.d(TAG, dataModel!!.title)

                    title_et.text = dataModel!!.title
                    content_et.text = dataModel!!.content
                    val myUid = intent.getStringExtra("id").toString()
                    val writerUid = dataModel!!.uid
                    val settingbtn: ImageView = findViewById(R.id.boardSettingIcon)

                    if(myUid.equals(writerUid)){

                        Log.d(TAG, "내가 쓴 글")
                        settingbtn.isVisible = true

                    } else {
                        settingbtn.isVisible = false
                        Log.d(TAG, "내가 쓴 글 아님")
                    }

                } catch (e : Exception){
                    Log.d(TAG, "삭제완료")
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBrtbe.noticeboard.child(key).addValueEventListener(postListener)
    }
}
