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

    private lateinit var email: EditText
    private lateinit var pass: EditText
    private lateinit var loginBtn: Button
    private lateinit var forgotBtn: Button
    private lateinit var registerBtn: Button
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. ربط التصميم XML
        setContentView(R.layout.activity_login)

        // 2. ربط العناصر
        email = findViewById(R.id.email)
        pass = findViewById(R.id.password)
        loginBtn = findViewById(R.id.loginBtn)
        forgotBtn = findViewById(R.id.forgotBtn)
        registerBtn = findViewById(R.id.btnGoToRegister) // تأكد من الـ ID في XML
        progress = findViewById(R.id.progressBar)

        // 3. زر تسجيل الدخول
        loginBtn.setOnClickListener {
            val emailText = email.text.toString().trim()
            val passText = pass.text.toString().trim()

            if (emailText.isEmpty() || passText.isEmpty()) {
                Toast.makeText(this, "املأ جميع الحقول", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progress.visibility = View.VISIBLE
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
                    Toast.makeText(this@LoginActivity, "خطأ: ${e.message}", Toast.LENGTH_LONG).show()
                } finally {
                    progress.visibility = View.GONE
                    loginBtn.isEnabled = true
                }
            }
        }

        // 4. الانتقال لإنشاء حساب
        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        
        // 5. زر نسيت كلمة المرور (تأكد من وجود الملف أو احذفه إذا لم يكن جاهزاً)
        forgotBtn.setOnClickListener {
            // startActivity(Intent(this, ForgotPasswordActivity::class.java))
            Toast.makeText(this, "قريباً...", Toast.LENGTH_SHORT).show()
        }
    }
}
