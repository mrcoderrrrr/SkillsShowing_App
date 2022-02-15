package com.example.demoapps.model

data class RetrofitModel(
    val `data`: Data,
    val message: String,
    val method: String,
    val status: Int,
    val success: Boolean
)