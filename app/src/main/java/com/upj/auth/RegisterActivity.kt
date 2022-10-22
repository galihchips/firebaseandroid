package com.upj.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity: AppCompatActivity() {
    private  lateinit var btnSimpan : Button
    private  lateinit var btnLogin : Button
    private  lateinit var txtEmail : TextView
    private  lateinit var txtPassword : TextView
    private  lateinit var auth : FirebaseAuth
    private lateinit var txtalreadyHaveAcc : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        txtEmail = findViewById(R.id.etEmailReg)
        txtPassword = findViewById(R.id.etPasswordReg)
        btnSimpan = findViewById(R.id.btnSimpan)

        txtalreadyHaveAcc = findViewById(R.id.tvAlreadyHaveAccount)
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
        txtalreadyHaveAcc.setOnClickListener {
            finish();
            super.onBackPressed();
        }

    }

    private fun registerUser(email : String, password : String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Intent(this@RegisterActivity, MainActivity::class.java).also {
                        startActivity(it)
                    }
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser !== null) {
            Intent(this@RegisterActivity, HomeActivity::class.java).also {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}