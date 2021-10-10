package com.example.logo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.logo.databinding.ActivityMainBinding

/*
MainActivity는 로그인과 비밀번호 화면입니다
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginWait()


    }

    fun LoginWait() : Unit {
        val login_button = findViewById<Button>(R.id.login)
        val id_edit = findViewById<EditText>(R.id.username)
        val password_edit = findViewById<EditText>(R.id.password)


        login_button.setOnClickListener { // 로그인 버튼 클릭시
            var id_edit_string = id_edit.getText().toString()
            id_edit_string = id_edit_string.trim() // trim() 은 공백제거

            var password_edit_string = password_edit.getText().toString()
            password_edit_string = password_edit_string.trim()


            if(id_edit_string.length <=0 || password_edit_string.length <=0) { // 입력안할시
                Toast.makeText(this,"아이디, 비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show()
            }
            else {
                var intent = Intent(this, InterfaceMain::class.java)
                startActivity(intent)
            }


        }
    }
}
