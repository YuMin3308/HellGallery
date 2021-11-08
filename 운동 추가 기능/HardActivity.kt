package com.example.logo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
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
        var txtTime3=findViewById(R.id.txtTime3)as TextView

        exrIv3.setImageResource(+R.raw.exer_jumpingjack)//뷰에 이미지 세트
        Glide.with(this).load(R.raw.exer_jumpingjack).override(3000, 4000).into(exrIv3)//gif형식 파일 사용


        ////////////////////////////////////////////////////////////////////////////////////////////////////
        //추가 업다운 버튼 11.04
        var upperBtn: View =findViewById(R.id.upperBtn)//횟수 업 버튼
        var downBtn: View =findViewById(R.id.downBtn)//횟수 다운 버튼
        var count:Int=20//운동 횟수 제어자
        var saveCount:Int=0//운동 횟수 증가 정도 저장 인텐트로 넘겨줄거임
        upperBtn.setOnClickListener{
            if(count<30){
                count++
                saveCount++
            }//추가 11.09
            else Toast.makeText(this,"10회 이상 추가는 안되요",Toast.LENGTH_SHORT).show()//추가 11.09
            txtTime3.setText(count.toString()+"회")
        }

        downBtn.setOnClickListener{
            if(count>0){
                count--
                saveCount--
            }
            else Toast.makeText(this,"0이하로는 더 못내려갑니다.",Toast.LENGTH_SHORT).show()
            txtTime3.setText(count.toString()+"회")
        }
        // /추가 업다운 버튼 11.04

        //추가 11.04 횟수 세팅
        fun setsetting(num:String){
            count=20//최초에는 운동횟수 20
            txtTime3.text="$num"
        }
        // /추가 11.04 횟수 세팅
        //각 운동 제어문 마다 setsetting("10회")추가 마지막 제어문 부분은 setsetting("")을 추가

///////////////////////////////////////////////////////////////////////////////////////////////
        nxtBtn3.setOnClickListener {
            exrSet += 1
            if (exrSet == 1) {//1일때 푸쉬업
                setsetting("20회")//추가 11.04
                exrNameTv3.setText("버피")
                exrIv3.setImageResource(+R.raw.exer_buppit)
                Glide.with(this).load(R.raw.exer_buppit).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            } else if (exrSet == 2) {//2일때 니업
                setsetting("20회")//추가 11.04
                exrNameTv3.setText("니업")
                exrIv3.setImageResource(+R.raw.exer_kneeup)
                Glide.with(this).load(R.raw.exer_kneeup).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            } else if (exrSet == 3) { //3일때 레그레이즈
                setsetting("20회")//추가 11.04
                exrNameTv3.setText("레그레이즈")
                exrIv3.setImageResource(+R.raw.exer_legrais)
                Glide.with(this).load(R.raw.exer_legrais).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            } else if (exrSet == 4) { //4일때 런지
                setsetting("20회")//추가 11.04
                exrNameTv3.setText("런지")
                exrIv3.setImageResource(+R.raw.exer_lunge)
                Glide.with(this).load(R.raw.exer_lunge).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            } else if (exrSet == 5) {//5일때 브릿지
                setsetting("20회")//추가 11.04
                exrNameTv3.setText("브릿지")
                exrIv3.setImageResource(+R.raw.exer_bridge)
                Glide.with(this).load(R.raw.exer_bridge).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            } else if (exrSet == 6) {//6일때 마운틴 클라이머
                setsetting("20회")//추가 11.04
                exrNameTv3.setText("마운틴 클라이머")
                exrIv3.setImageResource(+R.raw.exer_mountainclimer)
                Glide.with(this).load(R.raw.exer_mountainclimer).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            }else if (exrSet == 7) {//
                setsetting("20회")//추가 11.04
                exrNameTv3.setText("팔굽혀펴기")
                exrIv3.setImageResource(+R.raw.exer_pushup)
                Glide.with(this).load(R.raw.exer_pushup).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            } else if (exrSet == 8) {//8일때 재자리 오르기
                setsetting("20회")//추가 11.04
                exrNameTv3.setText("제자리 오르기")
                exrIv3.setImageResource(+R.raw.exer_stair)
                Glide.with(this).load(R.raw.exer_stair).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
            }else if (exrSet == 9) {
                upperBtn.setVisibility(View.INVISIBLE)//11.04 버튼 없애기
                downBtn.setVisibility(View.INVISIBLE)//11.04 버튼 없애기
                setsetting("")//추가 11.04
                exrNameTv3.setText("플랭크")
                Toast.makeText(this@HardActivity, "3초뒤 시작합니다 자세를 잡으세요", Toast.LENGTH_SHORT).show()
                exrIv3.setImageResource(+R.raw.exer_planksideup)
                Handler().postDelayed({startTimer()},3000L)
                Handler().postDelayed({val intentHard = Intent(this,FinishActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
                    intentHard.putExtra("id",intent.getStringExtra("id"))
                    intentHard.putExtra("save","$saveCount") //추가 11.04 운동 횟수조작 카운트 값 넘김
                    intentHard.putExtra("Hard","Hard")//추가 11.09 난이도 태그
                    startActivity(intentHard)},45000L)
            }
        }
    }

    override fun onBackPressed() { //뒤로가기 버튼 막기
    }

    fun startTimer() {
        time = 0
        var txtTime3 = findViewById(R.id.txtTime3) as TextView
        timerTask = kotlin.concurrent.timer(period = 10) {    // timer() 호출
            time++    // period=10, 0.01초마다 time를 1씩 증가
            val sec = time / 100    // time/100, 나눗셈의 몫 (초 부분)
            val milli = time % 100    // time%100, 나눗셈의 나머지 (밀리초 부분)

            runOnUiThread {
                if (sec >= 40) txtTime3.text = "40:00"
                else txtTime3.text = "$sec:$milli"    // TextView 세팅
            }
        }
    }

}