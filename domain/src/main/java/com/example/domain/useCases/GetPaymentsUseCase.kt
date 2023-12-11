package com.example.domain.useCases

import com.example.domain.models.payments.Payment
import com.example.domain.repository.Repository

class GetPaymentsUseCase(
    private val repository: Repository
) {
    suspend fun execute(token: String): List<Payment> {
        return repository.getPayments(token)
    }
}