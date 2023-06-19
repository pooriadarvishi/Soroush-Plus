package com.example.soroushplusproject.di

import android.app.Application
import androidx.room.Room
import com.example.soroushplusproject.data.Repository
import com.example.soroushplusproject.data.RepositoryImpl
import com.example.soroushplusproject.data.contents.ContentObserver
import com.example.soroushplusproject.data.local.ContactDao
import com.example.soroushplusproject.data.local.ContactDataBase
import com.example.soroushplusproject.data.local.LocalDataSource
import com.example.soroushplusproject.data.local.LocalDataSourceImpl
import com.example.soroushplusproject.data.mappers.EntityToDetails
import com.example.soroushplusproject.data.mappers.EntityToItem
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
    fun provideLocalDataSource(
        contactDao: ContactDao, entityToItem: EntityToItem, entityToDetails: EntityToDetails
    ): LocalDataSource = LocalDataSourceImpl(contactDao, entityToItem, entityToDetails)


    @Provides
    @Singleton
    fun provideContactProvider(
        application: Application, localDataSource: LocalDataSource
    ): ContentObserver = ContentObserver(application, localDataSource)


    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: LocalDataSource,
        contentObserver: ContentObserver
    ): Repository = RepositoryImpl(localDataSource, contentObserver)
}