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

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.mostaqlapp.R.layout.activity_login)

        val emailField = findViewById<EditText>(com.example.mostaqlapp.R.id.email)
        val passField = findViewById<EditText>(com.example.mostaqlapp.R.id.password)
        val loginBtn = findViewById<Button>(com.example.mostaqlapp.R.id.loginBtn)
        val registerBtn = findViewById<Button>(com.example.mostaqlapp.R.id.btnGoToRegister)
        val progress = findViewById<ProgressBar>(com.example.mostaqlapp.R.id.progressBar)

        loginBtn.setOnClickListener {
            val emailText = emailField.text.toString().trim()
            val passText = passField.text.toString().trim()

            if (emailText.isEmpty() || passText.isEmpty()) return@setOnClickListener

            progress.visibility = View.VISIBLE
            lifecycleScope.launch {
                try {
                    // مسار كامل لكلاس البيانات
                    com.example.mostaqlapp.data.SupabaseClient.client.auth.signInWith(Email) {
                        email = emailText
                        password = passText
                    }
                    // مسار كامل للانتقال لشاشة الأدوار
                    startActivity(Intent(this@LoginActivity, com.example.mostaqlapp.user.ChooseRoleActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_SHORT).show()
                } finally {
                    progress.visibility = View.GONE
                }
            }
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this, com.example.mostaqlapp.auth.RegisterActivity::class.java))
        }
    }
}
