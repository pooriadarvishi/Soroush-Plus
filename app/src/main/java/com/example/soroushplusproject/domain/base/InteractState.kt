package com.example.soroushplusproject.domain.base

sealed interface InteractState {
    object Loading : InteractState
    object Error : InteractState
    object Success : InteractState
}