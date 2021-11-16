package com.example.logo.Community


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.logo.R


class Community_PostActivity : AppCompatActivity() {

    private val TAG = Community_PostActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_post)

        val postBtn = findViewById<Button>(R.id.postbBtn)
        val titleArea = findViewById<EditText>(R.id.titleArea)
        val contentArea = findViewById<EditText>(R.id.contentArea)

        postBtn.setOnClickListener {
            val title = titleArea.text.toString()
            val content = contentArea.text.toString()
            val uid = intent.getStringExtra("id").toString() // 사용자 이메일로
            val time = FBAuth.getTime()
            val pushed = FBrtbe.noticeboard.push()
            val cid = pushed.key.toString()

            val key = FBrtbe.noticeboard.push().key.toString()

            FBrtbe.noticeboard
                  .child(key)
                  .setValue(Model(title, content, uid, time, cid))

             Toast.makeText(this, "게시글 입력완료!", Toast.LENGTH_LONG).show()

             finish()
        }
    }
}