package com.example.soroushplusproject.domain.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class Interact<T> {
    operator fun invoke(params: T): Flow<InteractState> = flow {
        emit(InteractState.Loading)
        doWork(params)
        emit(InteractState.Success)
    }.catch {
        emit(InteractState.Error)
    }.flowOn(Dispatchers.IO)

    protected abstract suspend fun doWork(params: T)
}