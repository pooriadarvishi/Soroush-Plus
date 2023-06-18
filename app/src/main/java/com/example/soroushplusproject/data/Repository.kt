package com.example.soroushplusproject.data

import com.example.soroushplusproject.ui.models.ContactDetails
import com.example.soroushplusproject.ui.models.ContactItem
import kotlinx.coroutines.flow.Flow

interface Repository {


    suspend fun syncContacts()


    fun getAllContact(): Flow<List<ContactItem>>


    fun getContactById(id: Int): Flow<ContactDetails>

}