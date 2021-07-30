package com.hxfood.newsdemo.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.hxfood.newsdemo.data.NewsResponse
import com.hxfood.newsdemo.remote.NewsRepository
import com.hxfood.newsdemo.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val query = MutableLiveData("")
    val list = query.switchMap { query ->
        Pager(PagingConfig(pageSize = 12)) {
            NewsPaging(repository)
        }.liveData.cachedIn(viewModelScope)
    }

    val newsLiveData = MutableLiveData<Result<NewsResponse>>()

    fun getNews(pageNumber: Int) = viewModelScope.launch {
        newsLiveData.postValue(repository.getNews(pageNumber))
    }
}