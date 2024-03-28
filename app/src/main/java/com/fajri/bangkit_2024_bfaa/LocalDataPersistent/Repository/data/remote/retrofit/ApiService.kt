package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data.remote.retrofit

import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("top-headlines?country=id&category=science")
    fun getNews(@Query("apiKey") apiKey: String): Call<NewsResponse>
}