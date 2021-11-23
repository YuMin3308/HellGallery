package com.example.logo.Community


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.logo.R

//Post activity => 게시글 작성부분 구현
class Community_PostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_post)

        //button 설정
        val postBtn = findViewById<Button>(R.id.postbBtn)
        val titleArea = findViewById<EditText>(R.id.titleArea)
        val contentArea = findViewById<EditText>(R.id.contentArea)

        //게시글 저장 버튼시 firebase 업로드 부분 구현
        postBtn.setOnClickListener {
            val title = titleArea.text.toString()
            val content = contentArea.text.toString()
            val uid = intent.getStringExtra("id").toString() // 사용자 이메일로
            val time = FBAuth.getTime()
            val pushed = FBrtbe.noticeboard.push()
            val cid = pushed.key.toString()

            //해당 key값을 push()
            val key = FBrtbe.noticeboard.push().key.toString()

            //key를 통해 noticeboard에 해당 게시글 저장.
            FBrtbe.noticeboard
                  .child(key)
                  .setValue(Model(title, content, uid, time, cid))

            //완료후 Toast메시지 출력
             Toast.makeText(this, "게시글 입력완료!", Toast.LENGTH_LONG).show()

             finish()
        }
    }
}