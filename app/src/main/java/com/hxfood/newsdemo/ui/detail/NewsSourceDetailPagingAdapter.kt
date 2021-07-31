package com.hxfood.newsdemo.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hxfood.newsdemo.BR
import com.hxfood.newsdemo.data.News
import com.hxfood.newsdemo.databinding.ListItemNewsSourceDetailBinding
import com.hxfood.newsdemo.ui.news.NewsPagingAdapter

class NewsSourceDetailPagingAdapter : PagingDataAdapter<News, NewsSourceDetailPagingAdapter.NewsSourceDetailViewHolder>(NewsPagingAdapter.DIFF_UTIL)  {

    private lateinit var onClick: (String) -> Unit

    override fun onBindViewHolder(holder: NewsSourceDetailViewHolder, position: Int) {
        val data = getItem(position)

        holder.viewDataBinding.setVariable(BR.news, data)

        holder.viewDataBinding.root.setOnClickListener {
            data?.url?.let { it1 -> onClick(it1) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSourceDetailViewHolder {
        val binding = ListItemNewsSourceDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsSourceDetailViewHolder(binding)
    }

    inner class NewsSourceDetailViewHolder(val viewDataBinding: ListItemNewsSourceDetailBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    fun onMovieClick(listener: (String) -> Unit) {
        onClick = listener
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, item: News): Boolean {
                return oldItem.source.id == item.source.id
            }

            override fun areContentsTheSame(oldItem: News, item: News): Boolean {
                return oldItem == item
            }
        }
    }
}