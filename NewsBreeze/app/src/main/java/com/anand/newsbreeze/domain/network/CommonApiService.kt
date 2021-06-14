package com.anand.newsbreeze.domain.network

import com.anand.newsbreeze.data.models.NewsListRSP
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Url

interface CommonApiService {

    @GET
    fun getNewsListCallAsync(
        @Url url: String
    ): Deferred<NewsListRSP>

}