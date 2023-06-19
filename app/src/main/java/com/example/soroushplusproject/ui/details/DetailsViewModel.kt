package com.example.soroushplusproject.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soroushplusproject.domain.interact_result.InteractResultState
import com.example.soroushplusproject.domain.interactors.GetContactByIdUseCase
import com.example.soroushplusproject.domain.interactors.SyncContactsUseCase
import com.example.soroushplusproject.ui.models.ContactDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val syncContactsUseCase: SyncContactsUseCase,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    private val _contact = MutableLiveData<InteractResultState<ContactDetails>>()
    val contact: LiveData<InteractResultState<ContactDetails>> = _contact
    private val contactId = stateHandle.get<Int>(DetailsFragment.CONTACT_ID)

    init {
        contactId?.let { getContactById(it) }
    }


    private fun getContactById(contactId: Int) {
        val params = GetContactByIdUseCase.Params(contactId)
        viewModelScope.launch {
            getContactByIdUseCase(params).collectLatest { contact ->
                _contact.postValue(contact)
            }
        }
    }

    fun sync() {
        viewModelScope.launch {
            syncContactsUseCase(Unit)
        }
    }
}