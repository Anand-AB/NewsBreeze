package com.anand.newsbreeze.data.datasource

import com.anand.newsbreeze.data.models.NewsListRSP
import kotlinx.coroutines.Deferred

interface CommonApisDataSource {

    suspend fun getNewsListCallAsync(url: String): Deferred<NewsListRSP>

}