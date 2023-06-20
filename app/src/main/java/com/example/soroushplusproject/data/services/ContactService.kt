package com.example.soroushplusproject.data.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.provider.ContactsContract
import com.example.soroushplusproject.data.contents_provider.ContentObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ContactService  : Service() {

    @Inject
    lateinit var contentObserver: ContentObserver
    override fun onBind(intent: Intent?): IBinder? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        observeContacts()
        return super.onStartCommand(intent, flags, startId)
    }


    override fun onDestroy() {
        super.onDestroy()
        unObserveContact()
    }


    private fun observeContacts() {
        val resolver = contentResolver
        resolver.registerContentObserver(
            ContactsContract.DeletedContacts.CONTENT_URI, true, contentObserver
        )
    }

    private fun unObserveContact() {
        val resolver = contentResolver
        resolver.unregisterContentObserver(
            contentObserver
        )
    }
}