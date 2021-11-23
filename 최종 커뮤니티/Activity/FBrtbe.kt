package com.example.logo.Community

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

//realtime firebase의 noticeboard에 data저장(연결)
class FBrtbe {
    companion object {
        private val database = Firebase.database

        val noticeboard = database.getReference("noticeboard")

    }
}