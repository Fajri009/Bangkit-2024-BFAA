package com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.data.retrofit

import com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.data.response.PostReviewRaesponse
import com.fajri.bangkit_2024_bfaa.BackgroundThreadDanNetworking.Retrofit.data.response.RestaurantResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("detail/{id}")
    fun getRestaurant(
        @Path("id") id: String
    ): Call<RestaurantResponse>

    // Fungsi untuk mengirim data
    @FormUrlEncoded
    @Headers("Authorization: token 12345")
    @POST("review")
    fun postReview(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("review") review: String
    ): Call<PostReviewRaesponse>
}