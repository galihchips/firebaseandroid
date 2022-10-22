package com.upj.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private  lateinit var btnLogout : Button
    private  lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        auth = FirebaseAuth.getInstance()
        btnLogout = findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            auth.signOut()
            finish();
            super.onBackPressed();
        }
    }
}