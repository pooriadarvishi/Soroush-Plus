package com.example.soroushplusproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("contact_table")
data class ContactEntity(
    @ColumnInfo("id")
    @PrimaryKey(false)
    val id: Int?,
    @ColumnInfo("name")
    val name: String?,
    @ColumnInfo("number")
    val number: String?,
    @ColumnInfo("email")
    val Email: String?,
    @ColumnInfo("image")
    val image: String?,
)