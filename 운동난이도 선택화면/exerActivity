package com.example.logo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class exerActivity : AppCompatActivity() { // 운동 선택 화면
    private lateinit var auth: FirebaseAuth; // 파이어베이스
    private val TAG: String = "InterfaceMain"
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exer)


        //var expLvTv=findViewById(R.id.expLvTv) as TextView //경험치창
        var beginBtn:ImageView = findViewById(R.id.beginBtn) //begin난이도 버튼
        var normalBtn:ImageView = findViewById(R.id.normalBtn) //normal난이도 버튼
        var hardBtn:ImageView = findViewById(R.id.hardBtn) //hard난이도 버튼

        //파이어 스토어 데이터 가져오기
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) { // 가져온 문서들이 result로 들어감
                    Log.d(TAG, "${document.id} => ${document.data}")

                    //유저 데이터 가져오기
                    val user_data = FireStore(document["email"] as String,
                        document["exp"] as Long, document["level"] as Long,
                        document["height"] as Long, document["weight"] as Long)

                    if(user_data.email.equals(intent.getStringExtra("id"))) { // 로그인한 유저의 데이터베이스 찾기

                        val userCodeView = findViewById<TextView>(R.id.userCode)
                        userCodeView.setText(user_data.email)

                        levelBar(user_data.level, user_data.exp) // 레벨 정보 함수
                        if (user_data.level < 10) { // 레벨당 중급, 고급운동 잠금
                            normalBtn.setEnabled(false)
                            normalBtn.setImageResource(R.drawable.normal_lock_img)

                            hardBtn.setEnabled(false)
                            hardBtn.setImageResource(R.drawable.hard_lock_img)
                        } else if (user_data.level < 20) {
                            hardBtn.setEnabled(false)
                            hardBtn.setImageResource(R.drawable.hard_lock_img)
                        }
                    }

                }

            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        beginBtn.setOnClickListener{
            val intentExer = Intent(this,BeginActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
            intentExer.putExtra("id",intent.getStringExtra("id"))

            startActivity(intentExer)
        }

        normalBtn.setOnClickListener{
            val intentExer = Intent(this,NormalActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
            intentExer.putExtra("id",intent.getStringExtra("id"))

            startActivity(intentExer)
        }

        hardBtn.setOnClickListener{
            val intentExer = Intent(this,HardActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
            intentExer.putExtra("id",intent.getStringExtra("id"))

            startActivity(intentExer)
        }
    }

    private fun levelBar(level : Long,exp : Long) : Unit {
        val levelPBar = findViewById<ProgressBar>(R.id.levelBar)
        val currentExper = findViewById<TextView>(R.id.currentExper)
        val levelView = findViewById<TextView>(R.id.levelNumberText)



        levelView.setText(level.toString())
        currentExper.setText(exp.toString())
        levelPBar.setIndeterminate(false)
        levelPBar.setProgress(exp.toInt())
    }

}
