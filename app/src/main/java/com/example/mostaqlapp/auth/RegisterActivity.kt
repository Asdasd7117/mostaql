package com.example.mostaqlapp.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
// الربط الصحيح مع الموارد والبيانات
import com.example.mostaqlapp.R
import com.example.mostaqlapp.databinding.ActivityRegisterBinding // إذا كنت تستخدم ViewBinding
import com.example.mostaqlapp.data.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    // استخدام أسماء واضحة للمكونات لتطابق الـ XML
    private lateinit var emailField: android.widget.EditText
    private lateinit var passwordField: android.widget.EditText
    private lateinit var confirmPasswordField: android.widget.EditText
    private lateinit var registerButton: android.widget.Button
    private lateinit var loadingBar: android.widget.ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 1. ربط العناصر (Initialization)
        initViews()

        // 2. إعداد المستمعات (Listeners)
        registerButton.setOnClickListener {
            handleRegistration()
        }
    }

    private fun initViews() {
        emailField = findViewById(R.id.email)
        passwordField = findViewById(R.id.password)
        confirmPasswordField = findViewById(R.id.confirmPassword)
        registerButton = findViewById(R.id.registerBtn)
        loadingBar = findViewById(R.id.progressBar)
    }

    private fun handleRegistration() {
        val email = emailField.text.toString().trim()
        val password = passwordField.text.toString().trim()
        val confirmPassword = confirmPasswordField.text.toString().trim()

        // التحقق من المدخلات (Validation)
        if (!isInputValid(email, password, confirmPassword)) return

        // بدء عملية التسجيل
        toggleLoading(true)
        
        lifecycleScope.launch {
            try {
                // تنفيذ التسجيل في Supabase
                SupabaseClient.client.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                }

                showToast("تم إنشاء الحساب، يرجى إدخال رمز التحقق")
                navigateToVerifyOtp(email)

            } catch (e: Exception) {
                showToast("خطأ: ${e.localizedMessage}")
            } finally {
                toggleLoading(false)
            }
        }
    }

    private fun isInputValid(email: String, pass: String, confirm: String): Boolean {
        return when {
            email.isEmpty() || pass.isEmpty() -> {
                showToast("يرجى ملء جميع الحقول")
                false
            }
            pass != confirm -> {
                showToast("كلمات المرور غير متطابقة")
                false
            }
            pass.length < 6 -> {
                showToast("كلمة المرور قصيرة جداً")
                false
            }
            else -> true
        }
    }

    private fun navigateToVerifyOtp(email: String) {
        val intent = Intent(this, VerifyOtpActivity::class.java).apply {
            putExtra("email", email)
        }
        startActivity(intent)
        finish()
    }

    private fun toggleLoading(isLoading: Boolean) {
        loadingBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        registerButton.isEnabled = !isLoading
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
