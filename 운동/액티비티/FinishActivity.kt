package com.example.exercisekt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)
        var rtMainBtn :Button=findViewById(R.id.rtMainBtn)
        var endIv: ImageView=findViewById(R.id.endIv)
        endIv.setImageResource(R.raw.fireflower)
        Glide.with(this).load(R.raw.fireflower).override(1000, 1000).into(endIv)//gif형식 파일 사용
        rtMainBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
            startActivity(intent)
        }
    }
}