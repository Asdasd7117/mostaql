package com.example.mostaql.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
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

        // ربط العناصر بالواجهة
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.confirmPassword)
        registerBtn = findViewById(R.id.registerBtn)
        progress = findViewById(R.id.progressBar)

        registerBtn.setOnClickListener {

            val emailText = email.text.toString().trim()
            val passText = password.text.toString().trim()
            val confirmText = confirmPassword.text.toString().trim()

            // التحقق من الحقول
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

            // إظهار التحميل وتعطيل الزر
            progress.visibility = View.VISIBLE
            registerBtn.isEnabled = false

            lifecycleScope.launch {
                try {
                    // 1. تنفيذ عملية التسجيل في Supabase
                    SupabaseClient.client.auth.signUpWith(Email) {
                        email = emailText
                        password = passText
                    }

                    Toast.makeText(
                        this@RegisterActivity,
                        "تم إنشاء الحساب، يرجى التحقق من بريدك",
                        Toast.LENGTH_LONG
                    ).show()

                    // 2. 🔥 الانتقال لشاشة التحقق (VerifyOtpActivity)
                    val intent = Intent(this@RegisterActivity, VerifyOtpActivity::class.java)
                    // نمرر الإيميل لكي تستخدمه شاشة التحقق في طلب الـ OTP
                    intent.putExtra("email", emailText) 
                    startActivity(intent)

                    // 3. إغلاق شاشة التسجيل
                    finish()

                } catch (e: Exception) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "خطأ: ${e.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                } finally {
                    // إخفاء التحميل وإعادة تفعيل الزر في كل الأحوال
                    progress.visibility = View.GONE
                    registerBtn.isEnabled = true
                }
            }
        }
    }
}
