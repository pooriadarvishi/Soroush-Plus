package com.example.soroushplusproject.data.contents

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.provider.ContactsContract
import com.example.soroushplusproject.data.model.ContactEntity

class ContactProvider(private val context: Context) {

    @SuppressLint("Range")
    fun getContacts(): List<ContactEntity> {
        val contentResolver: ContentResolver = context.contentResolver
        return setContactInfo(contentResolver)
    }

    private fun setContactInfo(contentResolver: ContentResolver): MutableList<ContactEntity> {
        val contacts = mutableListOf<ContactEntity>()
        contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI, null, null, null, null
        )?.use { cursor ->
            val contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val photoIndex = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)
            while (cursor.moveToNext()) {
                val contact = ContactEntity().apply {
                    val sId = cursor.getString(contactIdIndex)

                    name = cursor.getString(nameIndex)
                    image = cursor.getString(photoIndex)

                    setContactNumber(sId, this, contentResolver)

                    setContactEmail(sId, this, contentResolver)

                    id = sId.toInt()
                }
                contacts.add(contact)
            }
        }
        return contacts
    }

    private fun setContactNumber(
        id: String, contact: ContactEntity, contentResolver: ContentResolver
    ) {
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
            arrayOf(id),
            null
        )?.use { phoneCursor ->
            val hasPhoneNumberIndex =
                phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER)
            val numberIndex =
                phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            while (phoneCursor.moveToNext()) {
                if (phoneCursor.getInt(hasPhoneNumberIndex) > 0) {
                    contact.phoneNumber = phoneCursor.getString(numberIndex)
                }
            }
            phoneCursor.close()
        }

    }

    private fun setContactEmail(
        id: String, contact: ContactEntity, contentResolver: ContentResolver
    ) {

        contentResolver.query(
            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
            arrayOf(id),
            null
        )?.use { emailCursor ->
            val emailIndex =
                emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
            while (emailCursor.moveToNext()) {
                contact.email = emailCursor.getString(emailIndex)
            }
        }
    }


}