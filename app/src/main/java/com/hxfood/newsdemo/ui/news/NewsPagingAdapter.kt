package com.hxfood.newsdemo.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hxfood.newsdemo.data.News
import com.hxfood.newsdemo.BR
import com.hxfood.newsdemo.databinding.ListItemNewsBinding

class NewsPagingAdapter : PagingDataAdapter<News, NewsPagingAdapter.NewsViewHolder>(DIFF_UTIL)  {

    private lateinit var onClick: (String) -> Unit

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val data = getItem(position)

        holder.viewDataBinding.setVariable(BR.news, data)

        holder.viewDataBinding.root.setOnClickListener {
            data?.source?.id?.let {
                onClick(it)
            } ?: run {
                onClick("null")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ListItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    inner class NewsViewHolder(val viewDataBinding: ListItemNewsBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    fun onMovieClick(listener: (String) -> Unit) {
        onClick = listener
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, item: News): Boolean {
                return oldItem.source?.id == item.source?.id
            }

            override fun areContentsTheSame(oldItem: News, item: News): Boolean {
                return oldItem == item
            }
        }
    }
}