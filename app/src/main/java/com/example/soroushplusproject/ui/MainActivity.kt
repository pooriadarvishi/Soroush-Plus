package com.example.soroushplusproject.ui

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.soroushplusproject.R
import com.example.soroushplusproject.domain.base.InteractState
import com.example.soroushplusproject.ui.permission.showDialog
import com.example.soroushplusproject.util.grantedPermission
import com.example.soroushplusproject.util.onShowSnackBar
import com.example.soroushplusproject.util.onShowSnackBarWithActionIndefinite
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) onGrantedPermission()
        else {
            onShowSnackBarError()
            unGrantedPermission()
        }
    }


    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var root: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUi()

    }

    override fun onResume() {
        super.onResume()
        if (!permissionRequireState()) onShowSnackBarError()
    }

    override fun onDestroy() {
        super.onDestroy()
        unObserveContact()
    }


    private fun setUi() {
        root = findViewById(R.id.fragmentContainerView)
        onSync()
    }

    private fun observe() {
        mainViewModel.dataState.observe(this) { dataState ->
            when (dataState) {
                InteractState.Error -> onShowSnackBarError()
                InteractState.Loading -> onShowSnackBarLoading()
                InteractState.Success -> onShowSnackBarSuccess()
            }
        }
    }


    private fun onSync() {
        if (!permissionState() && permissionRequireState()) {
            onPopUpDialogPermission()
        } else if (!permissionState() && !permissionRequireState()) {
            onShowSettingDialogPermission()
        } else onGrantedPermission()
    }


    private fun onGrantedPermission() {
        observe()
        observeContacts()
        mainViewModel.grantedPermission()
        mainViewModel.sync()
    }

    private fun unGrantedPermission() {
        mainViewModel.unGrantedPermission()
        mainViewModel.sync()
    }

    private fun onDialog(actionGranted: () -> Unit) {
        this.showDialog(onGetPermission = actionGranted) { unGrantedPermission() }
    }

    private fun onShowSettingDialogPermission() {
        onDialog { settingPermissionLauncher() }
    }

    private fun onPopUpDialogPermission() {
        onDialog { permissionLauncher.launch(Manifest.permission.READ_CONTACTS) }
    }

    private fun settingPermissionLauncher() {
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

    private fun onShowSnackBarLoading() {
        onShowSnackBar(root, getString(R.string.syncing))
    }

    private fun onShowSnackBarSuccess() {
        onShowSnackBar(root, getString(R.string.syncing_success))
    }

    private fun onShowSnackBarError() {
        onShowSnackBarWithActionIndefinite(
            root, getString(R.string.permission_failed), getString(R.string.get_permission)
        ) {
            onSync()
        }
    }


}