package com.example.exercisekt

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.*
import com.bumptech.glide.Glide
import java.util.*
import kotlin.concurrent.timer


class BeginActivity : AppCompatActivity() {
    var time2 =0// 시간제어
    var timerTask :Timer?=null//시간제어
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_begin)
        var exrSet = 0 //운동 종류 제어자

        var exrIv:ImageView =findViewById(R.id.exrIv)//운동 이미지
        var nxtBtn:Button =findViewById(R.id.nxtBtn)//다음 운동 버튼
        var exrNameTv=findViewById(R.id.exrNameTv)as TextView

        exrIv.setImageResource(R.raw.jumpingjack)//뷰에 이미지 세트
        Glide.with(this).load(R.raw.jumpingjack).override(3000, 4000).into(exrIv)//gif형식 파일 사용

        nxtBtn.setOnClickListener {
            exrSet+=1
            if(exrSet==1){//1일때 푸쉬업
                exrNameTv.setText("푸쉬업")
                exrIv.setImageResource(R.raw.pushup)
                Glide.with(this).load(R.raw.pushup).override(3000, 4000).into(exrIv)//gif형식 파일 사용
            }
            else if(exrSet==2){//2일때 윗몸일으키기
                exrNameTv.setText("윗몸일으키기")
                exrIv.setImageResource(R.raw.upper)
                Glide.with(this).load(R.raw.upper).override(3000, 4000).into(exrIv)//gif형식 파일 사용
            }
            else if(exrSet==3){ //3일때 플랭크 사이드업
                exrNameTv.setText("플랭크 사이드업")
                exrIv.setImageResource(R.raw.planksideup)
                Glide.with(this).load(R.raw.planksideup).override(3000, 4000).into(exrIv)//gif형식 파일 사용
            }
            else if(exrSet==4){ //4일때 사이드 체크
                exrNameTv.setText("사이드 체크")
                exrIv.setImageResource(R.raw.sidecheck)
                Glide.with(this).load(R.raw.sidecheck).override(3000, 4000).into(exrIv)//gif형식 파일 사용
            }
            else if(exrSet==5){//5일때  체어딥스
                exrNameTv.setText("체어딥스")
                exrIv.setImageResource(R.raw.chairdips)
                Glide.with(this).load(R.raw.chairdips).override(3000, 4000).into(exrIv)//gif형식 파일 사용
            }
            else if(exrSet==6){
                exrNameTv.setText("플랭크")
                Toast.makeText(this@BeginActivity, "3초뒤 시작합니다 자세를 잡으세요", Toast.LENGTH_SHORT).show()
                exrIv.setImageResource(R.raw.planksideup)
                Handler().postDelayed({startTimer()},3000L)
                Handler().postDelayed({
                    val intent = Intent(this,FinishActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
                    startActivity(intent)},24500L)
            }
        }
    }

    fun startTimer(){
        time2=0
        timerTask=timer(period = 10){
            time2++
            var countTv=findViewById(R.id.countTv) as TextView
            val sec =time2/100
            val milli=time2%100
            runOnUiThread {
                countTv?.text="${sec} : ${milli}"
                if(sec==20){timerTask?.cancel()
                }
            }
        }
    }

}