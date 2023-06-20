package com.example.soroushplusproject.data.services

import android.app.Service
import android.content.Intent
import android.os.IBinder

class ContactService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)

    }


    override fun onDestroy() {
        super.onDestroy()
    }
}