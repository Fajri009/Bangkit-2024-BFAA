package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data.local.entity.NewsEntity
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data.local.room.NewsDao
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data.remote.response.NewsResponse
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.data.remote.retrofit.ApiService
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.Repository.utils.AppExecutors
import com.fajri.bangkit_2024_bfaa.BuildConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Untuk menyimpan data dari network ke local
class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newsDao: NewsDao,
    private val appExecutors: AppExecutors
) {
    // MediatorLiveData digunakan jika ingin menggabungkan banyak sumber data dalam sebuah LiveData
    private val result = MediatorLiveData<Result<List<NewsEntity>>>()

    fun getHeadLineNews(): LiveData<Result<List<NewsEntity>>> {
        // insisiasi dnegan status loading
        result.value = Result.Loading
        // mengambil dari network dengan ApiService
        val client = apiService.getNews(BuildConfig.API_KEY)
        client.enqueue(object: Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                // membaca data ketika response berhasil
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    val newsList = ArrayList<NewsEntity>()
                    appExecutors.diskIO.execute {
                        articles?.forEach { article ->
                            // mengecek apakah data yang ada sudah ada di dalam bookmark atau belum
                            val isBookmarked = newsDao.isNewsBookmarked(article.title)
                            val news = NewsEntity(
                                article.title,
                                article.publishedAt,
                                article.urlToImage,
                                article.url,
                                isBookmarked
                            )
                            // mengubah data response menjadi entity sebelum dimasukkan ke dalam database
                            newsList.add(news)
                        }
                        // menghapus semua data dari database yang tidak ditandai bookmark
                        newsDao.deleteAll()
                        // memasukkan data baru dari internet ke dalam database
                        newsDao.insertNews(newsList)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                // memberi status jika terjadi error
                result.value = Result.Error(t.message.toString())
            }
        })
        // mengambil data dari database yang merupakan sumber utama untuk dikonsumsi dan memberi tanda sukses
        val localData = newsDao.getNews()
        result.addSource(localData) { newsData: List<NewsEntity> ->
            result.value = Result.Success(newsData)
        }
        return result
    }

    // fungsi untuk mengatur dan mengambil bookmark
    fun getBookmarkedNews(): LiveData<List<NewsEntity>> {
        return newsDao.getBookmarkedNews()
    }

    fun setBookmarkedNews(news: NewsEntity, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
            news.isBookmarked = bookmarkState
            newsDao.updateNews(news)
        }
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newsDao: NewsDao,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newsDao, appExecutors)
            }.also {
                instance = it
            }
    }
}