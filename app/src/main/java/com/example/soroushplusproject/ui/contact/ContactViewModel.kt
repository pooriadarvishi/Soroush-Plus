package com.example.soroushplusproject.ui.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soroushplusproject.domain.base.InteractResultState
import com.example.soroushplusproject.domain.interactors.GetAllContactUseCase
import com.example.soroushplusproject.domain.interactors.SearchContactsUseCse
import com.example.soroushplusproject.ui.models.ContactItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getAllContactUseCase: GetAllContactUseCase,
    private val searchContactsUseCse: SearchContactsUseCse
) : ViewModel() {
    private val _contacts = MutableLiveData<InteractResultState<List<ContactItem>>>()
    val contacts: LiveData<InteractResultState<List<ContactItem>>> = _contacts
    private var job: Job? = null

    init {
        getContacts()
    }

    private fun getContacts() {
        job?.cancel()
        job = viewModelScope.launch {
            getAllContactUseCase(Unit).collect {
                _contacts.postValue(it)
            }
        }
    }


    fun searchContact(query: String) {
        job = viewModelScope.launch {
            searchContactsUseCse(query).collect {
                _contacts.postValue(it)
            }
        }
    }

    fun startShowAllContact() {
        getContacts()
    }

}