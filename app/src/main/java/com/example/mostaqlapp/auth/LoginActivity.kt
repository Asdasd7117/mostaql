package com.example.mostaqlapp.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaqlapp.R
import com.example.mostaqlapp.data.SupabaseClient
import com.example.mostaqlapp.user.ChooseRoleActivity
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val emailField = findViewById<EditText>(R.id.email)
        val passField = findViewById<EditText>(R.id.password)
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        val registerBtn = findViewById<Button>(R.id.btnGoToRegister)
        val progress = findViewById<ProgressBar>(R.id.progressBar)

        loginBtn.setOnClickListener {
            val emailText = emailField.text.toString().trim()
            val passText = passField.text.toString().trim()

            if (emailText.isEmpty() || passText.isEmpty()) return@setOnClickListener

            progress.visibility = View.VISIBLE
            lifecycleScope.launch {
                try {
                    SupabaseClient.client.auth.signInWith(Email) {
                        email = emailText
                        password = passText
                    }
                    startActivity(Intent(this@LoginActivity, ChooseRoleActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "خطأ: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    progress.visibility = View.GONE
                }
            }
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
