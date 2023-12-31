package com.example.soroushplusproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soroushplusproject.data.contents_provider.ContentObserver
import com.example.soroushplusproject.domain.base.InteractState
import com.example.soroushplusproject.domain.interactors.SyncContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val syncContactsUseCase: SyncContactsUseCase,
    private val contentObserver: ContentObserver
) : ViewModel() {
    private var isGrantedPermission = true

    private val _dataState = MutableLiveData<InteractState>()
    val dataState: LiveData<InteractState> = _dataState
    private var job: Job? = null


    fun firstSync() {
        job?.cancel()
        job = viewModelScope.launch {
            syncContactsUseCase(Unit).collect { _dataState.postValue(it) }
        }
    }


    fun otherSync() {
        job?.cancel()
        job = viewModelScope.launch {
            contentObserver.syncContacts()
        }
    }


    fun grantedPermission() {
        isGrantedPermission = true
    }

    fun unGrantedPermission() {
        isGrantedPermission = false
    }

    fun permissionRequireState() = isGrantedPermission


}