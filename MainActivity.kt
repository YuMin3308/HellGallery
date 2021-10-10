package com.example.exercisekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var expLvTv=findViewById(R.id.expLvTv) as TextView //경험치창
        var beginBtn:ImageButton = findViewById(R.id.beginBtn) //begin난이도 버튼
        var normalBtn:ImageButton = findViewById(R.id.normalBtn) //normal난이도 버튼
        var hardBtn:ImageButton = findViewById(R.id.hardBtn) //hard난이도 버튼

        beginBtn.setOnClickListener{
            val intent = Intent(this,BeginActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
            startActivity(intent)
        }

        normalBtn.setOnClickListener{
            val intent = Intent(this,NormalActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
            startActivity(intent)
        }

        hardBtn.setOnClickListener{
            val intent = Intent(this,HardActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
            startActivity(intent)
        }
    }
}