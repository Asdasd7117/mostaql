package com.example.mostaql

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaql.auth.LoginActivity
import com.example.mostaql.data.SupabaseClient
import com.example.mostaql.home.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import io.github.jan.supabase.auth.auth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // واجهة Splash بسيطة
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER

        val title = TextView(this)
        title.text = "مستقل"
        title.textSize = 28f

        val progress = ProgressBar(this)

        layout.addView(title)
        layout.addView(progress)

        setContentView(layout)

        checkUser()
    }

    private fun checkUser() {
        lifecycleScope.launch {
            try {
                // تأخير بسيط يعطي شكل احترافي (Splash)
                delay(1500)

                val user = SupabaseClient.client.auth.currentUserOrNull()

                if (user != null) {
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                } else {
                    startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                }

                finish()

            } catch (e: Exception) {
                e.printStackTrace()

                // في حال خطأ نوديه تسجيل الدخول
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
            }
        }
    }
}