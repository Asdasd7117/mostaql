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
// استيراد الموارد والبيانات بشكل مباشر وصحيح
import com.example.mostaqlapp.R
import com.example.mostaqlapp.data.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    // تعريف العناصر بناءً على IDs الموجودة في activity_register.xml
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var confirmPasswordField: EditText
    private lateinit var registerButton: Button
    private lateinit var loadingBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 1. ربط العناصر بالواجهة
        setupViews()

        // 2. إعداد زر التسجيل
        registerButton.setOnClickListener {
            performRegistration()
        }
    }

    private fun setupViews() {
        emailField = findViewById(R.id.email)
        passwordField = findViewById(R.id.password)
        confirmPasswordField = findViewById(R.id.confirmPassword)
        registerButton = findViewById(R.id.registerBtn)
        loadingBar = findViewById(R.id.progressBar)
    }

    private fun performRegistration() {
        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString().trim()
        val confirmPassword = confirmPasswordField.text.toString().trim()

        // التحقق من صحة البيانات
        if (email.isEmpty() || password.isEmpty()) {
            showError("يرجى ملء جميع الحقول")
            return
        }

        if (password != confirmPassword) {
            showError("كلمات المرور غير متطابقة")
            return
        }

        // بدء عملية التسجيل في Supabase
        setLoadingState(true)
        
        lifecycleScope.launch {
            try {
                SupabaseClient.client.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }

                showError("تم التسجيل بنجاح، يرجى التحقق من بريدك")
                
                // الانتقال لشاشة الـ OTP
                val intent = Intent(this@RegisterActivity, VerifyOtpActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
                finish()

            } catch (e: Exception) {
                showError("خطأ: ${e.localizedMessage}")
            } finally {
                setLoadingState(false)
            }
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        registerButton.isEnabled = !isLoading
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
