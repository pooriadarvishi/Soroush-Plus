package com.example.soroushplusproject.domain.interactors

import com.example.soroushplusproject.data.Repository
import com.example.soroushplusproject.domain.base.Interact
import javax.inject.Inject

class SyncContactsUseCase @Inject constructor(private val repository: Repository) :
    Interact<Unit>() {
    override suspend fun doWork(params: Unit) {
        repository.syncContacts()
    }
}