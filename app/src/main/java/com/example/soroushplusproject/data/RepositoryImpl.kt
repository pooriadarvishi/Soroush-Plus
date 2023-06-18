package com.example.soroushplusproject.data

import com.example.soroushplusproject.data.contents.ContentObserver
import com.example.soroushplusproject.data.local.LocalDataSource
import com.example.soroushplusproject.ui.models.ContactDetails
import com.example.soroushplusproject.ui.models.ContactItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl (
    private val localDataSource: LocalDataSource,
    private val contentObserver: ContentObserver,
) : Repository {


    override suspend fun syncContacts() {
        contentObserver.syncContacts()
    }


    override fun getAllContact(): Flow<List<ContactItem>> = localDataSource.getAllContacts()


    override fun getContactById(id: Int): Flow<ContactDetails> = localDataSource.getContactById(id)

}