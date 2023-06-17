package com.example.soroushplusproject.data

import com.example.soroushplusproject.data.contents.ContactProvider
import com.example.soroushplusproject.data.local.ContactDao
import com.example.soroushplusproject.ui.models.ContactDetails
import com.example.soroushplusproject.ui.models.ContactItem
import com.example.soroushplusproject.util.mappers.EntityToDetails
import com.example.soroushplusproject.util.mappers.EntityToItem
import com.example.soroushplusproject.util.mappers.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Repository @Inject constructor(
    private val contactDao: ContactDao,
    private val contactProvider: ContactProvider,
    private val entityToItem: EntityToItem,
    private val entityToDetails: EntityToDetails,
) {
    suspend fun insertAllContact() {
        if (contactDao.getAllIdContacts().isEmpty()) {
            contactDao.insertContacts(contactProvider.getContacts())
        }
    }


    suspend fun syncContacts() {
        val contacts = contactProvider.getContacts()
        contactDao.insertContacts(contacts)
        syncDeletedContacts()
    }


    private suspend fun syncDeletedContacts() {
        val localContacts = contactDao.getAllIdContacts()
        val mobileContacts = contactProvider.getIdsContacts()
        val deletedContacts = localContacts.filter { it !in mobileContacts }
        deletedContacts.forEach { contactDao.deleteContactById(it) }
    }


    fun getAllContact(): Flow<List<ContactItem>> =
        contactDao.getAllContacts().map { entityToItem.map(it) }

    fun getContactById(id: Int): Flow<ContactDetails> =
        contactDao.getContactById(id).map { entityToDetails.map(it) }
}