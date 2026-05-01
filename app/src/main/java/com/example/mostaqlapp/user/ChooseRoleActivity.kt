package com.example.mostaqlapp.user

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.mostaqlapp.R
import com.example.mostaqlapp.auth.LoginActivity

class ChooseRoleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_role)

        val nameField = findViewById<EditText>(R.id.name)
        val bBtn = findViewById<Button>(R.id.buyerBtn)
        val sBtn = findViewById<Button>(R.id.sellerBtn)

        bBtn.setOnClickListener {
            if (nameField.text.toString().isEmpty()) return@setOnClickListener
            startActivity(Intent(this, LoginActivity::class.java))
        }

        sBtn.setOnClickListener {
            if (nameField.text.toString().isEmpty()) return@setOnClickListener
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}
