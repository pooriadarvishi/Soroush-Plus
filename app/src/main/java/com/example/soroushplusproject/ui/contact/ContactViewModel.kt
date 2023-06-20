package com.example.soroushplusproject.ui.contact

import androidx.lifecycle.viewModelScope
import com.example.soroushplusproject.domain.base.InteractResultState
import com.example.soroushplusproject.domain.interactors.GetAllContactUseCase
import com.example.soroushplusproject.domain.interactors.SearchContactsUseCse
import com.example.soroushplusproject.ui.base.BaseViewModel
import com.example.soroushplusproject.data.model.ContactItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getAllContactUseCase: GetAllContactUseCase,
    private val searchContactsUseCse: SearchContactsUseCse
) : BaseViewModel<InteractResultState<List<ContactItem>>>() {
    private var job: Job? = null

    init {
        getContacts()
    }

    private fun getContacts() {
        job?.cancel()
        job = viewModelScope.launch {
            getAllContactUseCase(Unit).collect {
                _dataState.postValue(it)
            }
        }
    }


    fun searchContact(query: String) {
        job = viewModelScope.launch {
            searchContactsUseCse(query).collect {
                _dataState.postValue(it)
            }
        }
    }

    fun startShowAllContact() {
        getContacts()
    }

}