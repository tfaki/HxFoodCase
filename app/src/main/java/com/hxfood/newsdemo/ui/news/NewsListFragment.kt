package com.hxfood.newsdemo.ui.news

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hxfood.newsdemo.R
import com.hxfood.newsdemo.base.BaseFragment
import com.hxfood.newsdemo.databinding.FragmentNewsListBinding
import com.hxfood.newsdemo.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsListFragment : BaseFragment() {

    private lateinit var binding: FragmentNewsListBinding
    private val viewModel: NewsViewModel by viewModels()
    private val newsPagingAdapter = NewsPagingAdapter()

    override fun getLayoutId(): Int = R.layout.fragment_news_list

    override fun bindScreen() {
        binding = setBinding()

        binding.newsRv.apply {
            adapter = newsPagingAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        arrangeClick()
        arrangeObserver()
        sendRequest()
    }

    private fun arrangeClick() {
        newsPagingAdapter.onNewsClick {
            if (it != "null"){
                val action = NewsListFragmentDirections.actionNewsFragmentToDetailsFragment(it)
                findNavController().navigate(action)
            } else {
                requireContext().toast(getString(R.string.news_source_not_found))
            }
        }
    }

    private fun arrangeObserver() {
        viewModel.list.observe(viewLifecycleOwner) {
            newsPagingAdapter.submitData(lifecycle, it)
        }
    }

    private fun sendRequest() {
        viewModel.setQuery()
    }
}