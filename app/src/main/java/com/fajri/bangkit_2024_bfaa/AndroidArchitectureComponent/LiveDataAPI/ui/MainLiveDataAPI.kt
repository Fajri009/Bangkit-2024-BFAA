package com.fajri.bangkit_2024_bfaa.AndroidArchitectureComponent.LiveDataAPI.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.data.response.CustomerReviewsItem
import com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.data.response.Restaurant
import com.fajri.bangkit_2024_bfaa.databinding.ActivityMainLiveDataApiBinding
import com.google.android.material.snackbar.Snackbar

class MainLiveDataAPI : AppCompatActivity() {
    private lateinit var binding: ActivityMainLiveDataApiBinding
    // Cara menggunakan Android-KTX
    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        private const val TAG = "MainRetrofit"
        private const val RESTAURANT_ID = "uewq1zg2zlskfw1e867"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLiveDataApiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        // Cara normal
//        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.restaurant.observe(this) { restaurant ->
            setRestaurantData(restaurant)
        }

        val layoutInflater = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutInflater
        val itemDecoration = DividerItemDecoration(this, layoutInflater.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        mainViewModel.listReview.observe(this) { consumerReviews ->
            setReviewData(consumerReviews)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackBarText.observe(this) {
            // memeriksa apakah aksi tersebut pernah dilakukan sebelumnya atau tidak
            it.getContentIfNoHandled()?.let { snackBarText ->
                Snackbar.make(window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.btnSend.setOnClickListener { view ->
//            postReview(binding.edReview.text.toString())
            mainViewModel.postReview(binding.edReview.text.toString())
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun setRestaurantData(restaurant: Restaurant) {
        binding.tvTitle.text = restaurant.name
        binding.tvDescription.text = restaurant.description
        Glide.with(this@MainLiveDataAPI)
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
            binding.progressBar.visibility = View.GONE
        }
    }
}