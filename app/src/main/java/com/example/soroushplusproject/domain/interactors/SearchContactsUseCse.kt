package com.example.soroushplusproject.domain.interactors

import com.example.soroushplusproject.data.Repository
import com.example.soroushplusproject.domain.interact_result.InteractResult
import com.example.soroushplusproject.ui.models.ContactItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchContactsUseCse @Inject constructor(private val repository: Repository) :
    InteractResult<List<ContactItem>, String>() {
    override fun doWork(params: String): Flow<List<ContactItem>> = repository.searchContacts(params)
}