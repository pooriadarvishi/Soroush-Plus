package com.example.soroushplusproject.domain.interact_result

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

abstract class InteractResult<T,Q> {
    operator fun invoke(params: Q): Flow<InteractResultState<T>> = doWork(params).map { result ->
        if (result != null) {
            InteractResultState.Success(result)
        }
        else {
            InteractResultState.Empty
        }
    }.onStart { emit(InteractResultState.Loading) }.catch {
        emit(InteractResultState.Error)
    }


    protected abstract fun doWork(params : Q): Flow<T>
}