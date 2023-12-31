package com.example.soroushplusproject.domain.interactors

import com.example.soroushplusproject.data.Repository
import com.example.soroushplusproject.domain.base.InteractResult
import com.example.soroushplusproject.data.model.ContactDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactByIdUseCase @Inject constructor(private val repository: Repository) :
    InteractResult<ContactDetails, GetContactByIdUseCase.Params>() {


    data class Params(val contactId: Int)

    override fun doWork(params: Params): Flow<ContactDetails> =
        repository.getContactById(params.contactId)
}