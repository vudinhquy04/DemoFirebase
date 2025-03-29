package com.quyvd.demofirebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleApiClient: GoogleApiClient
    private val RC_SIGN_IN = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        var btnSignUp = findViewById<AppCompatButton>(R.id.btnSignUp)
        var btnLoginWithGoogle = findViewById<AppCompatButton>(R.id.btnLoginWithGoogle)

        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = findViewById<EditText>(R.id.edtEmail).text.toString()
            val password = findViewById<EditText>(R.id.edtPassword).text.toString()
            loginUser(email, password)
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this) { Log.e("LoginActivity", "GoogleApiClient connection failed") }
            .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
            .build()


        btnLoginWithGoogle.setOnClickListener {

            googleSignIn()
        }


    }
    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, UserActivity::class.java).apply {
                        putExtra("email", email)
                        putExtra("password", password)
                    }
                    startActivity(intent)
                    finish()
                } else {
                    println("Đăng nhập thất bại: ${task.exception?.message}")
                }
            }
    }

    private fun googleSignIn() {
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val result = data?.let { Auth.GoogleSignInApi.getSignInResultFromIntent(it) }
            if (result?.isSuccess == true) {
                val account = result.signInAccount
                firebaseAuthWithGoogle(account)
            } else {
                Log.e("LoginActivity", "Google Sign-In failed.")
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(acct?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateInfo(user)
                } else {
                    Log.e("LoginActivity", "Firebase Authentication failed.")
                }
            }
    }

    private fun updateInfo(user: FirebaseUser?) {
        user?.let {
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}