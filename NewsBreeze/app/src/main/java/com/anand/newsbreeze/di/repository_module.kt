package com.anand.newsbreeze.di

import com.anand.newsbreeze.data.contract.CommonApisRepo
import com.anand.newsbreeze.data.repository.CommonApisRepository
import org.koin.dsl.module.module

val repositoryModule = module {
    single<CommonApisRepo> { CommonApisRepository(get(), get()) }
}