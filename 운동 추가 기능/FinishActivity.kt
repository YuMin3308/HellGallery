package com.example.logo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
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

        var rtMainBtn :Button=findViewById(R.id.rtMainBtn)
        var endIv: ImageView=findViewById(R.id.endIv)



        endIv.setImageResource(+R.raw.exer_fireflower)
        Glide.with(this).load(R.raw.exer_fireflower).override(1000, 1000).into(endIv)//gif형식 파일 사용


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
                        if(intent.hasExtra("save")) {
                            var saveCount: String
                            saveCount = intent.getStringExtra("save").toString()
                            if(intent.hasExtra("Begin")){user_data.exp += ((500 + (saveCount.toInt() * 10)).toLong())}//추가 11.09 난이도별 경험치 세분화
                            else if(intent.hasExtra("Normal")){user_data.exp += ((600 + (saveCount.toInt() * 10)).toLong())}//추가 11.09 난이도별 경험치 세분화
                            else if(intent.hasExtra("Hard")){user_data.exp += ((700 + (saveCount.toInt() * 10)).toLong())}//추가 11.09 난이도별 경험치 세분화
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

    override fun onBackPressed() { //뒤로가기 버튼 막기
    }

}