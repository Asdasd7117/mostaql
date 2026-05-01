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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val emailIn = findViewById<EditText>(R.id.email)
        val passIn = findViewById<EditText>(R.id.password)
        val confirmIn = findViewById<EditText>(R.id.confirmPassword)
        val btn = findViewById<Button>(R.id.registerBtn)
        val loader = findViewById<ProgressBar>(R.id.progressBar)

        btn.setOnClickListener {
            val m = emailIn.text.toString().trim()
            val p = passIn.text.toString().trim()
            if (p != confirmIn.text.toString().trim()) {
                Toast.makeText(this, "كلمات المرور غير متطابقة", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loader.visibility = View.VISIBLE
            lifecycleScope.launch {
                try {
                    SupabaseClient.client.auth.signUpWith(Email) {
                        email = m
                        password = p
                    }
                    Toast.makeText(this@RegisterActivity, "تم بنجاح", Toast.LENGTH_SHORT).show()
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_SHORT).show()
                } finally {
                    loader.visibility = View.GONE
                }
            }
        }
    }
}
