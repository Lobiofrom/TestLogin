package com.example.data.repositoryImpl

import com.example.data.dto.toPayments
import com.example.data.retrofit.RetrofitAndApi
import com.example.domain.models.LoginRequest
import com.example.domain.models.apiResponse.ApiResponse
import com.example.domain.models.payments.Payment
import com.example.domain.repository.Repository

class RepositoryImpl(
    private val retrofitAndApi: RetrofitAndApi,
) : Repository {

    override suspend fun login(username: String, password: String): ApiResponse {
        val user = LoginRequest(username, password)
        return retrofitAndApi.api.login(user)
    }

    override suspend fun getPayments(token: String): List<Payment> {
        return retrofitAndApi.api.getPayments(token).toPayments().response
    }
}