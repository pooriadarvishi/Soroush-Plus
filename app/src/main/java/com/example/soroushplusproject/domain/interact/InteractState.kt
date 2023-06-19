package com.example.soroushplusproject.domain.interact

sealed interface InteractState {
    object Loading : InteractState
    object Error : InteractState
    object Success : InteractState
}