package com.example.soroushplusproject.domain.interact_result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

abstract class InteractResult<T> {
    operator fun invoke(): Flow<InteractResultState<T>> = doWork().map { result ->
        if (result != null) InteractResultState.Success(result)
        else InteractResultState.Empty
    }.onStart { emit(InteractResultState.Loading) }.catch { emit(InteractResultState.Error) }


    protected abstract fun doWork(): Flow<T>
}