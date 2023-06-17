package com.example.soroushplusproject.data.contents

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import com.example.soroushplusproject.data.model.ContactEntity

class ContactProvider(private val context: Context) {

    @SuppressLint("Range")
    fun getContacts(): List<ContactEntity> {
        val contacts = mutableListOf<ContactEntity>()
        val contentResolver: ContentResolver = context.contentResolver

        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER,
                ContactsContract.CommonDataKinds.Email.ADDRESS
            ), null, null, null
        )?.use { cursor ->
            val contactIdIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val nameIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val hasPhoneNumberIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)
            val emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)

            while (cursor.moveToNext()) {
                val contact = ContactEntity().apply {
                    id = cursor.getString(contactIdIndex).toInt()
                    name = cursor.getString(nameIndex)
                    number = cursor.getString(numberIndex)
                    if (cursor.getInt(hasPhoneNumberIndex) > 0) {
                        email = cursor.getString(emailIndex)
                    }
                }

                contacts.add(contact)
            }
        }

        return contacts
    }
}