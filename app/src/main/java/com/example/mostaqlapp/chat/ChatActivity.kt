package com.example.mostaql.chat

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.mostaql.R
import com.example.mostaql.data.SupabaseClient

// 🔥 أهم imports
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var messageInput: EditText
    private lateinit var sendBtn: Button
    private lateinit var messagesBox: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messageInput = findViewById(R.id.messageInput)
        sendBtn = findViewById(R.id.sendBtn)
        messagesBox = findViewById(R.id.messagesBox)

        sendBtn.setOnClickListener {

            val text = messageInput.text.toString()

            if (text.isEmpty()) return@setOnClickListener

            lifecycleScope.launch {
                try {
                    val user = SupabaseClient.client.auth.currentUserOrNull()

                    SupabaseClient.client.from("messages").insert(
                        mapOf(
                            "user_id" to user?.id,
                            "message" to text
                        )
                    )

                    messagesBox.append("\n$text")
                    messageInput.setText("")

                } catch (e: Exception) {
                    Toast.makeText(this@ChatActivity, "خطأ", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}