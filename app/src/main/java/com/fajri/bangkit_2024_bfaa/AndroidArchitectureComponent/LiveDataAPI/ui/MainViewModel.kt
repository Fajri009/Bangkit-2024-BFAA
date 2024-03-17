package com.fajri.bangkit_2024_bfaa.AndroidArchitectureComponent.LiveDataAPI.ui

import android.util.Log
import androidx.lifecycle.*
import com.fajri.bangkit_2024_bfaa.AndroidArchitectureComponent.LiveDataAPI.Event
import com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.data.response.*
import com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    private val _listReview = MutableLiveData<List<CustomerReviewsItem>>()
    val listReview: LiveData<List<CustomerReviewsItem>> = _listReview

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackBarText = MutableLiveData<Event<String>>()
    val snackBarText: LiveData<Event<String>> = _snackBarText

    init {
        findRestaurant()
    }

    private fun findRestaurant() {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object: Callback<RestaurantResponse> {
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body() != null) {
//                        setRestaurantData(responseBody.restaurant)
                        _restaurant.value = response.body()?.restaurant

//                        setReviewData(responseBody.restaurant.customerReviews)
                        _listReview.value = response.body()?.restaurant?.customerReviews
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun postReview(review: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Dicoding", review)
        client.enqueue(object: Callback<PostReviewRaesponse> {
            override fun onResponse(
                call: Call<PostReviewRaesponse>,
                response: Response<PostReviewRaesponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful && response.body() != null) {
//                    setReviewData(responseBody.customerReviews)
                    _listReview.value = response.body()?.customerReviews
                    _snackBarText.value = Event(response.body()?.message.toString())
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostReviewRaesponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}