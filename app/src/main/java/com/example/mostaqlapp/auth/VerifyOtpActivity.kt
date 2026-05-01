package com.example.mostaql.auth  // ✅ 1. Namespace موحد

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaql.data.SupabaseClientProvider  // ✅ 2. مسار صحيح
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.otp.OtpType  // ✅ 3. استيراد صحيح لـ 3.6.0
import kotlinx.coroutines.launch

class VerifyOtpActivity : AppCompatActivity() {

    private lateinit var codeInput: EditText
    private lateinit var verifyBtn: Button
    private lateinit var progress: ProgressBar
    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)  // ✅ سيعمل بعد توحيد الـ namespace

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
                    // ✅ 4. استدعاء صحيح للنسخة 3.6.0
                    SupabaseClientProvider.client.auth.verifyEmailOtp(
                        type = OtpType.Email.EMAIL,
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
