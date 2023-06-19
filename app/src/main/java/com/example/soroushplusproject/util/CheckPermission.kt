package com.example.soroushplusproject.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

fun Context.grantedPermission(): Boolean = ContextCompat.checkSelfPermission(
    this, Manifest.permission.READ_CONTACTS
) == PackageManager.PERMISSION_GRANTED