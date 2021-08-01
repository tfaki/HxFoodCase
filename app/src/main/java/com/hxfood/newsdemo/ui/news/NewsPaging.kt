package com.hxfood.newsdemo.ui.news

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hxfood.newsdemo.data.News
import com.hxfood.newsdemo.remote.NewsRepository

class NewsPaging(private val newsRepository: NewsRepository): PagingSource<Int, News>() {
    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        val page = params.key ?: 1

        return try {
            val response = newsRepository.getNewsSourceList(page)
            LoadResult.Page(
                data = response.data?.articles ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.data?.articles.isNullOrEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}