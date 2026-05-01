package com.example.mostaqlapp.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
// استيراد المكونات الأساسية لـ Supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // استخدام المسار البرمجي المباشر للـ Layout لضمان عدم حدوث Unresolved reference R
        setContentView(com.example.mostaqlapp.R.layout.activity_register)

        // تعريف العناصر باستخدام IDs الموضحة في ملفات الـ XML الخاصة بك
        val emailField = findViewById<EditText>(com.example.mostaqlapp.R.id.email)
        val passwordField = findViewById<EditText>(com.example.mostaqlapp.R.id.password)
        val confirmPasswordField = findViewById<EditText>(com.example.mostaqlapp.R.id.confirmPassword)
        val registerButton = findViewById<Button>(com.example.mostaqlapp.R.id.registerBtn)
        val loadingBar = findViewById<ProgressBar>(com.example.mostaqlapp.R.id.progressBar)

        registerButton.setOnClickListener {
            val emailText = emailField.text.toString().trim()
            val passText = passwordField.text.toString().trim()
            val confirmText = confirmPasswordField.text.toString().trim()

            if (emailText.isEmpty() || passText.isEmpty() || confirmText.isEmpty()) {
                Toast.makeText(this, "املأ جميع الحقول", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (passText != confirmText) {
                Toast.makeText(this, "كلمة المرور غير متطابقة", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loadingBar.visibility = View.VISIBLE
            registerButton.isEnabled = false

            lifecycleScope.launch {
                try {
                    // الوصول المباشر لكلاس SupabaseClient عبر مساره الكامل في مجلد data
                    com.example.mostaqlapp.data.SupabaseClient.client.auth.signUpWith(Email) {
                        email = emailText
                        password = passText
                    }

                    Toast.makeText(this@RegisterActivity, "تم إرسال رمز التحقق لهاتفك/بريدك", Toast.LENGTH_LONG).show()

                    // الانتقال لشاشة التحقق المكتشفة في مجلد auth
                    val intent = Intent(this@RegisterActivity, VerifyOtpActivity::class.java)
                    intent.putExtra("email", emailText)
                    startActivity(intent)
                    finish()

                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, "خطأ: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                } finally {
                    loadingBar.visibility = View.GONE
                    registerButton.isEnabled = true
                }
            }
        }
    }
}
