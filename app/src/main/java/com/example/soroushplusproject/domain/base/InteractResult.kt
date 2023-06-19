package com.example.soroushplusproject.domain.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

abstract class InteractResult<T,Q> {
    operator fun invoke(params: Q): Flow<InteractResultState<T>> = doWork(params).map { result ->

        when (result) {
            is Collection<*> -> {
                if (result.isEmpty()) {
                    InteractResultState.Empty
                } else {
                    InteractResultState.Success(result)
                }
            }
            else -> {
                if (result != null) {
                    InteractResultState.Success(result)
                } else {
                    InteractResultState.Empty
                }
            }
        }

    }.onStart { emit(InteractResultState.Loading) }.catch {
        emit(InteractResultState.Error)
    }.flowOn(Dispatchers.IO)


    protected abstract fun doWork(params : Q): Flow<T>
}