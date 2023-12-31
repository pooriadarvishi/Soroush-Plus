package com.example.soroushplusproject.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.soroushplusproject.data.model.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Upsert
    suspend fun insertContacts(contacts: List<ContactEntity>)

    @Query("SELECT * FROM contact_table ORDER BY name COLLATE NOCASE ASC")
    fun getAllContacts(): Flow<List<ContactEntity>>


    @Query("SELECT * FROM contact_table WHERE name LIKE :query OR number LIKE :query ORDER BY name COLLATE NOCASE ASC")
    fun searchContacts(query: String): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contact_table WHERE id = :id")
    fun getContactById(id: Int): Flow<ContactEntity>


    @Query("DELETE FROM contact_table WHERE id = :id")
    suspend fun deleteContactById(id: Int)

    @Update
    suspend fun updateContact(contact: ContactEntity)


}