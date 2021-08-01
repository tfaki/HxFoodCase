package com.hxfood.newsdemo.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hxfood.newsdemo.BR
import com.hxfood.newsdemo.HxFoodApp
import com.hxfood.newsdemo.R
import com.hxfood.newsdemo.data.News
import com.hxfood.newsdemo.databinding.ListItemNewsSourceDetailBinding
import com.hxfood.newsdemo.room.DatabaseManager

class NewsSourceDetailPagingAdapter(private val databaseManager: DatabaseManager) : PagingDataAdapter<News, NewsSourceDetailPagingAdapter.NewsSourceDetailViewHolder>(DIFF_UTIL) {

    private lateinit var onClick: (String) -> Unit
    private lateinit var onFavClick: (String) -> Unit

    override fun onBindViewHolder(holder: NewsSourceDetailViewHolder, position: Int) {
        val data = getItem(position)

        holder.viewDataBinding.setVariable(BR.news, data)

        holder.viewDataBinding.root.setOnClickListener {
            data?.url?.let { onClick(it) }
        }

        holder.viewDataBinding.addMyListTv.setOnClickListener {
            data?.title?.let { onFavClick(it) }
        }

        if (databaseManager.itemDao().getAllItem().isNotEmpty()){
            for(item in databaseManager.itemDao().getAllItem()){
                if (item.title == data?.title) {
                    holder.viewDataBinding.addMyListTv.text = HxFoodApp.applicationContext().getString(R.string.remove_from_my_list)
                    return
                } else {
                    holder.viewDataBinding.addMyListTv.text = HxFoodApp.applicationContext().getString(R.string.add_my_list)
                }
            }
        } else {
            holder.viewDataBinding.addMyListTv.text = HxFoodApp.applicationContext().getString(R.string.add_my_list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsSourceDetailViewHolder {
         val binding = ListItemNewsSourceDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NewsSourceDetailViewHolder(binding)
    }

    inner class NewsSourceDetailViewHolder(val viewDataBinding: ListItemNewsSourceDetailBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)

    fun onClick(listener: (String) -> Unit) {
        onClick = listener
    }

    fun onFavClick(listener: (String) -> Unit) {
        onFavClick = listener
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