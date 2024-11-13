package com.quyvd.demofirebase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val btnSignup = findViewById<Button>(R.id.btnSignup)

        btnSignup.setOnClickListener {
            val email = findViewById<EditText>(R.id.edtEmailSignUp).text.toString()
            val password = findViewById<EditText>(R.id.edtPasswordSignup).text.toString()
            signUpUser(email, password)
        }
    }
    fun signUpUser(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    println("Đăng ký thất bại: ${task.exception?.message}")
                }
            }
    }
}