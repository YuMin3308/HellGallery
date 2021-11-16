package com.example.logo.Community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.logo.FireStore
import com.example.logo.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Community_main : AppCompatActivity() {

    private val TAG = Community_main::class.java.simpleName
    private val boardDataList = mutableListOf<Model>()
    private val boardKeyList = mutableListOf<String>()
    private lateinit var Adapter: CmListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_main)

        val listView = findViewById<ListView>(R.id.BoardlistView)
        Adapter = CmListAdapter(boardDataList)
        listView.adapter = Adapter

        listView.setOnItemClickListener { parent, view, position, id -> // 유민_추가 리스트뷰 클릭시
            val titleSend = Adapter.getTitle(position)
            val contentSend = Adapter.getContent(position)
            val cidSend = Adapter.getCid(position)


//            val intentDetailPost = Intent(this, Community_Deatil_PostActivity::class.java)
//            intentDetailPost.putExtra("title",titleSend)
//            intentDetailPost.putExtra("content",contentSend)
//            startActivity(intentDetailPost)

            val intentDetailPost = Intent(this, Community_Deatil_PostActivity::class.java)
            intentDetailPost.putExtra("key", boardKeyList[position])
            intentDetailPost.putExtra("id", intent.getStringExtra("id"))
            intentDetailPost.putExtra("cid",cidSend)
            startActivity(intentDetailPost)
          }


        val searchButton = findViewById<Button>(R.id.searchButton) // 검색 버튼_유민추가
        searchButton.setOnClickListener {
            searchStart() // 검색 버튼 누를시 입력한 이메일 사용자의 글이 올라옴
        }

        val writeBtn: View = findViewById(R.id.writeBtn) //게시글 작성 버튼
        writeBtn.setOnClickListener {
            val intentCommuMain = Intent(this, Community_PostActivity::class.java)
            intentCommuMain.putExtra("id", intent.getStringExtra("id"))
            startActivity(intentCommuMain)
        }
        getFBBoardData()
        getFireStoreData() // 유민 추가_유저 데이터 파이어 스토어에서 가져오기
    }



    private fun searchStart() { // 검색 버튼_유민추가
        val getEditText = findViewById<EditText>(R.id.searchEdit)

        val postListener = object : ValueEventListener { // 상렬이가 쓴거 그대로 가져옴
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                boardDataList.clear()

                for (dataModel in dataSnapshot.children) {
                    Log.d(TAG, dataModel.toString())


                    val item = dataModel.getValue(Model::class.java)
                    if (item != null) {
                        if(getEditText.text.toString() == item.uid) { // EditText에 쓴 이메일값과 데이터베이스 uid값과 비교
                            boardDataList.add(item!!) // 같으면 리스트 갱신
                        }
                        else if(getEditText.text.toString().length == 0) // 검색칸을 비우고 버튼 누르면 다시 전체 검색
                            getFBBoardData()
                    }
                }
                Adapter.notifyDataSetChanged()

                Log.d(TAG, boardDataList.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBrtbe.noticeboard.addValueEventListener(postListener)
    }


    private fun getFBBoardData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                boardDataList.clear()

                for (dataModel in dataSnapshot.children) {
                    Log.d(TAG, dataModel.toString())

                    val item = dataModel.getValue(Model::class.java)
                    boardDataList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())
                }
                boardDataList.reverse()
                boardKeyList.reverse()

                Adapter.notifyDataSetChanged()

                Log.d(TAG, boardDataList.toString())

            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        FBrtbe.noticeboard.addValueEventListener(postListener)
    }


    private fun getFireStoreData() {
        val db = Firebase.firestore

        //파이어 스토어 데이터 가져오기
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

                    levelBar(user_data.level, user_data.exp) // 레벨 정보 함수


                    val userCodeView = findViewById<TextView>(R.id.commuEmail)
                    userCodeView.setText(user_data.email)

                    if (user_data.email.equals(intent.getStringExtra("id")))
                        break // 로그인한 이메일 찾았으면 데이터베이스 탐색중지
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }


    private fun levelBar(level : Long,exp : Long) : Unit { // 유민추가_ 경험치바 갱신
        val levelPBar = findViewById<ProgressBar>(R.id.levelCommuBar)
        val currentExper = findViewById<TextView>(R.id.currentExperCommu)
        val levelView = findViewById<TextView>(R.id.levelNumberTextCommu)



        levelView.setText(level.toString())
        currentExper.setText(exp.toString())
        levelPBar.setIndeterminate(false)
        levelPBar.setProgress(exp.toInt())
    }
}
