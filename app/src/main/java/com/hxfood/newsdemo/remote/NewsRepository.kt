package com.hxfood.newsdemo.remote

import com.hxfood.newsdemo.data.NewsResponse
import com.hxfood.newsdemo.utils.Status
import com.hxfood.newsdemo.utils.Result

open class NewsRepository(private val newsInterface: NewsInterface) {

    suspend fun getNewsSourceList(pageNumber: Int): Result<NewsResponse> {
        return try {
            val response = newsInterface.getNewsSourceList(pageNumber)
            if (response.isSuccessful) {
                Result(Status.SUCCESS, response.body())
            } else {
                Result(Status.ERROR, null)
            }
        } catch (e: Exception) {
            Result(Status.ERROR, null)
        }
    }

    suspend fun getNewsSourceDetail(pageNumber: Int, sources: String): Result<NewsResponse> {
        return try {
            val response = newsInterface.getNewsSourceDetail(pageNumber, sources)
            if (response.isSuccessful) {
                Result(Status.SUCCESS, response.body())
            } else {
                Result(Status.ERROR, null)
            }
        } catch (e: Exception) {
            Result(Status.ERROR, null)
        }
    }
}