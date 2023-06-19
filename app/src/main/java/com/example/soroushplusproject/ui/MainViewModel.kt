package com.example.soroushplusproject.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soroushplusproject.domain.interactors.SyncContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val syncContactsUseCase: SyncContactsUseCase
) : ViewModel() {
    var isGrantedPermission = true


    fun sync() {
        viewModelScope.launch {
            syncContactsUseCase(Unit)
        }
    }


    fun grantedPermission() {
        isGrantedPermission = true
    }

    fun imGrantedPermission() {
        isGrantedPermission = false
    }

    fun permissionRequireState() = isGrantedPermission
}