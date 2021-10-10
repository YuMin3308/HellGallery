package com.example.logo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

//IntroLayout.kt
// mainfests에서 <activity>를 IntroLayout으로 수정되었음
// 한번씩 확인할 것
// 처음 앱 실행시 나오는 로고 호마ㅕㄴ

class IntroLayout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.intro_layout)

        // 여기서부터 로고 이미지 관련 코드
        var handler = Handler()
        handler.postDelayed({
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, 3000) //인트로 시간설정 3000 -> 3초
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
