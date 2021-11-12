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
import com.example.logo.exercise.*
import java.util.*
import kotlin.concurrent.timer


class NormalActivity : AppCompatActivity() {

    var time =0// 시간제어
    var timerTask : Timer?=null//시간제어
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal)

        var exrSet = 0 //운동 종류 제어자

        val exrIv2: ImageView = findViewById(R.id.exrIv2)//운동 이미지
        val nxtBtn2: Button = findViewById(R.id.nxtBtn2)//다음 운동 버튼
        val exrNameTv2 = findViewById(R.id.exrNameTv2) as TextView
        val textV = findViewById<TextView>(R.id.txtTime2)
        val explanButton = findViewById<Button>(R.id.exerExplanButtonNormal)

        exrIv2.setImageResource(+R.raw.exer_jumpingjack)//뷰에 이미지 세트
        Glide.with(this).load(R.raw.exer_jumpingjack).override(3000, 4000).into(exrIv2)//gif형식 파일 사용
        // //////////////////////////////////////////////////////////////////////////////////////////////////
        //추가 업다운 버튼 11.04
        var upperBtn: View = findViewById(R.id.upperBtn)//횟수 업 버튼
        var downBtn: View = findViewById(R.id.downBtn)//횟수 다운 버튼
        var count: Int = 15//운동 횟수 제어자
        var saveCount: Int = 0//운동 횟수 증가 정도 저장 인텐트로 넘겨줄거임
        upperBtn.setOnClickListener {
            if(count < 25 ) {
                count++
                textV.setText(count.toString() + "회")
                saveCount++
            }
        }

        downBtn.setOnClickListener {
            if(count!=0){
                count--
                saveCount--
            } //수정 11.09
            if (count >= 0) {
                textV.setText(count.toString() + "회")
                if(count==0) Toast.makeText(this,"0이하로는 더 못내려갑니다.",Toast.LENGTH_SHORT).show()
            }
        }
        // /추가 업다운 버튼 11.04

        //추가 11.04 횟수 세팅
        fun setsetting(num: String) {
            count = 15//최초에는 운동횟수 15
            textV.text = "$num"
        }
        // /추가 11.04 횟수 세팅
        //각 운동 제어문 마다 setsetting("10회")추가 마지막 제어문 부분은 setsetting("")을 추가

///////////////////////////////////////////////////////////////////////////////////////////////
        explanButton.setOnClickListener {
            val intentJumping = Intent(this, JumpingJack::class.java)
            startActivity(intentJumping)
        }

        nxtBtn2.setOnClickListener {
            exrSet += 1
            if (exrSet == 1) {//1일때 푸쉬업
                setsetting("15회")//추가 11.04
                exrNameTv2.setText("푸쉬업")
                exrIv2.setImageResource(+R.raw.exer_pushup)
                Glide.with(this).load(R.raw.exer_pushup).override(3000, 4000).into(exrIv2)//gif형식 파일 사용

                explanButton.setOnClickListener {
                    val intentPush = Intent(this, PushUp::class.java)
                    startActivity(intentPush)
                }
            }

            else if (exrSet == 2) {//2일때 니업
                setsetting("15회")//추가 11.04
                exrNameTv2.setText("니업")
                exrIv2.setImageResource(+R.raw.exer_kneeup)
                Glide.with(this).load(R.raw.exer_kneeup).override(3000, 4000).into(exrIv2)//gif형식 파일 사용
                explanButton.setVisibility(View.GONE)
            }

            else if (exrSet == 3) { //3일때 레그레이즈
                setsetting("15회")//추가 11.04
                explanButton.setVisibility(View.VISIBLE)
                exrNameTv2.setText("레그레이즈")
                exrIv2.setImageResource(+R.raw.exer_legrais)
                Glide.with(this).load(R.raw.exer_legrais).override(3000, 4000).into(exrIv2)//gif형식 파일 사용

                explanButton.setOnClickListener {
                    val intentLegRaise = Intent(this, LegRaise::class.java)
                    startActivity(intentLegRaise)
                }
            }

            else if (exrSet == 4) { //4일때 런지
                setsetting("15회")//추가 11.04
                exrNameTv2.setText("런지")
                exrIv2.setImageResource(+R.raw.exer_lunge)
                Glide.with(this).load(R.raw.exer_lunge).override(3000, 4000).into(exrIv2)//gif형식 파일 사용

                explanButton.setOnClickListener {
                    val intentLunge = Intent(this, Lunge::class.java)
                    startActivity(intentLunge)
                }
            }

            else if (exrSet == 5) {//5일때 브릿지
                setsetting("15회")//추가 11.04
                exrNameTv2.setText("브릿지")
                exrIv2.setImageResource(+R.raw.exer_bridge)
                Glide.with(this).load(R.raw.exer_bridge).override(3000, 4000).into(exrIv2)//gif형식 파일 사용

                explanButton.setOnClickListener {
                    val intentBridge = Intent(this, Bridge::class.java)
                    startActivity(intentBridge)
                }
            }

            else if (exrSet == 6) {//6일때 마운틴 클라이머
                setsetting("15회")//추가 11.04
                exrNameTv2.setText("마운틴 클라이머")
                exrIv2.setImageResource(+R.raw.exer_mountainclimer)
                Glide.with(this).load(R.raw.exer_mountainclimer).override(3000, 4000).into(exrIv2)//gif형식 파일 사용

                explanButton.setOnClickListener {
                    val intentMountainClimber = Intent(this, MountainClimber::class.java)
                    startActivity(intentMountainClimber)
                }
            }

            else if (exrSet == 7) {
                upperBtn.setVisibility(View.INVISIBLE)//11.04 버튼 없애기
                downBtn.setVisibility(View.INVISIBLE)//11.04 버튼 없애기
                setsetting("")//추가 11.04

                nxtBtn2.setText("START")
                explanButton.setOnClickListener {
                    val intentPlank = Intent(this, Plank::class.java)
                    startActivity(intentPlank)
                }
                exrNameTv2.setText("플랭크")
                exrIv2.setImageResource(+R.raw.exer_planksideup)
                textV.setVisibility(View.GONE)
                textV.setText("")

                nxtBtn2.setOnClickListener {
                    nxtBtn2.setEnabled(false)
                    textV.setVisibility(View.VISIBLE)
                    explanButton.setVisibility(View.GONE)
                    Toast.makeText(this@NormalActivity, "3초뒤 시작합니다 자세를 잡으세요", Toast.LENGTH_SHORT).show()
                    Handler().postDelayed({ startTimer() }, 3000L)
                    Handler().postDelayed(
                        {
                            val intentNormal =
                                Intent(this, FinishActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
                            intentNormal.putExtra("id", intent.getStringExtra("id"))
                            intentNormal.putExtra("save","$saveCount")//추가 11.04 운동 횟수조작 카운트 값 넘김
                            intentNormal.putExtra("difficulty","Normal")
                            startActivity(intentNormal)
                        }, 35000L
                    )
                }
            }
        }
    }

    override fun onBackPressed() { //뒤로가기 버튼 막기
    }

    fun startTimer() {
        time = 0
        var txtTime2 = findViewById(R.id.txtTime2) as TextView
        timerTask = kotlin.concurrent.timer(period = 10) {    // timer() 호출
            time++    // period=10, 0.01초마다 time를 1씩 증가
            val sec = time / 100    // time/100, 나눗셈의 몫 (초 부분)
            val milli = time % 100    // time%100, 나눗셈의 나머지 (밀리초 부분)

            runOnUiThread {
                if (sec >= 30) txtTime2.text = "30:00"
                else txtTime2.text = "$sec:$milli"    // TextView 세팅
            }
        }
    }

}