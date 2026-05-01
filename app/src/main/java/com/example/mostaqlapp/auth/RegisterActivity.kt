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

            progress.visibility = View.VISIBLE
            registerBtn.isEnabled = false

            lifecycleScope.launch {
                try {
                    // التسجيل في Supabase
                    SupabaseClient.client.auth.signUpWith(Email) {
                        email = emailText
                        password = passText
                    }

                    Toast.makeText(this@RegisterActivity, "تم إرسال رمز التحقق", Toast.LENGTH_SHORT).show()

                    // 🔥 هنا التعديل: نستخدم الاسم كاملاً لضمان التعرف عليه
                    val intent = Intent(this@RegisterActivity, VerifyOtpActivity::class.java)
                    intent.putExtra("email", emailText)
                    startActivity(intent)
                    finish()

                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, "خطأ: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                } finally {
                    progress.visibility = View.GONE
                    registerBtn.isEnabled = true
                }
            }
        }
    }
}
