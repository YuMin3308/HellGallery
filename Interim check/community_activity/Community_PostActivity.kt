package com.example.logo.Community


import android.nfc.Tag
import android.os.Bundle
import android.system.Os.getuid
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.logo.FireStore
import com.example.logo.R
import com.example.logo.databinding.ActivityCommunityPostBinding


class Community_PostActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCommunityPostBinding

    private val TAG = Community_PostActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_post)

        binding.postbBtn.setOnClickListener {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getuid()
            val time = FBAuth.getTime()

            Log.d(TAG, title)
            Log.d(TAG, content)

            FBrtbe.noticeboard
                .push()
                .setValue(Model(title, content, uid, time))

            Toast.makeText(this, "게시글 입력완료!", Toast.LENGTH_LONG).show()

            finish()
        }
    }
}