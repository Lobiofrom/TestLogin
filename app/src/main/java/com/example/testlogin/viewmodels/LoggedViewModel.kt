package com.example.testlogin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.payments.Payment
import com.example.domain.useCases.GetPaymentsUseCase
import com.example.testlogin.states.States
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoggedViewModel(
    private val getPaymentsUseCase: GetPaymentsUseCase
) : ViewModel() {

    private val _list = MutableStateFlow<List<Payment>>(emptyList())
    val list = _list.asStateFlow()

    private var _state = MutableStateFlow<States>(States.Success)
    val state = _state.asStateFlow()

    fun getPayments(token: String) {
        _state.value = States.Loading
        viewModelScope.launch {
            val result = getPaymentsUseCase.execute(token)
            _list.value = result
            _state.value = States.Success
        }
    }
}