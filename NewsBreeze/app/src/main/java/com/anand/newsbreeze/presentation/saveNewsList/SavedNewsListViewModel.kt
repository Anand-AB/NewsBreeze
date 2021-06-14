package com.anand.newsbreeze.presentation.saveNewsList

import androidx.lifecycle.MutableLiveData
import com.anand.newsbreeze.data.contract.CommonApisRepo
import com.anand.newsbreeze.data.models.NewsListData
import com.anand.newsbreeze.presentation.core.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The ViewModel used for process saved news list data
 * */

class SavedNewsListViewModel constructor(private val commonApisRepo: CommonApisRepo) :
    BaseViewModel() {

    val newsListSavedLiveData: MutableLiveData<List<NewsListData>> = MutableLiveData()

    fun getSavedNewsList() {
        launch {
            postValue(
                commonApisRepo.getSavedNewsList(),
                newsListSavedLiveData
            )
        }
    }
}