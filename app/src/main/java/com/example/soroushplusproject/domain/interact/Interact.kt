package com.example.soroushplusproject.domain.interact

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

abstract class Interact<T> {
    operator fun invoke(params: T): Flow<InteractState> = flow {
        emit(InteractState.Loading)
        doWork(params)
        emit(InteractState.Success)
    }.catch { emit(InteractState.Error) }


    protected abstract fun doWork(params: T)
}