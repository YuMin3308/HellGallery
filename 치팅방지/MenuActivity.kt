package com.example.logo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        var celebration=findViewById<TextView>(R.id.celebration)
        if(intent.hasExtra("gift")){
            celebration.setText(intent.getStringExtra("gift").toString())
        }
    }
}