package com.example.mostaql.auth

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaql.R
import com.example.mostaql.data.SupabaseClient
import com.example.mostaql.user.ChooseRoleActivity
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class VerifyOtpActivity : AppCompatActivity() {

    private lateinit var email: String
    private lateinit var codeInput: EditText
    private lateinit var verifyBtn: Button
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_otp)

        email = intent.getStringExtra("email") ?: ""

        codeInput = findViewById(R.id.codeInput)
        verifyBtn = findViewById(R.id.verifyBtn)
        progress = findViewById(R.id.progressBar)

        verifyBtn.setOnClickListener {

            val code = codeInput.text.toString().trim()

            if (code.isEmpty()) {
                Toast.makeText(this, "أدخل الكود", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            progress.visibility = ProgressBar.VISIBLE

            lifecycleScope.launch {
                try {

                    SupabaseClient.client.auth.verifyEmailOtp(
                        type = Email.OtpType.Email,
                        email = email,
                        token = code
                    )

                    Toast.makeText(this@VerifyOtpActivity, "تم التحقق", Toast.LENGTH_SHORT).show()

                    startActivity(
                        Intent(this@VerifyOtpActivity, ChooseRoleActivity::class.java)
                    )
                    finish()

                } catch (e: Exception) {
                    Toast.makeText(this@VerifyOtpActivity, e.message, Toast.LENGTH_LONG).show()
                } finally {
                    progress.visibility = ProgressBar.GONE
                }
            }
        }
    }
}