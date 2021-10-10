package com.example.exercisekt

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

class HardActivity : AppCompatActivity() {
    var time =0// 시간제어
    var timerTask : Timer?=null//시간제어
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard)

        var exrSet = 0 //운동 종류 제어자

        var exrIv3: ImageView = findViewById(R.id.exrIv3)//운동 이미지
        var nxtBtn3: Button = findViewById(R.id.nxtBtn3)//다음 운동 버튼
        var exrNameTv3 = findViewById(R.id.exrNameTv3) as TextView

        exrIv3.setImageResource(R.raw.jumpingjack)//뷰에 이미지 세트
        Glide.with(this).load(R.raw.jumpingjack).override(3000, 4000).into(exrIv3)//gif형식 파일 사용

        nxtBtn3.setOnClickListener {
            exrSet += 1
            if (exrSet == 1) {//1일때 푸쉬업
                exrNameTv3.setText("버피")
                exrIv3.setImageResource(R.raw.buppit)
                Glide.with(this).load(R.raw.buppit).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            } else if (exrSet == 2) {//2일때 니업
                exrNameTv3.setText("니업")
                exrIv3.setImageResource(R.raw.kneeup)
                Glide.with(this).load(R.raw.kneeup).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            } else if (exrSet == 3) { //3일때 레그레이즈
                exrNameTv3.setText("레그레이즈")
                exrIv3.setImageResource(R.raw.legrais)
                Glide.with(this).load(R.raw.legrais).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            } else if (exrSet == 4) { //4일때 런지
                exrNameTv3.setText("런지")
                exrIv3.setImageResource(R.raw.lunge)
                Glide.with(this).load(R.raw.lunge).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            } else if (exrSet == 5) {//5일때 브릿지
                exrNameTv3.setText("브릿지")
                exrIv3.setImageResource(R.raw.bridge)
                Glide.with(this).load(R.raw.bridge).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            } else if (exrSet == 6) {//6일때 마운틴 클라이머
                exrNameTv3.setText("마운틴 클라이머")
                exrIv3.setImageResource(R.raw.mountainclimer)
                Glide.with(this).load(R.raw.mountainclimer).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            }else if (exrSet == 7) {//6일때 마운틴 클라이머
                exrNameTv3.setText("팔굽혀펴기")
                exrIv3.setImageResource(R.raw.pushup)
                Glide.with(this).load(R.raw.pushup).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            } else if (exrSet == 8) {//8일때 재자리 오르기
                exrNameTv3.setText("제자리 오르기")
                exrIv3.setImageResource(R.raw.stair)
                Glide.with(this).load(R.raw.stair).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            }else if (exrSet == 9) {
                exrNameTv3.setText("플랭크")
                Toast.makeText(this@HardActivity, "3초뒤 시작합니다 자세를 잡으세요", Toast.LENGTH_SHORT).show()
                exrIv3.setImageResource(R.raw.planksideup)
                Handler().postDelayed({startTimer()},3000L)
                Handler().postDelayed({val intent = Intent(this,FinishActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
                    startActivity(intent)},45000L)
            }
        }
    }
    fun startTimer(){
        time=0
        timerTask= timer(period = 10){
            time++
            var txtTime3=findViewById(R.id.txtTime3) as TextView
            val sec =time/100
            val milli=time%100
            runOnUiThread {
                txtTime3?.text="${sec} : ${milli}"
                if(sec==40){
                    timerTask?.cancel()
                }
            }
        }
    }

}