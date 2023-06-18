package com.example.soroushplusproject.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

fun <T> Flow<List<T>>.asResult(): Flow<Result<List<T>>> = map { result ->
    if (result.isEmpty()) {
        Result.Success(result)
    } else {
        Result.Empty
    }
}.onStart { Result.Loading }.catch { Result.Error }