package com.hxfood.newsdemo.remote

import com.hxfood.newsdemo.data.NewsResponse
import com.hxfood.newsdemo.utils.Consts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {

    @GET("v2/top-headlines")
    suspend fun getNewsSourceList(
        @Query("page") pageNumber: Int,
        @Query("country") country: String = Consts.COUNTRY,
        @Query("apiKey") apiKey: String = Consts.API_KEY
    ): Response<NewsResponse>

    @GET("v2/top-headlines")
    suspend fun getNewsSourceDetail(
        @Query("page") pageNumber: Int,
        @Query("sources") sources: String,
        @Query("apiKey") apiKey: String = Consts.API_KEY
    ): Response<NewsResponse>

}