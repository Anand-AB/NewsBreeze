package com.anand.newsbreeze.data.ds

import com.anand.newsbreeze.data.BaseRepository
import com.anand.newsbreeze.data.database.NewsBreezeDao
import com.anand.newsbreeze.data.datasource.CommonApiLocalDataSource
import com.anand.newsbreeze.data.models.NewsListData

class CommonApiLocalDS constructor(private val newsBreezeDao: NewsBreezeDao) : BaseRepository(),
    CommonApiLocalDataSource {

    override suspend fun getNewsListSaved(): List<NewsListData> {
        return newsBreezeDao.getNewsListSaved()
    }

    override suspend fun getSavedNewsList(): List<NewsListData> {
        return newsBreezeDao.getSavedNewsList(true)
    }

    override suspend fun updateNews(id: Long, isSaved: Boolean): Boolean {
        val status = newsBreezeDao.updateNews(id, isSaved)
        return status == 1
    }

    override suspend fun insertNewsList(list: List<NewsListData>): List<NewsListData> {
        newsBreezeDao.deleteUnSavedNewsList(false)
        newsBreezeDao.insertNewsList(list)
        return newsBreezeDao.getNewsListSaved()
    }

}