package com.example.mostaql.user

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mostaql.R
import com.example.mostaql.auth.LoginActivity

class ChooseRoleActivity : AppCompatActivity() {

    private lateinit var nameInput: EditText
    private lateinit var buyerBtn: Button
    private lateinit var sellerBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_role)

        // 🔗 ربط العناصر حسب XML الحقيقي
        nameInput = findViewById(R.id.name)
        buyerBtn = findViewById(R.id.buyerBtn)
        sellerBtn = findViewById(R.id.sellerBtn)

        // 🟦 مشتري
        buyerBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "اكتب اسمك أولاً", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "مرحباً مشتري $name", Toast.LENGTH_SHORT).show()

            startActivity(
                Intent(this@ChooseRoleActivity, LoginActivity::class.java)
            )
        }

        // 🟩 بائع
        sellerBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()

            if (name.isEmpty()) {
                Toast.makeText(this, "اكتب اسمك أولاً", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "مرحباً بائع $name", Toast.LENGTH_SHORT).show()

            startActivity(
                Intent(this@ChooseRoleActivity, LoginActivity::class.java)
            )
        }
    }
}