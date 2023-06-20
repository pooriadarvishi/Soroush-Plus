package com.example.soroushplusproject.data.mappers

import com.example.soroushplusproject.data.model.ContactEntity
import com.example.soroushplusproject.data.model.ContactItem
import javax.inject.Inject

class EntityToItem @Inject constructor() : Mapper<ContactEntity, ContactItem> {
    override fun map(from: ContactEntity): ContactItem =
        with(from) { ContactItem(id, name, image) }
}