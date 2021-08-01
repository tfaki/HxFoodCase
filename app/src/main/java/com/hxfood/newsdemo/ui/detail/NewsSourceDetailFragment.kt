package com.hxfood.newsdemo.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hxfood.newsdemo.HxFoodApp
import com.hxfood.newsdemo.R
import com.hxfood.newsdemo.base.BaseFragment
import com.hxfood.newsdemo.databinding.FragmentNewsSourceDetailBinding
import com.hxfood.newsdemo.room.DatabaseManager
import com.hxfood.newsdemo.room.Item
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsSourceDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsSourceDetailBinding
    private val viewModel: NewsSourceDetailViewModel by viewModels()
    private lateinit var newsSourceDetailPagingAdapter: NewsSourceDetailPagingAdapter
    private val args: NewsSourceDetailFragmentArgs by navArgs()
    private lateinit var databaseManager: DatabaseManager
    val handler = Handler(Looper.myLooper()!!)

    override fun getLayoutId(): Int = R.layout.fragment_news_source_detail

    override fun bindScreen() {
        binding = setBinding()
        databaseManager = DatabaseManager.getInstance(requireContext())!!
        newsSourceDetailPagingAdapter = NewsSourceDetailPagingAdapter(databaseManager)

        binding.newsSourceDetailRv.apply {
            adapter = newsSourceDetailPagingAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        arrangeClicks()
        arrangeObserver()
        sendRequest()
        countDownTimer()
    }

    private fun arrangeClicks() {
        newsSourceDetailPagingAdapter.onClick {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(myIntent)
        }

        newsSourceDetailPagingAdapter.onFavClick { title ->
            val itemModel = Item()
            if (databaseManager.itemDao().getAllItem().isNotEmpty()) {
                databaseManager.itemDao().getAllItem().forEach { item ->
                    if (item.title != title) {
                        itemModel.title = title
                        databaseManager.itemDao().insert(itemModel)
                    } else {
                        databaseManager.itemDao().deleteByTitle(title)
                    }
                }
            } else {
                itemModel.title = title
                databaseManager.itemDao().insert(itemModel)
            }
            newsSourceDetailPagingAdapter.notifyDataSetChanged()
        }
    }

    private fun arrangeObserver() {
        viewModel.list.observe(viewLifecycleOwner) {
            newsSourceDetailPagingAdapter.submitData(lifecycle, it)
        }
    }

    private fun sendRequest() {
        viewModel.setQuery(args.newsId)
    }

    private fun countDownTimer() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                //sendRequest()
                handler.postDelayed(this, 60000) //now is every 1 minutes
            }
        }, 60000) //Every 60000 ms (1 minutes)
    }
}