package com.example.soroushplusproject.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T> : ViewModel() {
    protected val  _dataState = MutableLiveData<T>()
    val dataState : LiveData<T> = _dataState



}