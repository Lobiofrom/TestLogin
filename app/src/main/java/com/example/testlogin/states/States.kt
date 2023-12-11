package com.example.testlogin.states

sealed class States {
    data object Loading : States()
    data object Success : States()
}