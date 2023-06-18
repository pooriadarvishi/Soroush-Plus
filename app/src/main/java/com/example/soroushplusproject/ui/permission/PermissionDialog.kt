package com.example.soroushplusproject.ui.permission

import android.content.Context
import android.view.LayoutInflater
import com.example.soroushplusproject.databinding.DialogPermissionBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


typealias onSetContinue = () -> Unit

fun Context.showDialog(onGetPermission: onSetContinue) {


    val dialogView = DialogPermissionBinding.inflate(LayoutInflater.from(this))

    val neverMindButton = dialogView.btnNeverMind
    val continueButton = dialogView.btnContinue

    val dialog = MaterialAlertDialogBuilder(this).setView(
        dialogView.root
    ).show()
    neverMindButton.setOnClickListener {
        dialog.dismiss()
    }

    continueButton.setOnClickListener {
        dialog.dismiss()
        onGetPermission()
    }


}
