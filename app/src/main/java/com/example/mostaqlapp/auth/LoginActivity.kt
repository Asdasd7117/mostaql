package com.example.mostaql.auth

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaql.R
import com.example.mostaql.data.SupabaseClient
import com.example.mostaql.user.ChooseRoleActivity
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch
import io.github.jan.supabase.auth.auth

    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var loginBtn: Button
    private lateinit var forgotBtn: Button
    private lateinit var registerBtn: Button
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ربط التصميم XML
        setContentView(R.layout.activity_login)

        // ربط العناصر
        email = findViewById(R.id.email)
        pass = findViewById(R.id.password)
        loginBtn = findViewById(R.id.loginBtn)
        forgotBtn = findViewById(R.id.forgotBtn)
        registerBtn = findViewById(R.id.registerBtn)

        // (اختياري) لو أضفت ProgressBar في XML
        progress = findViewById(R.id.progressBar)

        // تسجيل الدخول
        loginBtn.setOnClickListener {

            val emailText = email.text.toString().trim()
            val passText = pass.text.toString().trim()

            if (emailText.isEmpty() || passText.isEmpty()) {
                Toast.makeText(this, "املأ جميع الحقول", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progress.visibility = ProgressBar.VISIBLE
            loginBtn.isEnabled = false

            lifecycleScope.launch {
                try {
                    SupabaseClient.client.auth.signInWith(Email) {
                        this.email = emailText
                        this.password = passText
                    }

                    Toast.makeText(this@LoginActivity, "تم تسجيل الدخول", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this@LoginActivity, ChooseRoleActivity::class.java))
                    finish()

                } catch (e: Exception) {
                    Toast.makeText(
                        this@LoginActivity,
                        "فشل الدخول: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                } finally {
                    progress.visibility = ProgressBar.GONE
                    loginBtn.isEnabled = true
                }
            }
        }

        // نسيت كلمة المرور
        forgotBtn.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // إنشاء حساب
        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
