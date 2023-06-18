package com.example.soroushplusproject.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soroushplusproject.data.Repository
import com.example.soroushplusproject.ui.models.ContactDetails
import com.example.soroushplusproject.util.CONTACT_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: Repository, private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val _contact = MutableLiveData<ContactDetails>()
    val contact: LiveData<ContactDetails> = _contact

    private val contactId = stateHandle.get<Int>(CONTACT_ID)

    init {
        contactId?.let { getContactById(it) }
    }


    private fun getContactById(contactId: Int) {
        viewModelScope.launch {
            repository.getContactById(contactId).collectLatest { contact ->
                _contact.postValue(contact)
            }
        }
    }

    fun sync() {
        viewModelScope.launch {
            repository.syncContacts()
        }
    }
}