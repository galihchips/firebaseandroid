package com.upj.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
            Intent(this@MainActivity, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun loginUser(email : String, password : String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    Intent(this@MainActivity, HomeActivity::class.java).also {
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        txtEmail.text = ""
                        txtPassword.text = ""
                    }
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onStart() {
        super.onStart()
        if (auth.currentUser !== null) {
            Intent(this@MainActivity, HomeActivity::class.java).also {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }
}