package com.example.logo

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/*
InterfaceMain은 앱의 메인화면으로 사용
 */
class InterfaceMain: AppCompatActivity() {

    var mainList = arrayListOf<MainList>(
        MainList(R.drawable.exer_button_img),
        MainList(R.drawable.exer_button_img),
        MainList(R.drawable.exer_button_img),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        levelBar() // 레벨 정보 함수

        // 리스트뷰 시작 -
        val listView = findViewById<ListView>(R.id.mainList)
        val Adapter = MainListAdapter(this,mainList)
        listView.adapter = Adapter
        // 리스트뷰 끝 -


        // 리스트뷰 선택했을때 토스트 메세지
        listView.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this,"${position}번째 클릭됨",Toast.LENGTH_SHORT).show()
        } // 클릭이벤트가 발생했는지 알아보기위해 토스트 메세지를 넣은것임
    }





    private fun levelBar() : Unit {
        val levelPBar = findViewById<ProgressBar>(R.id.levelBar)
        val currentExper = findViewById<TextView>(R.id.currentExper)

        var Exper = Integer.parseInt(currentExper.getText().toString())

        levelPBar.setIndeterminate(false)
        levelPBar.setProgress(Exper)
    }
}
