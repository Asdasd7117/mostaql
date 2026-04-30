package com.example.mostaql.auth

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaql.R
import com.example.mostaql.data.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var registerBtn: Button
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.confirmPassword)
        registerBtn = findViewById(R.id.registerBtn)
        progress = findViewById(R.id.progressBar)

        registerBtn.setOnClickListener {

            val emailText = email.text.toString().trim()
            val passText = password.text.toString().trim()
            val confirmText = confirmPassword.text.toString().trim()

            if (emailText.isEmpty() || passText.isEmpty() || confirmText.isEmpty()) {
                Toast.makeText(this, "املأ جميع الحقول", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passText != confirmText) {
                Toast.makeText(this, "كلمة المرور غير متطابقة", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passText.length < 6) {
                Toast.makeText(this, "كلمة المرور يجب أن تكون 6 أحرف على الأقل", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progress.visibility = ProgressBar.VISIBLE
            registerBtn.isEnabled = false

            lifecycleScope.launch {
                try {

                    // ✔️ تسجيل حساب (بدون OTP)
                    SupabaseClient.client.auth.signUpWith(Email) {
                        email = emailText
                        password = passText
                    }

                    Toast.makeText(
                        this@RegisterActivity,
                        "تم إنشاء الحساب بنجاح",
                        Toast.LENGTH_LONG
                    ).show()

                    // يرجع لصفحة الدخول
                    finish()

                } catch (e: Exception) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "خطأ: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                } finally {
                    progress.visibility = ProgressBar.GONE
                    registerBtn.isEnabled = true
                }
            }
        }
    }
}