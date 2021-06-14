package com.anand.newsbreeze.data.contract

import com.anand.newsbreeze.data.Either
import com.anand.newsbreeze.data.models.NewsListData
import com.anand.newsbreeze.data.models.NewsListRSP
import com.anand.newsbreeze.domain.exceptions.MyException

interface CommonApisRepo {

    suspend fun getNewsListCall(url: String): Either<MyException, NewsListRSP>

    suspend fun getNewsListSaved(): Either<MyException, List<NewsListData>>

    suspend fun getSavedNewsList(): Either<MyException, List<NewsListData>>

    suspend fun updateNews(id: Long, isSaved: Boolean): Either<MyException, Boolean>

    suspend fun insertNewsList(news: List<NewsListData>): Either<MyException, List<NewsListData>>

}