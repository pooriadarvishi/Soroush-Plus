package com.example.soroushplusproject.data.local

import com.example.soroushplusproject.data.model.ContactEntity
import com.example.soroushplusproject.data.model.ContactDetails
import com.example.soroushplusproject.data.model.ContactItem
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertContacts(contacts: List<ContactEntity>)

    fun getAllContacts(): Flow<List<ContactItem>>

    fun getContactById(id: Int): Flow<ContactDetails>


    suspend fun deleteContactById(id: Int)

    suspend fun deleteContactsByIds(ids: List<Int>)

    suspend fun updateContact(contact: ContactEntity)

    fun searchContacts(query: String): Flow<List<ContactItem>>


}