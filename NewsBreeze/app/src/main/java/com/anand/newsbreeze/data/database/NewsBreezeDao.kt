package com.anand.newsbreeze.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.anand.newsbreeze.data.models.NewsListData

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The Dao helps to do data base operations
 * */

@Dao
interface NewsBreezeDao {

    @androidx.room.Query("SELECT * FROM news ")
    fun getNewsListSaved(): List<NewsListData>

    @androidx.room.Query("SELECT * FROM news WHERE isSaved = :isSaved")
    fun getSavedNewsList(isSaved: Boolean): List<NewsListData>

    @androidx.room.Query("DELETE FROM news WHERE isSaved = :isSaved")
    fun deleteUnSavedNewsList(isSaved: Boolean)

    @androidx.room.Query("UPDATE news SET isSaved = :isSaved  WHERE id = :id")
    fun updateNews(id: Long, isSaved: Boolean): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNewsList(list: List<NewsListData>)

}