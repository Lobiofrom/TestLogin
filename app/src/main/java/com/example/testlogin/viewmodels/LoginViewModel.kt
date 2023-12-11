package com.example.testlogin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.apiResponse.ApiResponse
import com.example.domain.useCases.LoginUseCase
import com.example.testlogin.states.States
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _loginData = MutableStateFlow<ApiResponse?>(null)
    val loginData = _loginData.asStateFlow()

    private var _state = MutableStateFlow<States>(States.Success)
    val state = _state.asStateFlow()


    fun login(login: String, password: String) {
        _state.value = States.Loading
        viewModelScope.launch {
            val result = loginUseCase.execute(login, password)
            _loginData.value = result
            _state.value = States.Success
        }
    }
}