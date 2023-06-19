package com.example.soroushplusproject.domain.base

sealed interface InteractResultState<out T> {
    object Loading : InteractResultState<Nothing>
    object Error : InteractResultState<Nothing>
    object Empty : InteractResultState<Nothing>
    data class Success<T>(val result: T) : InteractResultState<T>
}