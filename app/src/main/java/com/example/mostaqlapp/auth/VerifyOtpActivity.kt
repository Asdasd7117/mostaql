package com.example.mostaqlapp.auth  // ✅ 1. صحح الباكيج

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaqlapp.data.SupabaseClientProvider  // ✅ 2. صحح مسار الاستيراد
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.otp.OtpType  // ✅ 3. أضف استيراد OtpType
import kotlinx.coroutines.launch

class VerifyOtpActivity : AppCompatActivity() {

    private lateinit var codeInput: EditText
    private lateinit var verifyBtn: Button
    private lateinit var progress: ProgressBar
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)  // ✅ R سيعمل بعد إصلاح الباكيج

        codeInput = findViewById(R.id.codeInput)
        verifyBtn = findViewById(R.id.verifyBtn)
        progress = findViewById(R.id.progressBar)

        email = intent.getStringExtra("email") ?: ""

        verifyBtn.setOnClickListener {
            val code = codeInput.text.toString().trim()

            if (code.isEmpty()) {
                Toast.makeText(this, "أدخل رمز التحقق", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progress.visibility = ProgressBar.VISIBLE
            verifyBtn.isEnabled = false

            lifecycleScope.launch {
                try {
                    // ✅ 4. استدعاء صحيح لـ verifyEmailOtp
                    SupabaseClientProvider.client.auth.verifyEmailOtp(
                        type = OtpType.Email.EMAIL,  // ✅ حدد النوع
                        email = email,
                        token = code
                    )

                    Toast.makeText(
                        this@VerifyOtpActivity,
                        "تم التحقق بنجاح",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()

                } catch (e: Exception) {
                    Toast.makeText(
                        this@VerifyOtpActivity,
                        "خطأ: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                } finally {
                    progress.visibility = ProgressBar.GONE
                    verifyBtn.isEnabled = true
                }
            }
        }
    }
}
