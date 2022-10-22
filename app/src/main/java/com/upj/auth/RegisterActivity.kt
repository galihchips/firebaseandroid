package com.upj.auth

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity: AppCompatActivity() {
    private  lateinit var btnSimpan : Button
    private  lateinit var btnLogin : Button
    private  lateinit var txtEmail : TextView
    private  lateinit var txtPassword : TextView
    private  lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        txtEmail = findViewById(R.id.etEmailReg)
        txtPassword = findViewById(R.id.etPasswordReg)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnLogin = findViewById(R.id.btnLoginReg)

        btnSimpan.setOnClickListener {
            val email = txtEmail.text.toString().trim()
            val password = txtPassword.text.toString().trim()

            if(email.isEmpty()) {
                txtEmail.error = "Email cant be empty"
                txtEmail.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty() || password.length < 6) {
                txtPassword.error = "Password cant be empty and less than 6 char"
                txtPassword.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                txtEmail.error = "Format email wrong"
                txtEmail.requestFocus()
                return@setOnClickListener
            }
            registerUser(email, password)
        }

    }
}