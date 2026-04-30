package com.example.mostaql.data.models

import kotlinx.serialization.Serializable

@Serializable
data class AppUser(
    val id: String,
    val name: String,
    val role: String,
    val email: String? = null,
    val image: String? = null,
    val rating: Float = 0f
)