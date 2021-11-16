package com.example.logo


import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)//버전 정보 맞추기 11.15
class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth; // 파이어베이스
    private val TAG: String = "SignUp"
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)


        //Authentication 데이터베이스 인스턴스 가져오기
        auth = FirebaseAuth.getInstance()


        val email_view = findViewById<EditText>(R.id.emailView) // 이메일 id
        val password_view = findViewById<EditText>(R.id.pwView) // 비밀번호 id
        val sign_up_btn = findViewById<Button>(R.id.joinBtn) // 버튼 id
        val height_view = findViewById<EditText>(R.id.hView) // 키 id
        val weight_view = findViewById<EditText>(R.id.wView) // 몸무게 id



        sign_up_btn.setOnClickListener {
            if (email_view.text.toString().length == 0 || password_view.text.toString().length == 0
                || height_view.text.toString().length == 0 || weight_view.text.toString().length == 0) {
                Toast.makeText(this, "빈칸을 모두 입력해주세요", Toast.LENGTH_SHORT).show()
            }

            else {
                auth.createUserWithEmailAndPassword(email_view.text.toString(), password_view.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information

                            Log.d(TAG, "회원등록 완료")
                            val user = auth.currentUser

                            // 데이터 클래스 값 넣어주기기
                           val user_info = FireStore(email_view.text.toString(),
                            0,0,height_view.text.toString().toLong(),weight_view.text.toString().toLong(),0,"","")//추가11.15

                            val data_add = hashMapOf( // 파이어 스토어에 넣을 데이터값
                                "email" to user_info.email,
                                "exp" to user_info.exp,
                                "level" to user_info.level,
                                "height" to user_info.height,
                                "weight" to user_info.weight,
                                "count" to user_info.count,// count추가 11.15
                                "time" to user_info.time,// time추가 11.15
                                "nextTime" to user_info.nextTime//nexttime추가 11.15
                            )

                            db.collection("users") // 파이어 스토어로 데이터 추가
                                .add(data_add)
                                .addOnSuccessListener { documentReference ->
                                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Error adding document", e)
                                }

                            finish()

                        }

                        else {
                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "회원가입 실패.",
                                Toast.LENGTH_SHORT
                            ).show()

                            email_view.setText("")
                            password_view.setText("")
                            email_view.requestFocus()
                        }
                        //////////////////////추가부분 11.16 회원가입시에 다음날 데이터(nextTime) 세트
                        //다음날 데이터(nextTime)은 최초에만 생성이 되고 이후에는 조건에 의해서만 다른 액티비티에서 변경이됨
                        //로그인시에 세트되는 오늘날짜(time)정보와 달리 횟수 제어를 위해 최초에 한 번만 설정
                        //파이어 스토어 데이터 가져오기
                        db.collection("users")
                            .get()
                            .addOnSuccessListener { result ->
                                for (document in result) { // 가져온 문서들이 result로 들어감
                                    Log.d(TAG, "${document.id} => ${document.data}")
                                    //유저 데이터 가져오기
                                    val user_data = FireStore(
                                        document["email"] as String,
                                        document["exp"] as Long,
                                        document["level"] as Long,
                                        document["height"] as Long,
                                        document["weight"] as Long,
                                        document["count"] as Long,
                                        document["time"] as String,
                                        document["nextTime"] as String
                                    )
                                    var cal = Calendar.getInstance()
                                    val df: DateFormat = SimpleDateFormat("yyyyMMdd")//
                                    cal.time = df.parse("00000001")//
                                    var cal2 = Calendar.getInstance()
                                    cal2.time = Date()
                                    if(email_view.text.toString()==user_data.email) {
                                        cal2.add(Calendar.DATE, cal.get(Calendar.DATE))
                                        user_data.nextTime = df.format(cal2.time).toString()// 회원 가입시 다음날 데이터 삽입
                                        cal2.time = Date()
                                        user_data.time = df.format(cal2.time).toString()
                                        val db_ref = db.collection("users").document(document.id) // 데이터 경로 설정
                                        db_ref.set(user_data) // 수정된 데이터 삽입
                                    }
                                }
                            }

                    }

            } // else
        }
    } // onCreate

    } // class