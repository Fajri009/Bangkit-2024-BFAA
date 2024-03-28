package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.ui

import androidx.lifecycle.ViewModel
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data.NewsRepository
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data.local.entity.NewsEntity

class NewsViewModel(private val newsRepository: NewsRepository): ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadLineNews()

    fun getBookmarkedNews() = newsRepository.getBookmarkedNews()

    fun saveNews(news: NewsEntity) {
        newsRepository.setBookmarkedNews(news, true)
    }

    fun deleteNews(news: NewsEntity) {
        newsRepository.setBookmarkedNews(news, false)
    }
}