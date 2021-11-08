package com.example.logo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


/*
MainActivity는 로그인과 비밀번호 화면입니다
 */
class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val TAG: String = "MainActivity"

    override fun onStart() {
        super.onStart()
        //앱 시작 단계에서 사용자가 현재 로그인 되어 있는지 확인하고 처리

        val currentUser = auth?.currentUser
        if (currentUser != null) Firebase.auth.signOut()
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        LoginWait()

    }

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
                                intent.putExtra("id",id_edit.text.toString())
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
                        } // signInWith -

                }// else
            }// else
        } // 로그인 이벤트

        signUp_button.setOnClickListener {
            var intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        } // 회원가입 이벤트
    }
}
