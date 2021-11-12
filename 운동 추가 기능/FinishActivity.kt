package com.example.logo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

class FinishActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth; // 파이어베이스
    private val TAG: String = "InterfaceMain"
    private val db = Firebase.firestore
    private var user_data = FireStore("",0,0,0,0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        val rtMainBtn :Button=findViewById(R.id.rtMainBtn)
        val endIv: ImageView=findViewById(R.id.endIv)
        val expAddText = findViewById<TextView>(R.id.expAddText)
        val textView2 = findViewById<TextView>(R.id.textView2)




        //파이어 스토어 데이터 가져오기
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) { // 가져온 문서들이 result로 들어감
                    Log.d(TAG, "${document.id} => ${document.data}")


                        //유저 데이터 가져오기
                        user_data = FireStore(
                            document["email"] as String,
                            document["exp"] as Long, document["level"] as Long,
                            document["height"] as Long, document["weight"] as Long
                        )

                    if(user_data.email.equals(intent.getStringExtra("id"))) { // 로그인한 이메일이면
                        /////////////////////////////////
                        //추가 11.04 경험치 추가 획득
                        if((intent.hasExtra("save"))&&(intent.hasExtra("difficulty"))) {
                            if((intent.getStringExtra("difficulty") == "Begin" && user_data.level.toInt() >= 20) ||
                                (intent.getStringExtra("difficulty") == "Normal" && user_data.level.toInt() >= 20)) { // 사용자의 레벨이 20이상인데 노말 비긴을 실행할경우 500만 줌
                                var saveCount: String
                                saveCount = intent.getStringExtra("save").toString()
                                if(500 + (saveCount.toInt() * 10)>=0){
                                    imageSet("gif")
                                    expAddText.setText("+" + (500 + (saveCount.toInt() * 10)).toString() + "EXP!!")
                                    user_data.exp += ((500 + (saveCount.toInt() * 10)).toLong())
                                }
                                else if(500 + (saveCount.toInt() * 10)<0){
                                    imageSet("png")
                                    user_data.exp += 0
                                    expAddText.setText("0EXP WHAT??")
                                    textView2.setText("NO CHEAT")
                                }
                            }
                            else if(intent.getStringExtra("difficulty") == "Begin") {
                                var saveCount = intent.getStringExtra("save").toString()
                                if(500 + (saveCount.toInt() * 10)>=0){
                                    imageSet("gif")
                                    expAddText.setText("+" + (500 + (saveCount.toInt() * 10)).toString() + "EXP!!")
                                    user_data.exp += ((500 + (saveCount.toInt() * 10)).toLong())
                                }
                                else if(500 + (saveCount.toInt() * 10)<0){
                                    imageSet("png")
                                    user_data.exp += 0
                                    expAddText.setText("0EXP WHAT??")
                                    endIv.setImageResource(R.drawable.nocheating)
                                    textView2.setText("NO CHEAT")
                                }
                                else {
                                    imageSet("gif")
                                    user_data.exp += 500
                                    expAddText.setText("+500EXP!!")
                                }
                            }
                            else if(intent.getStringExtra("difficulty") == "Normal") {
                                var saveCount = intent.getStringExtra("save").toString()
                                if(saveCount.toInt() < 0 || saveCount.toInt() > 0) {
                                    if(600 + (saveCount.toInt() * 10)>=0){
                                        imageSet("gif")
                                        expAddText.setText("+" + (600 + (saveCount.toInt() * 10)).toString() + "EXP!!")
                                        user_data.exp += ((600 + (saveCount.toInt() * 10)).toLong())
                                    }
                                    else if(600 + (saveCount.toInt() * 10)<0){
                                        imageSet("png")
                                        user_data.exp += 0
                                        expAddText.setText("0EXP WHAT??")
                                        textView2.setText("NO CHEAT")
                                    }
                                }
                                else {
                                    imageSet("gif")
                                    user_data.exp += 600
                                    expAddText.setText("+600EXP!!")
                                }
                            }
                            else if(intent.getStringExtra("difficulty") == "Hard"){ // 하드모드일때의 경험치 흭득부분
                                var saveCount = intent.getStringExtra("save").toString()
                                if(saveCount.toInt() < 0 || saveCount.toInt() > 0) {
                                    if(700 + (saveCount.toInt() * 10)>=0){
                                        imageSet("gif")
                                        expAddText.setText("+" + (700 + (saveCount.toInt() * 10)).toString() + "EXP!!")
                                        user_data.exp += ((700 + (saveCount.toInt() * 10)).toLong())
                                    }
                                    else if(700 + (saveCount.toInt() * 10)<0){
                                        imageSet("png")
                                        user_data.exp += 0
                                        expAddText.setText("0EXP WHAT??")
                                        textView2.setText("NO CHEAT")
                                    }
                                }
                                else {
                                    imageSet("gif")
                                    user_data.exp += 700
                                    expAddText.setText("+700EXP!!")
                                }
                            }
                            // /추가 11.04 경험치 추가 획득
                            /////////////////////////////////
                        }// /로그인한 이메일이면
                        if (user_data.exp.toInt() >= 2000) {
                            user_data.level += 1
                            user_data.exp -= 2000
                        }

                        val db_ref = db.collection("users").document(document.id) // 데이터 경로 설정
                        db_ref.set(user_data) // 수정된 데이터 삽입

                    }
                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        rtMainBtn.setOnClickListener {
            val intentFinish = Intent(this,InterfaceMain::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
            intentFinish.putExtra("id",intent.getStringExtra("id"))
            startActivity(intentFinish)
        }
    }
    /////////////////////////////추가
    fun imageSet(select:String){
        val endIv: ImageView=findViewById(R.id.endIv)
        if(select=="gif"){//불꽃놀이
            endIv.setImageResource(+R.raw.exer_fireflower)
            Glide.with(this).load(R.raw.exer_fireflower).override(1000, 1000).into(endIv)//gif형식 파일 사용
        }
        else if(select=="png"){//0이하면 no cheat출력
            endIv.setImageResource(R.drawable.nocheating)
        }
    }
    ////////////////////////////

    override fun onBackPressed() { //뒤로가기 버튼 막기
    }

}