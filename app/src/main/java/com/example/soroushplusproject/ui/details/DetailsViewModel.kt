package com.example.soroushplusproject.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.soroushplusproject.data.model.ContactDetails
import com.example.soroushplusproject.domain.base.InteractResultState
import com.example.soroushplusproject.domain.interactors.GetContactByIdUseCase
import com.example.soroushplusproject.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val stateHandle: SavedStateHandle
) : BaseViewModel<InteractResultState<ContactDetails>>() {

    private val contactId = stateHandle.get<Int>(DetailsFragment.CONTACT_ID)

    init {
        getContact()
    }

    fun getContact() {
        contactId?.let { getContactById(it) }
    }

    private fun getContactById(contactId: Int) {
        val params = GetContactByIdUseCase.Params(contactId)
        viewModelScope.launch {
            getContactByIdUseCase(params).collect { contact ->
                _dataState.postValue(contact)
            }
        }
    }
}