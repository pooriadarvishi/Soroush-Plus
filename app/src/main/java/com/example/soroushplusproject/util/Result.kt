package com.example.soroushplusproject.util

sealed interface Result<out T> {
    object Loading : Result<Nothing>
    object Error : Result<Nothing>
    object Empty : Result<Nothing>
    data class Success<K>(val result: K) : Result<K>
}