package com.example.domain.useCases

import com.example.domain.models.apiResponse.ApiResponse
import com.example.domain.repository.Repository

class LoginUseCase(
    private val repository: Repository
) {
    suspend fun execute(userName: String, password: String): ApiResponse {
        return repository.login(userName, password)
    }
}