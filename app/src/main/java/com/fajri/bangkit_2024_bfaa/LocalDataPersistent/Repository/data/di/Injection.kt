package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data.di

import android.content.Context
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data.NewsRepository
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data.local.room.NewsDatabase
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data.remote.retrofit.ApiConfig
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }
}