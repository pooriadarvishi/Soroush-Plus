package com.example.soroushplusproject.ui

import android.Manifest
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.soroushplusproject.R
import com.example.soroushplusproject.data.contents.ContactProvider
import com.example.soroushplusproject.ui.permission.showDialog
import com.example.soroushplusproject.util.isGrantedPermission
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { if (it) observeContacts() }


    @Inject
    lateinit var contactProvider: ContactProvider
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission()
    }


    private fun showDialogs() {
        this.showDialog { permissionLauncher.launch(Manifest.permission.READ_CONTACTS) }
    }


    private fun checkPermission() {
        if (this.isGrantedPermission()) observeContacts()
        else showDialogs()
    }

    private fun observeContacts() {
        val resolver = contentResolver
        resolver.registerContentObserver(
            ContactsContract.DeletedContacts.CONTENT_URI,
            true,
            contactProvider
        )
    }
}