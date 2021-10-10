package com.example.exercisekt

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.util.*
import kotlin.concurrent.timer


class NormalActivity : AppCompatActivity() {

    var time =0// 시간제어
    var timerTask : Timer?=null//시간제어
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal)

        var exrSet = 0 //운동 종류 제어자

        var exrIv2: ImageView = findViewById(R.id.exrIv2)//운동 이미지
        var nxtBtn2: Button = findViewById(R.id.nxtBtn2)//다음 운동 버튼
        var exrNameTv2 = findViewById(R.id.exrNameTv2) as TextView

        exrIv2.setImageResource(R.raw.jumpingjack)//뷰에 이미지 세트
        Glide.with(this).load(R.raw.jumpingjack).override(3000, 4000).into(exrIv2)//gif형식 파일 사용

        nxtBtn2.setOnClickListener {
            exrSet += 1
            if (exrSet == 1) {//1일때 푸쉬업
                exrNameTv2.setText("푸쉬업")
                exrIv2.setImageResource(R.raw.pushup)
                Glide.with(this).load(R.raw.pushup).override(3000, 4000).into(exrIv2)//gif형식 파일 사용
            } else if (exrSet == 2) {//2일때 니업
                exrNameTv2.setText("니업")
                exrIv2.setImageResource(R.raw.kneeup)
                Glide.with(this).load(R.raw.kneeup).override(3000, 4000).into(exrIv2)//gif형식 파일 사용
            } else if (exrSet == 3) { //3일때 레그레이즈
                exrNameTv2.setText("레그레이즈")
                exrIv2.setImageResource(R.raw.legrais)
                Glide.with(this).load(R.raw.legrais).override(3000, 4000).into(exrIv2)//gif형식 파일 사용
            } else if (exrSet == 4) { //4일때 런지
                exrNameTv2.setText("런지")
                exrIv2.setImageResource(R.raw.lunge)
                Glide.with(this).load(R.raw.lunge).override(3000, 4000).into(exrIv2)//gif형식 파일 사용
            } else if (exrSet == 5) {//5일때 브릿지
                exrNameTv2.setText("브릿지")
                exrIv2.setImageResource(R.raw.bridge)
                Glide.with(this).load(R.raw.bridge).override(3000, 4000).into(exrIv2)//gif형식 파일 사용
            } else if (exrSet == 6) {//6일때 마운틴 클라이머
                exrNameTv2.setText("마운틴 클라이머")
                exrIv2.setImageResource(R.raw.mountainclimer)
                Glide.with(this).load(R.raw.mountainclimer).override(3000, 4000).into(exrIv2)//gif형식 파일 사용
            } else if (exrSet == 7) {
                exrNameTv2.setText("플랭크")
                Toast.makeText(this@NormalActivity, "3초뒤 시작합니다 자세를 잡으세요", Toast.LENGTH_SHORT).show()
                exrIv2.setImageResource(R.raw.planksideup)
                Handler().postDelayed({startTimer()},3000L)
                Handler().postDelayed(
                    {val intent = Intent(this,FinishActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
                    startActivity(intent)},35000L)
            }
        }
    }

    fun startTimer(){
        time=0
        timerTask= timer(period = 10){
            time++
            var txtTime2=findViewById(R.id.txtTime2) as TextView
            val sec =time/100
            val milli=time%100
            runOnUiThread {
                txtTime2?.text="${sec} : ${milli}"
                if(sec==30){
                    timerTask?.cancel()
                }
            }
        }
    }

}