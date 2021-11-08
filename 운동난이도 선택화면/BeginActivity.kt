package com.example.logo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import com.example.logo.exercise.*
import java.util.*
import kotlin.concurrent.timer


class BeginActivity : AppCompatActivity() { // 초보 운동 선택
    var time2 =0// 시간제어
    var timerTask :Timer?=null//시간제어
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_begin)
        var exrSet = 0 //운동 종류 제어자

        val exrIv:ImageView =findViewById(R.id.exrIv)//운동 이미지
        val nxtBtn:Button =findViewById(R.id.nxtBtn)//다음 운동 버튼
        val exrNameTv=findViewById(R.id.exrNameTv)as TextView
        val textV = findViewById<TextView>(R.id.countTv) // 버튼 아래 텍스트
        val explanButton = findViewById<Button>(R.id.exerExplanButtonBegin)

        exrIv.setImageResource(+R.raw.exer_jumpingjack)//뷰에 이미지 세트
        Glide.with(this).load(R.raw.exer_jumpingjack).override(3000, 4000).into(exrIv)//gif형식 파일 사용
        explanButton.setOnClickListener {
            val intentJumping = Intent(this, JumpingJack::class.java)
            startActivity(intentJumping)
        }

        nxtBtn.setOnClickListener {
            exrSet+=1
            if(exrSet==1){//1일때 푸쉬업
                exrNameTv.setText("푸쉬업")
                exrIv.setImageResource(+R.raw.exer_pushup)
                Glide.with(this).load(R.raw.exer_pushup).override(3000, 4000).into(exrIv)//gif형식 파일 사용
                explanButton.setOnClickListener {
                    val intentPush = Intent(this, PushUp::class.java)
                    startActivity(intentPush)
                }
            }
            else if(exrSet==2){//2일때 윗몸일으키기
                exrNameTv.setText("윗몸일으키기")
                exrIv.setImageResource(+R.raw.exer_upper)
                Glide.with(this).load(R.raw.exer_upper).override(3000, 4000).into(exrIv)//gif형식 파일 사용
                explanButton.setOnClickListener {
                    val intentUpper = Intent(this, Upper::class.java)
                    startActivity(intentUpper)
                }
            }
            else if(exrSet==3){ //3일때 플랭크 사이드업
                exrNameTv.setText("플랭크 사이드업")
                exrIv.setImageResource(+R.raw.exer_planksideup)
                Glide.with(this).load(R.raw.exer_planksideup).override(3000, 4000).into(exrIv)//gif형식 파일 사용
                explanButton.setOnClickListener {
                    val intentPSideup = Intent(this, PlankSideUp::class.java)
                    startActivity(intentPSideup)
                }
            }
            else if(exrSet==4){ //4일때 사이드 체크
                exrNameTv.setText("사이드 체크")
                exrIv.setImageResource(+R.raw.exer_sidecheck)
                Glide.with(this).load(R.raw.exer_sidecheck).override(3000, 4000).into(exrIv)//gif형식 파일 사용
                explanButton.setVisibility(View.GONE) // 버튼숨기기
            }
            else if(exrSet==5){//5일때  체어딥스
                exrNameTv.setText("체어딥스")
                exrIv.setImageResource(+R.raw.exer_chairdips)
                Glide.with(this).load(R.raw.exer_chairdips).override(3000, 4000).into(exrIv)//gif형식 파일 사용
                explanButton.setVisibility(View.VISIBLE) // 버튼숨기기
                explanButton.setOnClickListener {
                    val intentChairDips = Intent(this, ChairDips::class.java)
                    startActivity(intentChairDips)
                }
            }
            else if(exrSet==6){
                nxtBtn.setText("START")
                explanButton.setOnClickListener {
                    val intentPlank = Intent(this, Plank::class.java)
                    startActivity(intentPlank)
                }
                exrNameTv.setText("플랭크")
                exrIv.setImageResource(+R.raw.exer_planksideup)
                textV.setVisibility(View.GONE)
                textV.setText("")

                nxtBtn.setOnClickListener {
                    nxtBtn.setEnabled(false)
                    textV.setVisibility(View.VISIBLE)
                    explanButton.setVisibility(View.GONE)
                    Toast.makeText(this@BeginActivity, "3초뒤 시작합니다 자세를 잡으세요", Toast.LENGTH_SHORT)
                        .show()
                    Handler().postDelayed({ startTimer() }, 3000L)
                    Handler().postDelayed({
                        val intentBegin =
                            Intent(this, FinishActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
                        intentBegin.putExtra("id", intent.getStringExtra("id"))
                        startActivity(intentBegin)
                    }, 14050L)

                }
            }
        }
    }

    override fun onBackPressed() { //뒤로가기 버튼 막기
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
