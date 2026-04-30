package com.example.mostaql.auth

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaql.data.SupabaseClient
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch
import io.github.jan.supabase.auth.auth

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val email = EditText(this)
        email.hint = "اكتب بريدك الإلكتروني"

        val btn = Button(this)
        btn.text = "إرسال رابط الاستعادة"

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(40, 40, 40, 40)

        layout.addView(email)
        layout.addView(btn)

        setContentView(layout)

        btn.setOnClickListener {
            val emailText = email.text.toString()

            if (emailText.isEmpty()) {
                Toast.makeText(this, "اكتب البريد أولاً", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    SupabaseClient.client.auth.resetPasswordForEmail(emailText)

                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "تم إرسال رابط الاستعادة لبريدك",
                        Toast.LENGTH_LONG
                    ).show()

                } catch (e: Exception) {
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "خطأ: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}