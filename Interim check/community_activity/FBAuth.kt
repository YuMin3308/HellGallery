package com.example.logo.Community

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.KOREA

class FBAuth {
    companion object {
        private lateinit var auth: FirebaseAuth

        fun getuid() : String {

            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.uid.toString()
        }

        fun getTime() : String {

            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy.mm.dd HH:mm:ss", Locale.KOREA).format(currentDateTime)

            return dateFormat
        }
    }
}