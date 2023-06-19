package com.example.soroushplusproject.data.mappers

import com.example.soroushplusproject.data.model.ContactEntity
import com.example.soroushplusproject.ui.models.ContactDetails
import com.example.soroushplusproject.util.mappers.Mapper
import javax.inject.Inject

class EntityToDetails @Inject constructor() : Mapper<ContactEntity, ContactDetails> {
    override fun map(from: ContactEntity): ContactDetails =
        with(from) { ContactDetails(id, name, phoneNumber, email, image) }
}