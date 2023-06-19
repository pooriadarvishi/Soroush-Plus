package com.example.soroushplusproject.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.soroushplusproject.R
import com.example.soroushplusproject.domain.interact.InteractState
import com.example.soroushplusproject.ui.permission.showDialog
import com.example.soroushplusproject.util.grantedPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) onGrantedPermission()
        else unGrantedPermission()
    }


    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel.dataState.observe(this) { dataState ->
            when (dataState) {
                InteractState.Error -> onSync()
                InteractState.Loading -> onToastLoading()
                InteractState.Success -> onToastSuccess()
            }
        }
    }

    override fun onResume() {
        unShowingDialog()
        onSync()
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        unObserveContact()
    }


    private fun onSync() {
        if (!dialogShowingState()) {
            if (!permissionState() && permissionRequireState()) {
                onShowingDialog()
                onPopUpDialogPermission()
            } else if (!permissionState() && !permissionRequireState()) {
                onShowingDialog()
                onSettingDialogPermission()
            } else mainViewModel.sync()
        }
    }


    private fun onGrantedPermission() {
        unShowingDialog()
        observeContacts()
        mainViewModel.grantedPermission()
        mainViewModel.sync()
    }

    private fun unGrantedPermission() {
        unShowingDialog()
        mainViewModel.unGrantedPermission()
        mainViewModel.sync()
    }

    private fun onDialog(actionGranted: () -> Unit) {
        this.showDialog(onGetPermission = actionGranted) { unGrantedPermission() }
    }

    private fun onSettingDialogPermission() {
        onDialog { settingPermissionLauncher() }
    }

    private fun onPopUpDialogPermission() {
        onDialog { permissionLauncher.launch(Manifest.permission.READ_CONTACTS) }
    }

    private fun settingPermissionLauncher() {
        unShowingDialog()
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }


    private fun permissionState(): Boolean = this.grantedPermission()

    private fun permissionRequireState() = mainViewModel.permissionRequireState()


    private fun observeContacts() {
        val resolver = contentResolver
        resolver.registerContentObserver(
            ContactsContract.DeletedContacts.CONTENT_URI, true, mainViewModel.contentObserver()
        )
    }

    private fun unObserveContact() {
        val resolver = contentResolver
        resolver.unregisterContentObserver(
            mainViewModel.contentObserver()
        )
    }

    private fun onToastLoading() {
        onToast(getString(R.string.syncing))
    }

    private fun onToastSuccess() {
        onToast(getString(R.string.syncing_success))
    }


    private fun onToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun unShowingDialog() {
        mainViewModel.dialogUnShowing()
    }

    private fun onShowingDialog() {
        mainViewModel.dialogShowing()
    }

    private fun dialogShowingState() = mainViewModel.dialogShowState()


}