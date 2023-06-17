package com.example.soroushplusproject.ui.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soroushplusproject.data.Repository
import com.example.soroushplusproject.ui.models.ContactItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _contacts = MutableLiveData<List<ContactItem>>()
    val contacts: LiveData<List<ContactItem>> = _contacts

    private var job: Job? = null


    init {
        getContacts()
        insertContact()
    }

    private fun getContacts() {
        viewModelScope.launch {
            repository.getAllContact().collectLatest {
                _contacts.postValue(it)
            }
        }
    }

    private fun insertContact() {
        job?.cancel()
        job = viewModelScope.launch {
            repository.insertAllContact()
        }
    }

    fun sync() {
        job?.cancel()
        job = viewModelScope.launch {
            repository.syncContacts()
        }
    }
}