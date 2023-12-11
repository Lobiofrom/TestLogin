package com.example.domain.models.apiResponse

data class ApiResponse(
    val success: String,
    val response: Response,
    val error: Error,
)
