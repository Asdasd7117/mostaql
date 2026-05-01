package com.example.mostaql.auth

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaql.R
import com.example.mostaql.data.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.OtpType // التصحيح هنا: المسار المباشر لـ OtpType
import kotlinx.coroutines.launch

class VerifyOtpActivity : AppCompatActivity() {

    private lateinit var codeInput: EditText
    private lateinit var verifyBtn: Button
    private lateinit var progress: ProgressBar

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)

        // ربط العناصر بالواجهة
        codeInput = findViewById(R.id.codeInput)
        verifyBtn = findViewById(R.id.verifyBtn)
        progress = findViewById(R.id.progressBar)

        // استقبال الإيميل من الـ Intent
        email = intent.getStringExtra("email") ?: ""

        verifyBtn.setOnClickListener {
            val code = codeInput.text.toString().trim()

            if (code.isEmpty()) {
                Toast.makeText(this, "أدخل رمز التحقق", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // إظهار مؤشر التحميل وتعطيل الزر
            progress.visibility = View.VISIBLE
            verifyBtn.isEnabled = false

            lifecycleScope.launch {
                try {
                    // عملية التحقق من الرمز
                    // ملاحظة: تأكد أن الإصدار لديك يدعم OtpType.Email أو OtpType.Signup حسب نوع العملية
                    SupabaseClient.client.auth.verifyEmailOtp(
                        type = OtpType.Email, 
                        email = email,
                        token = code
                    )

                    Toast.makeText(this@VerifyOtpActivity, "تم التحقق بنجاح", Toast.LENGTH_SHORT).show()
                    
                    // هنا يمكنك الانتقال للشاشة الرئيسية بعد النجاح
                    // val intent = Intent(this@VerifyOtpActivity, MainActivity::class.java)
                    // startActivity(intent)
                    finish()

                } catch (e: Exception) {
                    // عرض رسالة الخطأ في حال فشل التحقق (مثل رمز منتهي الصلاحية)
                    Toast.makeText(this@VerifyOtpActivity, "خطأ: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                } finally {
                    // إعادة الواجهة لحالتها الطبيعية
                    progress.visibility = View.GONE
                    verifyBtn.isEnabled = true
                }
            }
        }
    }
}
