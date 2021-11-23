package com.example.logo.Community.Comment

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CommentRef {
    companion object {
        private val database = Firebase.database

        val comment = database.getReference("comment")
    }
}