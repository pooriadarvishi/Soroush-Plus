package com.example.soroushplusproject.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.soroushplusproject.data.model.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContacts(Contacts : List<ContactEntity>)


    @Query("SELECT * FROM contact_table")
    fun getContacts() : Flow<List<ContactEntity>>
}