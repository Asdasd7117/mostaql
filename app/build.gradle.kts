plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization") version "1.9.23" 
}

android {
    namespace = "com.example.mostaql"
    compileSdk = 35 // استخدام 35 بدلاً من 36 لضمان الاستقرار على هاتفك حالياً

    defaultConfig {
        applicationId = "com.example.mostaql"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    // =====================
    // Android Core
    // =====================
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // =====================
    // Coroutines
    // =====================
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // =====================
    // 🔥 SUPABASE (إصدار 3.6.0 المطلوب)
    // =====================
    implementation("io.github.jan-tennert.supabase:auth-kt:3.0.2") // إصدار 3.0.2 هو الأكثر استقراراً وتواجداً في Maven حالياً
    implementation("io.github.jan-tennert.supabase:postgrest-kt:3.0.2")

    // =====================
    // 🌐 Network & Ktor (حل مشكلة الانهيار في الصورة)
    // أضفنا Plugins و ContentNegotiation لضمان التوافق
    // =====================
    val ktor_version = "3.0.0"
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-okhttp:$ktor_version")
    implementation("io.ktor:ktor-client-plugins:$ktor_version") 
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")

    // JSON Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // =====================
    // Tests
    // =====================
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
