package com.example.mostaqlapp.user

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
// استيراد الـ R واللوجن لضمان الربط بين المجلدات
import com.example.mostaqlapp.R
import com.example.mostaqlapp.auth.LoginActivity

class ChooseRoleActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var buyerBtn: Button
    private lateinit var sellerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // ربط واجهة اختيار الدور
        setContentView(R.layout.activity_choose_role)

        // 1. تعريف العناصر من الـ XML
        nameInput = findViewById(R.id.name)
        buyerBtn = findViewById(R.id.buyerBtn)
        sellerBtn = findViewById(R.id.sellerBtn)

        // 2. منطق زر المشتري
        buyerBtn.setOnClickListener {
            handleSelection("مشتري")
        }

        // 3. منطق زر البائع
        sellerBtn.setOnClickListener {
            handleSelection("بائع")
        }
    }

    private fun handleSelection(role: String) {
        val name = nameInput.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "من فضلك اكتب اسمك أولاً", Toast.LENGTH_SHORT).show()
            return
        }

        // يمكنك هنا حفظ الاسم والدور في SharedPreferences إذا أردت لاحقاً
        Toast.makeText(this, "مرحباً بك يا $name (دوران: $role)", Toast.LENGTH_SHORT).show()

        // الانتقال لشاشة تسجيل الدخول
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
