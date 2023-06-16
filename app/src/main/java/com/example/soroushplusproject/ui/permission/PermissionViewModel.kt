package com.example.soroushplusproject.ui.permission

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PermissionViewModel : ViewModel() {
    private val _permissionState = MutableLiveData(false)
    val permissionState: LiveData<Boolean> = _permissionState

    private var showPermissionRequestState = true

    fun showPermissionState() = showPermissionRequestState

    fun onPermissionDenied() {
        _permissionState.postValue(false)
        showPermissionRequestState = false
    }

    fun onPermissionGranted() {
        _permissionState.postValue(true)
        showPermissionRequestState = true
    }
}