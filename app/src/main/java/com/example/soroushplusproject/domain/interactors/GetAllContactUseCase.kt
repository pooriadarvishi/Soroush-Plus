package com.example.soroushplusproject.domain.interactors

import com.example.soroushplusproject.data.Repository
import com.example.soroushplusproject.domain.base.InteractResult
import com.example.soroushplusproject.data.model.ContactItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllContactUseCase @Inject constructor(private val repository: Repository) :
    InteractResult<List<ContactItem>, Unit>() {
    override fun doWork(params: Unit): Flow<List<ContactItem>> = repository.getAllContact()
}