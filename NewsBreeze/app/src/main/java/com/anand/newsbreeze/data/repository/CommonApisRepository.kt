package com.anand.newsbreeze.data.repository

import com.anand.newsbreeze.data.BaseRepository
import com.anand.newsbreeze.data.Either
import com.anand.newsbreeze.data.contract.CommonApisRepo
import com.anand.newsbreeze.data.datasource.CommonApiLocalDataSource
import com.anand.newsbreeze.data.datasource.CommonApisDataSource
import com.anand.newsbreeze.data.models.NewsListData
import com.anand.newsbreeze.data.models.NewsListRSP
import com.anand.newsbreeze.domain.exceptions.MyException

class CommonApisRepository constructor(
    private val commonApisDataSource: CommonApisDataSource,
    private val commonApiLocalDataSource: CommonApiLocalDataSource
) : CommonApisRepo,
    BaseRepository() {

    override suspend fun getNewsListCall(url: String): Either<MyException, NewsListRSP> {
        return either(commonApisDataSource.getNewsListCallAsync(url))
    }

    override suspend fun getNewsListSaved(): Either<MyException, List<NewsListData>> {
        return eitherLocal(commonApiLocalDataSource.getNewsListSaved())
    }

    override suspend fun getSavedNewsList(): Either<MyException, List<NewsListData>> {
        return eitherLocal(commonApiLocalDataSource.getSavedNewsList())
    }

    override suspend fun updateNews(id: Long,isSaved:Boolean): Either<MyException, Boolean> {
        return eitherLocal(commonApiLocalDataSource.updateNews(id,isSaved))
    }

    override suspend fun insertNewsList(list: List<NewsListData>): Either<MyException, List<NewsListData>> {
        return eitherLocal(commonApiLocalDataSource.insertNewsList(list))
    }

}