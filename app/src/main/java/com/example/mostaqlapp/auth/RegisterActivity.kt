package com.example.mostaqlapp.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.mostaqlapp.R.layout.activity_register)

        val emailField = findViewById<EditText>(com.example.mostaqlapp.R.id.email)
        val passField = findViewById<EditText>(com.example.mostaqlapp.R.id.password)
        val regBtn = findViewById<Button>(com.example.mostaqlapp.R.id.registerBtn)

        regBtn.setOnClickListener {
            val emailText = emailField.text.toString().trim()
            val passText = passField.text.toString().trim()

            lifecycleScope.launch {
                try {
                    com.example.mostaqlapp.data.SupabaseClient.client.auth.signUpWith(Email) {
                        email = emailText
                        password = passText
                    }
                    startActivity(Intent(this@RegisterActivity, com.example.mostaqlapp.auth.VerifyOtpActivity::class.java))
                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
