package com.example.teamp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth; // 파이어베이스
    private val TAG: String = "Interf" +
            "aceMain"
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) { // 가져온 문서들이 result로 들어감
                    Log.d(TAG, "${document.id} => ${document.data}")

                    //유저 데이터 가져오기
                    val user_data = FireStore(
                        document["email"] as String,
                        document["exp"] as Long, document["level"] as Long,
                        document["height"] as Long, document["weight"] as Long
                    )
                    val exNum = findViewById<TextView>(R.id.exerNumber)
                    val number = user_data.exp / 500 + (user_data.level-1) * 4
                    exNum.setText(number.toString())

                    progressBar(user_data.level, user_data.exp) // 레벨 정보 함수

                    textUser(user_data.height, user_data.weight)


                    if (user_data.email.equals(intent.getStringExtra("id")))
                        break // 로그인한 이메일 찾았으면 데이터베이스 탐색중지
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


    }

    private fun textUser(height: Long, weight: Long) {
        val tall = findViewById<TextView>(R.id.tall)
        val weigh = findViewById<TextView>(R.id.weigh)
        val bmi = findViewById<TextView>(R.id.bmi)

        val sum = weight / ((height/100.00) * (height/100.00))

        tall.setText(height.toString())
        weigh.setText(weight.toString())
        bmi.setText(sum.toString())
    }

    private fun progressBar(level: Long, exp: Long): Unit {
        val basicBar = findViewById<ProgressBar>(R.id.basicBar)
        val normalBar = findViewById<ProgressBar>(R.id.normalBar)

        val sum = exp + (level-1)*2000


        basicBar.setIndeterminate(false)
        basicBar.setProgress(sum.toInt())

        normalBar.setIndeterminate(false)
        normalBar.setProgress(sum.toInt())
    }

}