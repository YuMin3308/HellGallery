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
import com.google.firebase.database.ValueEventListener
import java.lang.Exception


class Community_Deatil_PostActivity : AppCompatActivity() {

    private lateinit var key:String

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

        key = intent.getStringExtra("key").toString()
        getBoardData(key)

        val settingBtn: View = findViewById(R.id.boardSettingIcon)
        settingBtn.setOnClickListener{
            showDialog()
        }
    }

    private fun showDialog(){
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.community_setting_btninside, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("게시글 수정/삭제")

        val alertDialog = mBuilder.show()
        alertDialog.findViewById<Button>(R.id.editbtn)?.setOnClickListener {
            Toast.makeText(this, "수정 버튼을 눌렀습니다", Toast.LENGTH_LONG).show()

            val Intent = Intent(this, Community_Edit_Activity::class.java)
            Intent.putExtra("key",key)
            startActivity(Intent)
        }

        alertDialog.findViewById<Button>(R.id.removebtn)?.setOnClickListener {
            FBrtbe.noticeboard.child(key).removeValue()
            alertDialog.dismiss()
            finish()
        }
    }

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
