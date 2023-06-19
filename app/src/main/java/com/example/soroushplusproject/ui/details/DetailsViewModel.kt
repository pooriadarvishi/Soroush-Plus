package com.example.soroushplusproject.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.soroushplusproject.domain.base.InteractResultState
import com.example.soroushplusproject.domain.interactors.GetContactByIdUseCase
import com.example.soroushplusproject.ui.base.BaseViewModel
import com.example.soroushplusproject.ui.models.ContactDetails
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
        contactId?.let { getContactById(it) }
    }


    private fun getContactById(contactId: Int) {
        val params = GetContactByIdUseCase.Params(contactId)
        viewModelScope.launch {
            delay(1000)
            getContactByIdUseCase(params).collect { contact ->
                _dataState.postValue(contact)
            }
        }
    }
}