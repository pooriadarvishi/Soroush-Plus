package com.example.soroushplusproject.data.contents_provider

import android.content.ContentResolver
import android.content.Context
import android.database.ContentObserver
import android.provider.ContactsContract
import androidx.appcompat.app.AppCompatActivity
import com.example.soroushplusproject.data.local.LocalDataSource
import com.example.soroushplusproject.data.model.ContactEntity
import com.example.soroushplusproject.util.grantedPermission
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ContentObserver(
    private val context: Context, private val localDataSource: LocalDataSource
) : ContentObserver(null) {
    companion object {
        const val APP_PERF = "app_prefs"
        const val LAST_UPDATE_TIME = "last_update_time"

    }

    private var job: Job? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO)


    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        job?.cancel()
        job = coroutineScope.launch { syncContacts() }
    }


    suspend fun syncContacts() {
        if (context.grantedPermission()) {
            localDataSource.insertContacts(newContacts())
            deletedContacts().forEach { localDataSource.deleteContactById(it) }
        }
    }


    private fun newContacts(): MutableList<ContactEntity> {
        val contentResolver: ContentResolver = context.contentResolver
        val contacts = mutableListOf<ContactEntity>()
        var lastUpdateTime = getLastUpdateTime()


        contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            "${ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP} > ?",
            arrayOf(lastUpdateTime.toString()),
            null,
            null
        )?.use { cursor ->
            val contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val photoIndex = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)
            val updateIndex =
                cursor.getColumnIndex(ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP)
            while (cursor.moveToNext()) {
                val contact = ContactEntity().apply {
                    val sId = cursor.getString(contactIdIndex)
                    val updateTime = cursor.getLong(updateIndex)
                    if (updateTime > lastUpdateTime) lastUpdateTime = updateTime

                    name = cursor.getString(nameIndex)
                    image = cursor.getString(photoIndex)


                    setContactNumber(sId, this, contentResolver)

                    setContactEmail(sId, this, contentResolver)

                    id = sId.toInt()
                }
                contacts.add(contact)
            }
            cursor.close()
        }
        setLastUpdateTime(lastUpdateTime)
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
            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", arrayOf(id), null
        )?.use { emailCursor ->
            val emailIndex =
                emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS)
            while (emailCursor.moveToNext()) {
                contact.email = emailCursor.getString(emailIndex)
            }
        }
    }


    private fun deletedContacts(): MutableList<Int> {
        var lastUpdateTime = getLastUpdateTime()
        val deletedContactIds = mutableListOf<Int>()
        context.contentResolver.query(
            ContactsContract.DeletedContacts.CONTENT_URI,
            null,
            "${ContactsContract.DeletedContacts.CONTACT_DELETED_TIMESTAMP} > ?",
            arrayOf(lastUpdateTime.toString()),
            null
        )?.use { cursor ->

            val contactIdIndex = cursor.getColumnIndex(ContactsContract.DeletedContacts.CONTACT_ID)
            val updateIndex =
                cursor.getColumnIndex(ContactsContract.DeletedContacts.CONTACT_DELETED_TIMESTAMP)
            while (cursor.moveToNext()) {

                deletedContactIds.add(cursor.getLong(contactIdIndex).toInt())
                val updateTime = cursor.getLong(updateIndex)
                if (updateTime > lastUpdateTime) lastUpdateTime = updateTime
            }
            setLastUpdateTime(lastUpdateTime)
        }
        return deletedContactIds
    }


    private fun getLastUpdateTime(): Long {
        val sharedPreferences =
            context.getSharedPreferences(APP_PERF, AppCompatActivity.MODE_PRIVATE)
        return sharedPreferences.getLong(LAST_UPDATE_TIME, 0)
    }

    private fun setLastUpdateTime(lastUpdateTime: Long) {
        val sharedPreferences =
            context.getSharedPreferences(APP_PERF, AppCompatActivity.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putLong(LAST_UPDATE_TIME, lastUpdateTime)
            apply()
        }
    }


}