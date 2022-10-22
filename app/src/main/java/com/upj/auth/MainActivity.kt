package com.upj.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private  lateinit var btnRegister : Button
    private  lateinit var btnLogin : Button
    private  lateinit var txtEmail : TextView
    private  lateinit var txtPassword : TextView
    private  lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        txtEmail = findViewById(R.id.etEmail)
        txtPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener{
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            if(email.isEmpty()) {
                txtEmail.error = "Email cant be empty"
                txtEmail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()) {
                txtPassword.error = "Password cant be empty"
                txtPassword.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                txtEmail.error = "Format email wrong"
                txtEmail.requestFocus()
                return@setOnClickListener
            }
            loginUser(email, password)

        }
        btnRegister = findViewById(R.id.btnRegister)
        btnRegister.setOnClickListener {
            intent(this@MainActifity, RegisterActivity::class.java)
        }
    }
}