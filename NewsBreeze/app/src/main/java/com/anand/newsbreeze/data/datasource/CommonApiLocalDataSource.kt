package com.anand.newsbreeze.data.datasource

import com.anand.newsbreeze.data.models.NewsListData

interface CommonApiLocalDataSource {

    suspend fun getNewsListSaved(): List<NewsListData>

    suspend fun getSavedNewsList(): List<NewsListData>

    suspend fun updateNews(id: Long,isSaved:Boolean): Boolean

    suspend fun insertNewsList(list: List<NewsListData>): List<NewsListData>

}