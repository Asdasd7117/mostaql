package com.example.mostaqlapp.user // ✅ تم التصحيح ليطابق المسار الفعلي

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChooseRoleActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var buyerBtn: Button
    private lateinit var sellerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 🛠️ استخدام المسار الكامل للـ R لحل مشكلة Unresolved reference
        setContentView(com.example.mostaqlapp.R.layout.activity_choose_role)

        // 🔗 ربط العناصر باستخدام المسارات الكاملة لـ IDs
        nameInput = findViewById(com.example.mostaqlapp.R.id.name)
        buyerBtn = findViewById(com.example.mostaqlapp.R.id.buyerBtn)
        sellerBtn = findViewById(com.example.mostaqlapp.R.id.sellerBtn)

        // 🟦 اختيار دور: مشتري
        buyerBtn.setOnClickListener {
            handleRoleSelection("مشتري")
        }

        // 🟩 اختيار دور: بائع
        sellerBtn.setOnClickListener {
            handleRoleSelection("بائع")
        }
    }

    private fun handleRoleSelection(role: String) {
        val name = nameInput.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "اكتب اسمك أولاً", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(this, "مرحباً بك كـ $role: $name", Toast.LENGTH_SHORT).show()

        // 🚀 استخدام المسار الكامل لـ LoginActivity لضمان وصول الـ Intent
        val intent = Intent(this, com.example.mostaqlapp.auth.LoginActivity::class.java)
        startActivity(intent)
    }
}
