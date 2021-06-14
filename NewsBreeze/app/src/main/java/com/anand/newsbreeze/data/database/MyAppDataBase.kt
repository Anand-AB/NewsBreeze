package com.anand.newsbreeze.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anand.newsbreeze.data.models.Converters
import com.anand.newsbreeze.data.models.NewsListData

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The file used for database details setup
 * */

@Database(

    entities = [NewsListData::class
    ], version = 1, exportSchema = false
)

@TypeConverters(
    Converters::class
)
abstract class MyAppDataBase : RoomDatabase() {

    abstract fun getDhCoreDao(): NewsBreezeDao
}