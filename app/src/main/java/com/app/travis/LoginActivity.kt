package com.app.travis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.travis.databinding.ActivityLoginBinding
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener { loginAccount() }

        binding.signupQuestion.setOnClickListener {
            Toast
                .makeText(applicationContext, "Go to register activity", Toast.LENGTH_SHORT)
                .show()

            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }
    }

    private fun loginAccount() {
        var email = binding.emailField.text.toString()
        var password = binding.passwordField.text.toString()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var currentUser = auth.currentUser
                    Toast.makeText(applicationContext, "Logged in as ${currentUser?.email}", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Login Failed", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}