package com.example.data.dto

import com.example.domain.models.payments.Payment
import com.example.domain.models.payments.Payments

data class PaymentsDto(
    val response: List<Payment>,
    val success: String
)

fun PaymentsDto.toPayments() = Payments(
    response = response,
    success = success
)
