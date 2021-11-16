package com.example.logo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.logo.Community.Community_main
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

/*
InterfaceMain은 앱의 메인화면으로 사용
 */
class InterfaceMain: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth; // 파이어베이스
    private val TAG: String = "InterfaceMain"
    val db = Firebase.firestore

    var mainList = arrayListOf<MainList>(
        MainList(R.drawable.exer_button_img),
        MainList(R.drawable.exer_report_img),
        MainList(R.drawable.exer_community_img),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //파이어 스토어 데이터 가져오기
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) { // 가져온 문서들이 result로 들어감
                    Log.d(TAG, "${document.id} => ${document.data}")
                    //유저 데이터 가져오기
                    val user_data = FireStore(document["email"] as String,
                    document["exp"] as Long, document["level"] as Long,
                    document["height"] as Long, document["weight"] as Long, document["count"] as Long,
                        document["time"] as String, document["nextTime"] as String)//11.15추가

                    levelBar(user_data.level, user_data.exp) // 레벨 정보 함수


                    val userCodeView = findViewById<TextView>(R.id.userCode)
                    userCodeView.setText(user_data.email)

                    if(user_data.email.equals(intent.getStringExtra("id")))
                        break // 로그인한 이메일 찾았으면 데이터베이스 탐색중지
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


        // 리스트뷰 시작 -
        val listView = findViewById<ListView>(R.id.mainList)
        val Adapter = MainListAdapter(this,mainList)
        listView.adapter = Adapter
        // 리스트뷰 끝 -


        // 리스트뷰 선택했을때 토스트 메세지
        listView.setOnItemClickListener { parent, view, position, id ->

            if(position == 0) {
               val intentInterface = Intent(this, exerActivity::class.java)
                intentInterface.putExtra("id",intent.getStringExtra("id"))
                intentInterface.putExtra("gift",intent.getStringExtra("gift"))//레벨달성 선물
                startActivity(intentInterface)
            }

            else if(position == 1) {
                val intentInterface = Intent(this, reportActivity::class.java)
                intentInterface.putExtra("id",intent.getStringExtra("id"))
                startActivity(intentInterface)
            }

            //상렬추가
            else if(position == 2) {
                val intentInterface = Intent(this, Community_main::class.java)
                intentInterface.putExtra("id",intent.getStringExtra("id"))
                startActivity(intentInterface)
            }


        } // 클릭이벤트가 발생했는지 알아보기위해 토스트 메세지를 넣은것임
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

    override fun onBackPressed() { //뒤로가기 버튼 막기
    }

}