package com.example.testlogin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repositoryImpl.RepositoryImpl
import com.example.data.retrofit.RetrofitAndApi
import com.example.domain.useCases.GetPaymentsUseCase

class LoggedViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoggedViewModel::class.java)) {
            val retrofitAndApi = RetrofitAndApi()
            val useCase = GetPaymentsUseCase(RepositoryImpl(retrofitAndApi))
            return LoggedViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}