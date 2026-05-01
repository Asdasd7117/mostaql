package com.example.mostaqlapp.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaqlapp.R
import com.example.mostaqlapp.data.SupabaseClient
import com.example.mostaqlapp.user.ChooseRoleActivity
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var emailField: EditText
    private lateinit var passField: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerBtn: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailField = findViewById(R.id.email)
        passField = findViewById(R.id.password)
        loginBtn = findViewById(R.id.loginBtn)
        registerBtn = findViewById(R.id.btnGoToRegister)
        progressBar = findViewById(R.id.progressBar)

        loginBtn.setOnClickListener {
            val mail = emailField.text.toString().trim()
            val pw = passField.text.toString().trim()

            if (mail.isEmpty() || pw.isEmpty()) return@setOnClickListener

            progressBar.visibility = View.VISIBLE
            lifecycleScope.launch {
                try {
                    SupabaseClient.client.auth.signInWith(Email) {
                        email = mail
                        password = pw
                    }
                    startActivity(Intent(this@LoginActivity, ChooseRoleActivity::class.java))
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "خطأ: ${e.message}", Toast.LENGTH_SHORT).show()
                } finally {
                    progressBar.visibility = View.GONE
                }
            }
        }

        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
