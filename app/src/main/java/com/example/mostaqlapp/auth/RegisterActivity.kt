package com.example.mostaqlapp.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaqlapp.R
import com.example.mostaqlapp.data.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val emailField = findViewById<EditText>(R.id.email)
        val passField = findViewById<EditText>(R.id.password)
        val confirmPassField = findViewById<EditText>(R.id.confirmPassword)
        val registerBtn = findViewById<Button>(R.id.registerBtn)
        val progress = findViewById<ProgressBar>(R.id.progressBar)

        registerBtn.setOnClickListener {
            val emailText = emailField.text.toString().trim()
            val passText = passField.text.toString().trim()
            val confirmText = confirmPassField.text.toString().trim()

            if (passText != confirmText) {
                Toast.makeText(this, "كلمات المرور غير متطابقة", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progress.visibility = View.VISIBLE
            lifecycleScope.launch {
                try {
                    SupabaseClient.client.auth.signUpWith(Email) {
                        email = emailText
                        password = passText
                    }
                    Toast.makeText(this@RegisterActivity, "تم التسجيل بنجاح", Toast.LENGTH_SHORT).show()
                    finish() // العودة لشاشة اللوجن
                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, "خطأ: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    progress.visibility = View.GONE
                }
            }
        }
    }
}
