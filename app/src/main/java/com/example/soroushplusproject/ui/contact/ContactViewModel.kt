package com.example.soroushplusproject.ui.contact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soroushplusproject.domain.interact_result.InteractResultState
import com.example.soroushplusproject.domain.interactors.GetAllContactUseCase
import com.example.soroushplusproject.domain.interactors.SyncContactsUseCase
import com.example.soroushplusproject.ui.models.ContactItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getAllContactUseCase: GetAllContactUseCase,
) : ViewModel() {
    private val _contacts = MutableLiveData<InteractResultState<List<ContactItem>>>()
    val contacts: LiveData<InteractResultState<List<ContactItem>>> = _contacts


    init {
        getContacts()
    }

    private fun getContacts() {
        viewModelScope.launch {
            getAllContactUseCase(Unit).collectLatest {
                _contacts.postValue(it)
            }
        }
    }
}