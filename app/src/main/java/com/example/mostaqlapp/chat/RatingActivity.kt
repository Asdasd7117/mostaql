package com.example.mostaql.chat

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaql.R
import com.example.mostaql.data.SupabaseClient

// 🔥 مهم
import io.github.jan.supabase.postgrest.from

import kotlinx.coroutines.launch

class RatingActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var submitBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        ratingBar = findViewById(R.id.ratingBar)
        submitBtn = findViewById(R.id.submitBtn)

        submitBtn.setOnClickListener {

            val rating = ratingBar.rating

            lifecycleScope.launch {
                try {
                    SupabaseClient.client.from("ratings").insert(
                        mapOf(
                            "rating" to rating
                        )
                    )

                    Toast.makeText(this@RatingActivity, "تم التقييم", Toast.LENGTH_SHORT).show()
                    finish()

                } catch (e: Exception) {
                    Toast.makeText(this@RatingActivity, "خطأ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}