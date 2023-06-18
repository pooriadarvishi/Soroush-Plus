package com.example.soroushplusproject.data.local

import com.example.soroushplusproject.data.model.ContactEntity
import com.example.soroushplusproject.ui.models.ContactDetails
import com.example.soroushplusproject.ui.models.ContactItem
import com.example.soroushplusproject.util.mappers.EntityToDetails
import com.example.soroushplusproject.util.mappers.EntityToItem
import com.example.soroushplusproject.util.mappers.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val contactDao: ContactDao,
    private val entityToItem: EntityToItem,
    private val entityToDetails: EntityToDetails,
) : LocalDataSource {
    override suspend fun insertContacts(contacts: List<ContactEntity>) {
        contactDao.insertContacts(contacts)
    }

    override fun getAllContacts(): Flow<List<ContactItem>> =
        contactDao.getAllContacts().map { entityToItem.map(it) }

    override fun getContactById(id: Int): Flow<ContactDetails> =
        contactDao.getContactById(id).map { entityToDetails.map(it) }

    override suspend fun deleteContactById(id: Int) {
        contactDao.deleteContactById(id)
    }

    override suspend fun deleteContactsByIds(ids: List<Int>) {
        ids.forEach { contactDao.deleteContactById(it) }
    }

    override suspend fun updateContact(contact: ContactEntity) {
        contactDao.updateContact(contact)
    }
}