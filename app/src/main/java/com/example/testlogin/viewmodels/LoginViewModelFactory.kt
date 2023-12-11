package com.example.testlogin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repositoryImpl.RepositoryImpl
import com.example.data.retrofit.RetrofitAndApi
import com.example.domain.useCases.LoginUseCase

class LoginViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            val retrofitAndApi = RetrofitAndApi()
            val loginUseCase = LoginUseCase(RepositoryImpl(retrofitAndApi))
            return LoginViewModel(loginUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}