package com.example.soroushplusproject.di

import android.app.Application
import androidx.room.Room
import com.example.soroushplusproject.data.contents.ContactProvider
import com.example.soroushplusproject.data.local.ContactDao
import com.example.soroushplusproject.data.local.ContactDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataBase(application: Application): ContactDataBase =
        Room.databaseBuilder(application, ContactDataBase::class.java, "contact_database").build()

    @Provides
    @Singleton
    fun provideDao(contactDataBase: ContactDataBase): ContactDao = contactDataBase.contactDao()


    @Provides
    @Singleton
    fun provideContactProvider(application: Application, contactDao: ContactDao): ContactProvider =
        ContactProvider(application, contactDao)
}