package com.anand.newsbreeze.di

import com.anand.newsbreeze.data.datasource.CommonApiLocalDataSource
import com.anand.newsbreeze.data.datasource.CommonApisDataSource
import com.anand.newsbreeze.data.ds.CommonApiLocalDS
import com.anand.newsbreeze.data.ds.CommonApisRemoteDS
import org.koin.dsl.module.module

val dataSourceModule = module {
    single<CommonApisDataSource> { CommonApisRemoteDS(get()) }
    single<CommonApiLocalDataSource> { CommonApiLocalDS(get()) }
}