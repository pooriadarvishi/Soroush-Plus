package com.example.soroushplusproject.ui.permission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PermissionViewModel : ViewModel() {
    private val _permissionState = MutableLiveData(false)
    val permissionState: LiveData<Boolean> = _permissionState


    fun permissionDenied() {
        _permissionState.postValue(false)
    }

    fun permissionGranted() {
        _permissionState.postValue(true)
    }
}