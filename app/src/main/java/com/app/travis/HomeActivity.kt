package com.app.travis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.travis.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        var currentUser = auth.currentUser

        binding.emailDisplay.text = currentUser?.email
        binding.logoutButton.setOnClickListener { logoutAccount() }
    }

    private fun logoutAccount() {
        auth.signOut()
        startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        finish()
    }
}