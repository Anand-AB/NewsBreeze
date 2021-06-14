package com.anand.newsbreeze.data.ds

import com.anand.newsbreeze.data.datasource.CommonApisDataSource
import com.anand.newsbreeze.data.models.NewsListRSP
import com.anand.newsbreeze.domain.network.CommonApiService
import kotlinx.coroutines.Deferred

class CommonApisRemoteDS constructor(private val commonApiService: CommonApiService) :
    CommonApisDataSource {

    override suspend fun getNewsListCallAsync(url: String): Deferred<NewsListRSP> {
        return commonApiService.getNewsListCallAsync(url)
    }
}