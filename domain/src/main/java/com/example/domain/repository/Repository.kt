package com.example.domain.repository

import com.example.domain.models.apiResponse.ApiResponse
import com.example.domain.models.payments.Payment
import com.example.domain.models.payments.Payments

interface Repository {
    suspend fun login(username: String, password: String): ApiResponse
    suspend fun getPayments(token: String): List<Payment>
}