package com.example.soroushplusproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.soroushplusproject.data.model.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(Contacts: List<ContactEntity>)

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    fun getAllContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contact_table WHERE id = :id")
    fun getContactById(id: Int): Flow<ContactEntity>

    @Query("SELECT id FROM contact_table")
    suspend fun getAllIdContacts(): List<Int>

    @Query("DELETE FROM contact_table WHERE id = :id")
    suspend fun deleteContactById(id: Int)

    @Update
    suspend fun updateContact(contact: ContactEntity)


}