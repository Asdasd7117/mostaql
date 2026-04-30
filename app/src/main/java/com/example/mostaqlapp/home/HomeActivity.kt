package com.example.mostaql.home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mostaql.R
import com.example.mostaql.adapter.ServiceAdapter
import com.example.mostaql.data.SupabaseClient
import com.example.mostaql.data.models.Service
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.from

class HomeActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ServiceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initViews()
        loadServices()
    }

    private fun initViews() {
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        
        // Adapter فارغ مؤقتاً حتى تحميل البيانات
        adapter = ServiceAdapter(emptyList())
        recycler.adapter = adapter
    }

    private fun loadServices() {
        lifecycleScope.launch {
            try {
                val list = SupabaseClient.client.postgrest["services"]
                    .select()
                    .decodeList<Service>()

                adapter = ServiceAdapter(list)
                recycler.adapter = adapter

            } catch (e: Exception) {
                Toast.makeText(
                    this@HomeActivity,
                    "خطأ في تحميل البيانات: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                e.printStackTrace()
            }
        }
    }
}