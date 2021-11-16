package com.example.logo

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.logo.Community.Comment.CommentModel
import com.example.logo.Community.Comment.CommentRef
import com.example.logo.Community.FBAuth
import com.example.logo.Community.FBrtbe
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


/*
MainActivity는 로그인과 비밀번호 화면입니다
 */
class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore//파이어스토어 사용하기위해 추가 11.15
    private val TAG: String = "MainActivity"
    override fun onStart() {
        super.onStart()
        //앱 시작 단계에서 사용자가 현재 로그인 되어 있는지 확인하고 처리

        val currentUser = auth?.currentUser
        if (currentUser != null) Firebase.auth.signOut()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        LoginWait()

    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun LoginWait(): Unit {
        val login_button = findViewById<Button>(R.id.login)
        val id_edit = findViewById<EditText>(R.id.username)
        val password_edit = findViewById<EditText>(R.id.password)
        val signUp_button = findViewById<Button>(R.id.SignUp)


        login_button.setOnClickListener { // 로그인 버튼 클릭시
            var id_edit_string = id_edit.getText().toString()
            id_edit_string = id_edit_string.trim() // trim() 은 공백제거

            var password_edit_string = password_edit.getText().toString()
            password_edit_string = password_edit_string.trim()


            if (id_edit_string.length <= 0 || password_edit_string.length <= 0) { // 입력안할시
                Toast.makeText(this, "아이디, 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                if (id_edit.text.toString().length == 0 || password_edit.text.toString().length == 0) {
                    Toast.makeText(this, "email 혹은 password를 반드시 입력하세요.", Toast.LENGTH_SHORT).show()
                } else {
                    auth.signInWithEmailAndPassword(
                        id_edit.text.toString(),
                        password_edit.text.toString()
                    )
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "로그인 성공")
                                val user = auth.currentUser


                                var intent = Intent(this, InterfaceMain::class.java)
                                intent.putExtra("id", id_edit.text.toString())
                                startActivity(intent)

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                                Toast.makeText(
                                    baseContext, "로그인 실패",
                                    Toast.LENGTH_SHORT
                                ).show()

                                id_edit.setText("");
                                password_edit.setText("")


                            } //else
                        } // signInWith

                }// else
            }// else
            //////////////////////////////////////////////////////////////////////////////11.15 운동 카운팅을 위한 날짜 데이터 셋팅
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
                        )//추가
                        //////////////////////추가부분 11.15 로그인시에 오늘날짜 데이터(time) 세트
                        //로그인시에 세트되는 오늘날짜(time)정보는 매 로그인마다 횟수 제어를 위해 위해 세트됨
                        //다음날 데이터(nextTime)는 최초에만 생성이 되고 이후에는 조건에 의해서만 다른 액티비티에서 변경이됨
                        val df: DateFormat = SimpleDateFormat("yyyyMMdd")//
                        var cal2 = Calendar.getInstance()
                        cal2.time = Date()
                        if(id_edit.text.toString()==user_data.email) {
                            cal2.time = Date()//현재 날짜
                            user_data.time = df.format(cal2.time).toString()
                            val db_ref = db.collection("users").document(document.id) // 데이터 경로 설정
                            db_ref.set(user_data) // 수정된 데이터 삽입
                        }
                    }
                }
            ///////////////////////////////////////////////////////////////////////////
        } // 로그인 이벤트


        signUp_button.setOnClickListener {
            var intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        } // 회원가입 이벤트
    }
}