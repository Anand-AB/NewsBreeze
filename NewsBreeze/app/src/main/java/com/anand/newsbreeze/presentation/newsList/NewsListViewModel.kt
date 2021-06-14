package com.anand.newsbreeze.presentation.newsList

import androidx.lifecycle.MutableLiveData
import com.anand.newsbreeze.data.contract.CommonApisRepo
import com.anand.newsbreeze.data.models.NewsListData
import com.anand.newsbreeze.data.models.NewsListRSP
import com.anand.newsbreeze.presentation.core.BaseViewModel
import com.anand.newsbreeze.presentation.utility.AppConstants
import com.anand.newsbreeze.presentation.utility.AppConstants.Companion.API_KEY
import com.anand.newsbreeze.presentation.utility.AppConstants.Companion.SORT_BY
import com.anand.newsbreeze.presentation.utility.Helper.Companion.getCurrentDate
import kotlinx.coroutines.launch

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The ViewModel used for process news list data
 * */

class NewsListViewModel constructor(private val commonApisRepo: CommonApisRepo) :
    BaseViewModel() {

    val newsListLiveData: MutableLiveData<NewsListRSP> = MutableLiveData()
    val newsListSavedLiveData: MutableLiveData<List<NewsListData>> = MutableLiveData()
    val updateNewsLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getNewsListCall() {
        launch {
            postValue(
                commonApisRepo.getNewsListCall(
                    AppConstants.BASE_URL +
                            "everything?q=tesla&from=${getCurrentDate()}&sortBy=$SORT_BY&apiKey=$API_KEY"
                ),
                newsListLiveData
            )
        }
    }

    fun getNewsListSaved() {
        launch {
            postValue(commonApisRepo.getNewsListSaved(), newsListSavedLiveData)
        }
    }

    fun insertNewsList(list: List<NewsListData>) {
        launch {
            postValue(commonApisRepo.insertNewsList(list), newsListSavedLiveData)
        }
    }

    fun updateNews(id: Long, isSaved: Boolean) {
        launch {
            postValue(commonApisRepo.updateNews(id, isSaved), updateNewsLiveData)
        }
    }

}