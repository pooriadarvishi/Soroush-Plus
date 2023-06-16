package com.example.soroushplusproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.soroushplusproject.data.model.ContactEntity

@Database(entities = [ContactEntity::class], version = 1, exportSchema = false)
abstract class ContactDataBase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
}