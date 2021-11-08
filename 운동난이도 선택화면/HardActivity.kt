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
import org.w3c.dom.Text
import java.util.*
import kotlin.concurrent.timer

class HardActivity : AppCompatActivity() {
    var time =0// 시간제어
    var timerTask : Timer?=null//시간제어
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hard)

        var exrSet = 0 //운동 종류 제어자

        val exrIv3: ImageView = findViewById(R.id.exrIv3)//운동 이미지
        val nxtBtn3: Button = findViewById(R.id.nxtBtn3)//다음 운동 버튼
        val exrNameTv3 = findViewById(R.id.exrNameTv3) as TextView
        val textV = findViewById<TextView>(R.id.txtTime3)
        val explanButton = findViewById<Button>(R.id.exerExplanButtonHard)

        exrIv3.setImageResource(+R.raw.exer_jumpingjack)//뷰에 이미지 세트
        Glide.with(this).load(R.raw.exer_jumpingjack).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
        explanButton.setOnClickListener {
            val intentJumping = Intent(this, JumpingJack::class.java)
            startActivity(intentJumping)
        }

        nxtBtn3.setOnClickListener {
            exrSet += 1
            if (exrSet == 1) {//1일때 버피
                exrNameTv3.setText("버피")
                exrIv3.setImageResource(+R.raw.exer_buppit)
                Glide.with(this).load(R.raw.exer_buppit).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
                explanButton.setOnClickListener {
                    val intentBurpee = Intent(this, Burpee::class.java)
                    startActivity(intentBurpee)
                }
            }

            else if (exrSet == 2) {//2일때 니업
                exrNameTv3.setText("니업")
                exrIv3.setImageResource(+R.raw.exer_kneeup)
                Glide.with(this).load(R.raw.exer_kneeup).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
                explanButton.setVisibility(View.GONE)
            }

            else if (exrSet == 3) { //3일때 레그레이즈
                explanButton.setVisibility(View.VISIBLE)
                exrNameTv3.setText("레그레이즈")
                exrIv3.setImageResource(+R.raw.exer_legrais)
                Glide.with(this).load(R.raw.exer_legrais).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
                explanButton.setOnClickListener {
                    val intentLegRaise = Intent(this, LegRaise::class.java)
                    startActivity(intentLegRaise)
                }
            }

            else if (exrSet == 4) { //4일때 런지
                exrNameTv3.setText("런지")
                exrIv3.setImageResource(+R.raw.exer_lunge)
                Glide.with(this).load(R.raw.exer_lunge).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
                explanButton.setOnClickListener {
                    val intentLunge = Intent(this, Lunge::class.java)
                    startActivity(intentLunge)
                }
            }

            else if (exrSet == 5) {//5일때 브릿지
                exrNameTv3.setText("브릿지")
                exrIv3.setImageResource(+R.raw.exer_bridge)
                Glide.with(this).load(R.raw.exer_bridge).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
                explanButton.setOnClickListener {
                    val intentBridge = Intent(this, Bridge::class.java)
                    startActivity(intentBridge)
                }
            }

            else if (exrSet == 6) {//6일때 마운틴 클라이머
                exrNameTv3.setText("마운틴 클라이머")
                exrIv3.setImageResource(+R.raw.exer_mountainclimer)
                Glide.with(this).load(R.raw.exer_mountainclimer).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
                explanButton.setOnClickListener {
                    val intentMountainClimber = Intent(this, MountainClimber::class.java)
                    startActivity(intentMountainClimber)
                }
            }

            else if (exrSet == 7) {//7일때 푸쉬업
                exrNameTv3.setText("팔굽혀펴기")
                exrIv3.setImageResource(+R.raw.exer_pushup)
                Glide.with(this).load(R.raw.exer_pushup).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
                explanButton.setOnClickListener {
                    val intentPush = Intent(this, PushUp::class.java)
                    startActivity(intentPush)
                }
            }

            else if (exrSet == 8) {//8일때 제자리 오르기
                exrNameTv3.setText("제자리 오르기")
                exrIv3.setImageResource(+R.raw.exer_stair)
                Glide.with(this).load(R.raw.exer_stair).override(3000, 4000).into(exrIv3)//gif형식 파일 사용
                explanButton.setVisibility(View.GONE)
            }

            else if (exrSet == 9) {
                explanButton.setVisibility(View.VISIBLE)
                nxtBtn3.setText("START")
                explanButton.setOnClickListener {
                    val intentPlank = Intent(this, Plank::class.java)
                    startActivity(intentPlank)
                }
                exrNameTv3.setText("플랭크")
                exrIv3.setImageResource(+R.raw.exer_planksideup)
                textV.setVisibility(View.GONE)
                textV.setText("")

                nxtBtn3.setOnClickListener {
                    nxtBtn3.setEnabled(false)
                    textV.setVisibility(View.VISIBLE)
                    explanButton.setVisibility(View.GONE)
                    Toast.makeText(this@HardActivity, "3초뒤 시작합니다 자세를 잡으세요", Toast.LENGTH_SHORT).show()
                    Handler().postDelayed({ startTimer() }, 3000L)
                    Handler().postDelayed({
                        val intentHard =
                            Intent(this, FinishActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
                        intentHard.putExtra("id", intent.getStringExtra("id"))
                        startActivity(intentHard)
                    }, 45000L)
                }
            }
        }
    }

    override fun onBackPressed() { //뒤로가기 버튼 막기
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
