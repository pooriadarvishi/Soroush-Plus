package com.example.soroushplusproject.ui.permission

import android.content.Context
import android.view.LayoutInflater
import com.example.soroushplusproject.databinding.DialogPermissionBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


fun Context.showDialog(onGetPermission: onClick, onFailedPermission: onClick) {


    val dialogView = DialogPermissionBinding.inflate(LayoutInflater.from(this))

    val neverMindButton = dialogView.btnNeverMind
    val continueButton = dialogView.btnContinue

    val dialog = MaterialAlertDialogBuilder(this).setView(
        dialogView.root
    ).show()

    fun dismiss() {
        dialog.dismiss()
    }

    neverMindButton.setOnClickListener {
        onFailedPermission()
        dismiss()
    }

    continueButton.setOnClickListener {
        dismiss()
        onGetPermission()
    }



}
