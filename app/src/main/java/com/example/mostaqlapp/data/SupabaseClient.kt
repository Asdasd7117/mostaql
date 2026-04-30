package com.example.mostaql.data

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseClient {

    val client = createSupabaseClient(
        supabaseUrl = "https://ojposlwuwtvzypgpdemm.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9qcG9zbHd1d3R2enlwZ3BkZW1tIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzc0NDk5MzgsImV4cCI6MjA5MzAyNTkzOH0.ApdGwZvuzr5IgtSvEK_F3thY5FNsFR7qG6_mcy89eQ0"
    ) {

        // ✅ تفعيل الموديولات بشكل صحيح
        install(Auth)
        install(Postgrest)
    }
}
