package com.example.mostaql.data  // ✅ يتطابق مع namespace في build.gradle

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.postgrest.Postgrest

// ✅ اسم الكائن يجب أن يكون SupabaseClientProvider ليطابق الاستخدام في VerifyOtpActivity
object SupabaseClientProvider {

    // ✅ Lazy initialization لتجنب إنشاء العميل قبل الأوان
    val client: SupabaseClient by lazy {
        createSupabaseClient(
            supabaseUrl = "https://ojposlwuwtvzypgpdemm.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im9qcG9zbHd1d3R2enlwZ3BkZW1tIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzc0NDk5MzgsImV4cCI6MjA5MzAyNTkzOH0.ApdGwZvuzr5IgtSvEK_F3thY5FNsFR7qG6_mcy89eQ0"
        ) {
            install(Auth)      // ✅ لتفعيل المصادقة
            install(Postgrest) // ✅ لتفعيل قاعدة البيانات
        }
    }
}
