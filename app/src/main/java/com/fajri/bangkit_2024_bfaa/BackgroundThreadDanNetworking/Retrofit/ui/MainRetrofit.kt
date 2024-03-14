package com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.data.response.CustomerReviewsItem
import com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.data.response.PostReviewRaesponse
import com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.data.response.Restaurant
import com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.data.response.RestaurantResponse
import com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.data.retrofit.ApiConfig
import com.fajri.bangkit_2024_bfaa.databinding.ActivityMainRetrofitBinding
import retrofit2.*

class MainRetrofit : AppCompatActivity() {
    private lateinit var binding: ActivityMainRetrofitBinding

    companion object {
        private const val TAG = "MainRetrofit"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRetrofitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutInflater = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutInflater
        val itemDecoration = DividerItemDecoration(this, layoutInflater.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        findRestaurant()

        binding.btnSend.setOnClickListener { view ->
            postReview(binding.edReview.text.toString())
        }
    }

    private fun findRestaurant() {
        showLoading(true)

        val client = ApiConfig.getApiService().getRestaurant(RESTAURANT_ID)
        client.enqueue(object: Callback<RestaurantResponse> {
            override fun onResponse(
                call: Call<RestaurantResponse>,
                response: Response<RestaurantResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setRestaurantData(responseBody.restaurant)
                        setReviewData(responseBody.restaurant.customerReviews)
                    }
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setRestaurantData(restaurant: Restaurant) {
        binding.tvTitle.text = restaurant.name
        binding.tvDescription.text = restaurant.description
        Glide.with(this@MainRetrofit)
            .load("https://restaurant-api.dicoding.dev/images/large/${restaurant.pictureId}")
            .into(binding.ivPicture)
    }

    private fun setReviewData(consumerReviews: List<CustomerReviewsItem>) {
        val adapter = ReviewAdapter()
        adapter.submitList(consumerReviews)
        binding.rvReview.adapter = adapter
        binding.edReview.setText("")
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun postReview(review: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().postReview(RESTAURANT_ID, "Dicoding", review)
        client.enqueue(object: Callback<PostReviewRaesponse> {
            override fun onResponse(
                call: Call<PostReviewRaesponse>,
                response: Response<PostReviewRaesponse>
            ) {
                showLoading(false)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    setReviewData(responseBody.customerReviews)
                } else {
                    Log.e(TAG, "onResponse: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PostReviewRaesponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }
}
