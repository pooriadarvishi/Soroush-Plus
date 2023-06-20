package com.example.soroushplusproject.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

 fun onShowSnackBar(view: View, text: String) {
    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
}

 fun onShowSnackBarWithActionIndefinite(
    view: View,
    text: String,
    actionText: String,
    action: () -> Unit
) {
    Snackbar.make(view, text, Snackbar.LENGTH_INDEFINITE).setAction(actionText) { action() }.show()
} fun onShowSnackBarWithAction(
    view: View,
    text: String,
    actionText: String,
    action: () -> Unit
) {
    Snackbar.make(view, text, Snackbar.LENGTH_LONG).setAction(actionText) { action() }.show()
}