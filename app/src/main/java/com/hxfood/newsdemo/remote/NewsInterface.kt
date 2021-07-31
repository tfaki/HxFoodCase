package com.hxfood.newsdemo.remote

import com.hxfood.newsdemo.data.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {

    @GET("v2/top-headlines")
    suspend fun getAllNews(
        @Query("page") pageNumber: Int,
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = "8ec5b28f7aca433bbea6ec7f32e584f2"
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getNewsSourceDetail(
        @Query("page") pageNumber: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = "8ec5b28f7aca433bbea6ec7f32e584f2"
    ): Response<NewsResponse>

}