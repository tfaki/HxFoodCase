package com.hxfood.newsdemo.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hxfood.newsdemo.R
import com.hxfood.newsdemo.base.BaseFragment
import com.hxfood.newsdemo.databinding.FragmentNewsSourceDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsSourceDetailFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsSourceDetailBinding
    private val viewModel: NewsSourceDetailViewModel by viewModels()
    private val newsSourceDetailPagingAdapter = NewsSourceDetailPagingAdapter()
    private val args: NewsSourceDetailFragmentArgs by navArgs()

    override fun getLayoutId(): Int = R.layout.fragment_news_source_detail

    override fun bindScreen() {
        binding = setBinding()

        binding.newsSourceDetailRv.apply {
            adapter = newsSourceDetailPagingAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        newsSourceDetailPagingAdapter.onMovieClick {
            val myIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
            startActivity(myIntent)
        }

        viewModel.list.observe(viewLifecycleOwner) {
            newsSourceDetailPagingAdapter.submitData(lifecycle, it)
        }

        viewModel.setQuery(args.newsId)

    }
}