package com.example.domain.models.payments

data class Payments(
    val response: List<Payment>,
    val success: String
)