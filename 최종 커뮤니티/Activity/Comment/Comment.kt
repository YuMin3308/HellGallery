package com.example.logo.Community.Comment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.logo.Community.FBAuth
import com.example.logo.Community.FBrtbe
import com.example.logo.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Comment : AppCompatActivity() {
    private val CommentDataList = mutableListOf<CommentModel>()
    private lateinit var Adapter: CommentAdapter
    private val mDatabase = FirebaseDatabase.getInstance()
    private val commentReference = mDatabase.getReference("comment") // 댓글 트리 경로

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comment_layout)

        val commentText = findViewById<EditText>(R.id.commentAddArea)
        val commentAdd = findViewById<Button>(R.id.commentAddButton)
        commentAdd.setOnClickListener { // 댓글 추가 누를시

            val content = commentText.text.toString()
            val uid = intent.getStringExtra("id").toString() // 사용자 이메일로
            val time = FBAuth.getTime()
            val pushed = FBrtbe.noticeboard.push() // 경로 + 중복되지 않는 값 만들기
            val cid = pushed.key.toString() // 앞 경로명을 다 자르고 중복되지 않는 값만 가져오기

            CommentRef.comment.child(intent.getStringExtra("cid").toString())
                .push() // .push()는 절대 중복되지 않은 임의의 문자열을 만드는것으로 생각하고 있다...
                .setValue(CommentModel(cid, content, uid, time))

            Toast.makeText(this, "댓글 입력완료!", Toast.LENGTH_SHORT).show()
            commentText.setText("")
        }

        val listView = findViewById<ListView>(R.id.commentList)
        Adapter = CommentAdapter(CommentDataList)
        listView.adapter = Adapter

        listView.setOnItemClickListener { parent, view, position, l -> // 댓글 리스트뷰 클릭시
            if(Adapter.getEmail(position) == intent.getStringExtra("id")) { // 자기가 작성한 댓글이 아니면 클릭해서 아무 반응없음
                val builder = AlertDialog.Builder(this) // 댓글 클릭시 뜨는 경고창
                builder.setTitle("댓글 삭제") // 경고창 타이틀
                    .setMessage("댓글을 삭제 하시겠습니까 ?") // 경고창 메세지
                    .setPositiveButton("예", DialogInterface.OnClickListener { dialogInterface, i ->
                        val cid = Adapter.getCid(position) // 이 cid는 댓글 작성한 사용자의 cid임 --> 리얼타임 데베 확인
                        val email = Adapter.getEmail(position) // 댓글 작성한 사용자의 email

                        commentReference.addValueEventListener(object : ValueEventListener {  // 데베에서 데이터 읽어오기
                            override fun onDataChange(dataSnapshot: DataSnapshot) {

                                for (dataModel in dataSnapshot.child(intent.getStringExtra("cid").toString()).children) { // 이 경로는 comment -> cid 임
                                    val item = dataModel.getValue(CommentModel::class.java) // 아이템 받아오고
                                    if (cid == item?.cid && email == intent.getStringExtra("id")) { // 댓글의 cid와 선택한 댓글의 cid가 같고, 이메일까지 같다면
                                        val ref = dataModel.key.toString() // dataMode.key는 (comment -> cid)의 경로를 문자열로 받아옴
                                        val dataRef = commentReference
                                            .child(intent.getStringExtra("cid").toString()).child(ref) // comment-> cid의 child는 comment -> cid -> cid임
                                        dataRef.setValue(null) // 그것을 삭제
                                    }
                                }
                                Adapter.notifyDataSetChanged()
                            }
                            override fun onCancelled(databaseError: DatabaseError) {
                            }
                        })
                    })
                    .setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i -> // 취소 버튼

                    })
                builder.show() // 이거 안하면 경고창 안뜸
            }
        }
        getCommentList() // 댓글 추가


    }

    private fun getCommentList() { // 댓글리스트 보이게 하기
        commentReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                CommentDataList.clear()

                for (dataModel in dataSnapshot.child(intent.getStringExtra("cid").toString()).children) {

                    val item = dataModel.getValue(CommentModel::class.java)
                    CommentDataList.add(item!!)
                }
                Adapter.notifyDataSetChanged()

            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

    }
}