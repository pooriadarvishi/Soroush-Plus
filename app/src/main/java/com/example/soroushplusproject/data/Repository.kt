package com.example.soroushplusproject.data

import com.example.soroushplusproject.data.contents.ContactProvider
import com.example.soroushplusproject.data.local.ContactDao
import com.example.soroushplusproject.data.model.ContactEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(private val contactDao: ContactDao, private val contactProvider: ContactProvider){

}