package com.hxfood.newsdemo.ui.detail

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
class NewsSourceDetailViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val query = MutableLiveData("")
    val list = query.switchMap { query ->
        Pager(PagingConfig(pageSize = 50)) {
            NewsSourceDetailPaging(query, repository)
        }.liveData.cachedIn(viewModelScope)
    }

    fun setQuery(source: String) {
        query.postValue(source)
    }

    val newsSourceDetailLiveData = MutableLiveData<Result<NewsResponse>>()

    fun getNewsSourceDetail(pageNumber: Int, source: String) = viewModelScope.launch {
        newsSourceDetailLiveData.postValue(repository.getNewsSourceDetail(pageNumber, source))
    }
}