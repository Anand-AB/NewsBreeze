package com.anand.newsbreeze.presentation.newsDetail

import androidx.lifecycle.MutableLiveData
import com.anand.newsbreeze.data.contract.CommonApisRepo
import com.anand.newsbreeze.presentation.core.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The ViewModel used for process news detail data
 * */

class NewsDetailViewModel constructor(private val commonApisRepo: CommonApisRepo) :
    BaseViewModel() {

    private val updateNewsLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun updateNews(id: Long, isSaved: Boolean) {
        launch {
            postValue(commonApisRepo.updateNews(id, isSaved), updateNewsLiveData)
        }
    }

}