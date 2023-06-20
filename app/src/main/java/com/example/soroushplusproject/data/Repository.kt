package com.example.soroushplusproject.data

import com.example.soroushplusproject.data.model.ContactDetails
import com.example.soroushplusproject.data.model.ContactItem
import kotlinx.coroutines.flow.Flow

interface Repository {


    suspend fun syncContacts()


    fun getAllContact(): Flow<List<ContactItem>>


    fun getContactById(id: Int): Flow<ContactDetails>

    fun searchContacts(query : String) : Flow<List<ContactItem>>

}