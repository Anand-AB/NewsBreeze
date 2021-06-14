package com.anand.newsbreeze.di

import androidx.room.Room
import com.anand.newsbreeze.data.database.MyAppDataBase
import com.anand.newsbreeze.presentation.utility.AppConstants.Companion.DB_NAME
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

val roomModule = module {

    single {
        Room.databaseBuilder(androidApplication(), MyAppDataBase::class.java, DB_NAME).build()
    }

    single { get<MyAppDataBase>().getDhCoreDao() }
}