package com.example.soroushplusproject.domain

import com.example.soroushplusproject.data.Repository
import com.example.soroushplusproject.domain.interact_result.InteractResult
import com.example.soroushplusproject.ui.models.ContactItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllContactUseCase @Inject constructor(private val repository: Repository) :
    InteractResult<List<ContactItem>, Nothing>() {
    override fun doWork(params: Nothing): Flow<List<ContactItem>> = repository.getAllContact()
}