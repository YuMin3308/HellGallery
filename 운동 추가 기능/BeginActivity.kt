package com.example.logo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
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
        var countTv=findViewById(R.id.countTv) as TextView

        var exrIv:ImageView =findViewById(R.id.exrIv)//운동 이미지
        var nxtBtn:Button =findViewById(R.id.nxtBtn)//다음 운동 버튼
        var exrNameTv=findViewById(R.id.exrNameTv)as TextView


        exrIv.setImageResource(+R.raw.exer_jumpingjack)//뷰에 이미지 세트
        Glide.with(this).load(R.raw.exer_jumpingjack).override(3000, 4000).into(exrIv)//gif형식 파일 사용

        ////////////////////////////////////////////////////////////////////////////////////////////////////
        //추가 업다운 버튼 11.04
        var upperBtn: View =findViewById(R.id.upperBtn)//횟수 업 버튼
        var downBtn: View =findViewById(R.id.downBtn)//횟수 다운 버튼
        var count:Int=10//운동 횟수 제어자
        var saveCount:Int=0//운동 횟수 증가 정도 저장 인텐트로 넘겨줄거임
        upperBtn.setOnClickListener{
            count++
            countTv.setText(count.toString()+"회")
            saveCount++
        }

        downBtn.setOnClickListener{
            if(count!=0) count--
            if(count>=0) {
                saveCount--
                countTv.setText(count.toString()+"회")
            }
            else Toast.makeText(this,"0이하로는 더 못내려갑니다.",Toast.LENGTH_SHORT).show()
        }
        // /추가 업다운 버튼 11.04

        //추가 11.04 횟수 세팅
        fun setsetting(num:String){
            count=10//최초에는 운동횟수 10
            countTv.text="$num"
        }
        // /추가 11.04 횟수 세팅
        //각 운동 제어문 마다 setsetting("10회")추가 마지막 제어문 부분은 setsetting("")을 추가

///////////////////////////////////////////////////////////////////////////////////////////////

        nxtBtn.setOnClickListener {
            exrSet+=1
            if(exrSet==1){//1일때 푸쉬업
                setsetting("10회")//추가 11.04
                exrNameTv.setText("푸쉬업")
                exrIv.setImageResource(+R.raw.exer_pushup)
                Glide.with(this).load(R.raw.exer_pushup).override(3000, 4000).into(exrIv)//gif형식 파일 사용
            }
            else if(exrSet==2){//2일때 윗몸일으키기
                setsetting("10회")//추가 11.04
                exrNameTv.setText("윗몸일으키기")
                exrIv.setImageResource(+R.raw.exer_upper)
                Glide.with(this).load(R.raw.exer_upper).override(3000, 4000).into(exrIv)//gif형식 파일 사용
            }
            else if(exrSet==3){ //3일때 플랭크 사이드업
                setsetting("10회")//추가 11.04
                exrNameTv.setText("플랭크 사이드업")
                exrIv.setImageResource(+R.raw.exer_planksideup)
                Glide.with(this).load(R.raw.exer_planksideup).override(3000, 4000).into(exrIv)//gif형식 파일 사용
            }
            else if(exrSet==4){ //4일때 사이드 체크
                setsetting("10회")//추가 11.04
                exrNameTv.setText("사이드 체크")
                exrIv.setImageResource(+R.raw.exer_sidecheck)
                Glide.with(this).load(R.raw.exer_sidecheck).override(3000, 4000).into(exrIv)//gif형식 파일 사용
            }
            else if(exrSet==5){//5일때  체어딥스
                setsetting("10회")//추가 11.04
                exrNameTv.setText("체어딥스")
                exrIv.setImageResource(+R.raw.exer_chairdips)
                Glide.with(this).load(R.raw.exer_chairdips).override(3000, 4000).into(exrIv)//gif형식 파일 사용
            }
            else if(exrSet==6){
                upperBtn.setVisibility(View.INVISIBLE)//11.04 버튼 없애기
                downBtn.setVisibility(View.INVISIBLE)//11.04 버튼 없애기
                setsetting("")//추가 11.04
                exrNameTv.setText("플랭크")
                Toast.makeText(this@BeginActivity, "3초뒤 시작합니다 자세를 잡으세요", Toast.LENGTH_SHORT).show()
                exrIv.setImageResource(+R.raw.exer_planksideup)
                Handler().postDelayed({startTimer()},3000L)
                Handler().postDelayed({
                    val intentBegin = Intent(this,FinishActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
                    intentBegin.putExtra("id",intent.getStringExtra("id"))
                    intentBegin.putExtra("save","$saveCount")//추가 11.04 운동 횟수조작 카운트 값 넘김
                    startActivity(intentBegin)},25000L)
            }
        }
    }

    override fun onBackPressed() { //뒤로가기 버튼 막기
    }
    fun startTimer(){
        time2=0
        var countTv=findViewById(R.id.countTv) as TextView
        timerTask = kotlin.concurrent.timer(period = 10) {	// timer() 호출
            time2++	// period=10, 0.01초마다 time를 1씩 증가
            val sec = time2 / 100	// time/100, 나눗셈의 몫 (초 부분)
            val milli = time2 % 100	// time%100, 나눗셈의 나머지 (밀리초 부분)

            runOnUiThread {
                if(sec>=20)countTv.text ="20:00"
                else countTv.text = "$sec:$milli"	// TextView 세팅
            }
        }
    }

}