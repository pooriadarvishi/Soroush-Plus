package com.example.soroushplusproject.ui.contact

import androidx.lifecycle.viewModelScope
import com.example.soroushplusproject.domain.base.InteractResultState
import com.example.soroushplusproject.domain.interactors.GetAllContactUseCase
import com.example.soroushplusproject.domain.interactors.SearchContactsUseCse
import com.example.soroushplusproject.ui.base.BaseViewModel
import com.example.soroushplusproject.data.model.ContactItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
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

    // delay for Create dynamic UI
    private fun getContacts() {
        job?.cancel()
        job = viewModelScope.launch {
            delay(500)
            getAllContactUseCase(Unit).collect {
                _dataState.postValue(it)
            }
        }
    }


    fun searchContact(query: String) {
        job = viewModelScope.launch {
            delay(500)
            searchContactsUseCse(query).collect {
                _dataState.postValue(it)
            }
        }
    }

    fun startShowAllContact() {
        getContacts()
    }

}