package com.example.logo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class exerActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth; // 파이어베이스
    private val TAG: String = "InterfaceMain"
    val db = Firebase.firestore
    //////////////////////////////////////11.15 빌드버전 맞추기
    @RequiresApi(Build.VERSION_CODES.O)
    ///////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exer)


        //var expLvTv=findViewById(R.id.expLvTv) as TextView //경험치창
        var beginBtn:ImageView = findViewById(R.id.beginBtn) //begin난이도 버튼
        var normalBtn:ImageView = findViewById(R.id.normalBtn) //normal난이도 버튼
        var hardBtn:ImageView = findViewById(R.id.hardBtn) //hard난이도 버튼
        var menubtn:Button =findViewById(R.id.menuBtn) //메뉴버튼
        //파이어 스토어 데이터 가져오기
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) { // 가져온 문서들이 result로 들어감
                    Log.d(TAG, "${document.id} => ${document.data}")

                    //유저 데이터 가져오기
                    val user_data = FireStore(document["email"] as String,
                        document["exp"] as Long, document["level"] as Long,
                        document["height"] as Long, document["weight"] as Long, document["count"] as Long
                        ,document["time"] as String, document["nextTime"] as String)//추가11.15

                    if(user_data.email.equals(intent.getStringExtra("id"))) { // 로그인한 유저의 데이터베이스 찾기
                        val userCodeView = findViewById<TextView>(R.id.userCode)
                        userCodeView.setText(user_data.email)

                        if (user_data.level < 10) { // 레벨당 중급, 고급운동 잠금
                            normalBtn.setEnabled(false)
                            normalBtn.setImageResource(R.drawable.normal_lock_img)

                            hardBtn.setEnabled(false)
                            hardBtn.setImageResource(R.drawable.hard_lock_img)
                        } else if (user_data.level < 20) {
                            hardBtn.setEnabled(false)
                            hardBtn.setImageResource(R.drawable.hard_lock_img)
                        }

                        levelBar(user_data.level, user_data.exp) // 레벨 정보 함수
                        //////////////////////////////////////////////////////////////
                        // 11.15 치팅방지 파이어스토어 DB의 시간데이터와 운동을 시작하는 시점에서 시간을 비교하여 같은 날짜의 운동횟수를 2번으로 제한
                        // 시간 감시는 최초접속시 운동시에 운동 당일 날짜(time)보다 다음날 데이터(nextTime)가 커야한다.
                        // 이 조건에 위반될시 이는 접속날짜(time)가 저번 다음운동날짜(nextTime)보다 최신이라는 의미이다.(ex 11.15일에 접속 저번운동 날짜는11.13 위반발생 초기화시작)
                        // 그렇게 되면 시스템은 다음날(nextTime)을 현재시간(time)+1을 해주고 현재날짜(time)보다 큰 상태로 유지시킨다.(11.15에 접속 데이터(time)+1로 다음날 데이터(nextTime) 저장)
                        // 위 조건에 맞게 시간데이터가 돌아간다면 다음은 카운트를 들여다본다. 카운트(count)는 일 2회로 그 이상을 실행하려하면 조건에 걸려 운동 기능들을 잠그게 된다.
                        // 위의 위반 사례에 의해 날짜 데이터(nextTime)가 변경된다면 최소한 다음날이 되었다는 의미로 해당 변경과 함께 카운트(count)가 다시 0으로 초기화 된다.
                        var cal1 = Calendar.getInstance() //시간 설정
                        val df: DateFormat = SimpleDateFormat("yyyyMMdd")
                        cal1.time = Date()
                        var now= df.format(cal1.time).toString()
                        if(user_data.nextTime.toInt()<=(now.toInt())){//현재 시간이 경과하여 다음날 DB데이터 nextTime보다 클때 카운트 초기화 및 다음날/당일 데이터 다시 세팅
                            //////////////////////핵심 추가 부분 11.15 날짜 계산 /횟수제한
                            //cal은 1일 cal2는 현재시간 +1일
                            var cal = Calendar.getInstance() //시간 설정
                            val df: DateFormat = SimpleDateFormat("yyyyMMdd") //데이터 포맷//
                            cal.time = df.parse("00000001") //1일 추가용 01일 변수//
                            var cal2 = Calendar.getInstance()
                            cal2.time = Date()//현재시간
                            if(intent.getStringExtra("id")==user_data.email) {
                                cal2.add(Calendar.DATE, cal.get(Calendar.DATE))//cal2에 현재시간에 +1일
                                user_data.nextTime = df.format(cal2.time).toString() //유저 DB데이터 다음날짜를 다시 설정
                                cal2.time = Date() //cal2는 다시 현재시간으로
                                user_data.time = df.format(cal2.time).toString()//유저 DB데이터 현재날짜를 다시 설정
                                user_data.count = 0

                                val db_ref = db.collection("users").document(document.id) // 데이터 경로 설정
                                db_ref.set(user_data) // 수정된 데이터 삽입
                            }
                        }
                        else if((now.toString()==user_data.time.toString())&&(user_data.count >1)){//DB와 운동하는 현재시간 날짜 체크 같다면 DB의 카운트를 봄
                            beginBtn.setEnabled(false)
                            beginBtn.setImageResource(R.drawable.normal_lock_img)
                            normalBtn.setEnabled(false)
                            normalBtn.setImageResource(R.drawable.normal_lock_img)
                            hardBtn.setEnabled(false)
                            hardBtn.setImageResource(R.drawable.hard_lock_img)
                            Toast.makeText(this, "운동은 하루 2회까지", Toast.LENGTH_SHORT).show()
                        }

                        /////////////////////////////////////////////////////////////////////////////////////////////
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
        menubtn.setVisibility(View.INVISIBLE)//11.16 버튼 없애기
        if(intent.getStringExtra("gift")=="normalTicket"){
            menubtn.setVisibility(View.VISIBLE)//11.16 버튼 보이기
        }
        else if(intent.getStringExtra("gift")=="hardTicket"){
            menubtn.setVisibility(View.VISIBLE)//11.16 버튼 보이기
        }
        menubtn.setOnClickListener {
            val intentExer = Intent(this,MenuActivity::class.java)//다음 화면으로이동하기 위한 인텐트 객체 생성.
            intentExer.putExtra("gift",intent.getStringExtra("gift"))
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