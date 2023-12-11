package com.example.data.retrofit

import com.example.data.dto.PaymentsDto
import com.example.domain.models.LoginRequest
import com.example.domain.models.apiResponse.ApiResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

class RetrofitAndApi {

    val api: Api by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://easypay.world/api-test/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                }).build()
            )
            .build()
            .create(Api::class.java)
    }

    interface Api {

        @POST("login")
        suspend fun login(
            @Body loginRequest: LoginRequest,
            @Header("app-key") appKey: String = "12345",
            @Header("v") version: String = "1"
        ): ApiResponse

        @GET("payments")
        suspend fun getPayments(
            @Header("token") token: String,
            @Header("app-key") appKey: String = "12345",
            @Header("v") version: String = "1"
        ): PaymentsDto

    }
}