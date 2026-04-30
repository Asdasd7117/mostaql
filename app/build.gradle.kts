plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    kotlin("plugin.serialization")
}

android {
    namespace = "com.example.mostaql"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.mostaql"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
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

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // =====================
    // 🔥 SUPABASE (FIXED)
    // =====================
    implementation("io.github.jan-tennert.supabase:bom:3.6.0")

    implementation("io.github.jan-tennert.supabase:auth-kt:3.6.0")
    implementation("io.github.jan-tennert.supabase:postgrest-kt:3.6.0")

    // =====================
    // Network
    // =====================
    implementation("io.ktor:ktor-client-okhttp:2.3.7")

    // JSON
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
