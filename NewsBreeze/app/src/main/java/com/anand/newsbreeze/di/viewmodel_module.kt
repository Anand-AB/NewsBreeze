package com.anand.newsbreeze.di

import com.anand.newsbreeze.presentation.newsDetail.NewsDetailViewModel
import com.anand.newsbreeze.presentation.newsList.NewsListViewModel
import com.anand.newsbreeze.presentation.saveNewsList.SavedNewsListViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { NewsListViewModel(get()) }
    viewModel { NewsDetailViewModel(get()) }
    viewModel { SavedNewsListViewModel(get()) }
}
