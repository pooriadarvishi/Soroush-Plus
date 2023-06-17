package com.example.soroushplusproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("contact_table")
data class ContactEntity(
    @ColumnInfo("id")
    @PrimaryKey(false)
    var id: Int? = null,
    @ColumnInfo("name")
    var name: String? = null,
    @ColumnInfo("number")
    var number: String? = null,
    @ColumnInfo("email")
    var email: String? = null,
    @ColumnInfo("image")
    var image: String? = null,
)