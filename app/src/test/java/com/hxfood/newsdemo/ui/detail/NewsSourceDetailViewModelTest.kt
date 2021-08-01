package com.hxfood.newsdemo.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hxfood.newsdemo.data.NewsResponse
import com.hxfood.newsdemo.remote.NewsRepository
import com.hxfood.newsdemo.utils.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ObsoleteCoroutinesApi
class NewsSourceDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: NewsRepository

    private lateinit var viewModel: NewsSourceDetailViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = NewsSourceDetailViewModel(repository)
    }

    @Test
    fun `get news source list by source query`() {
        runBlocking {
            val observer = Mockito.mock(Observer::class.java) as Observer<Result<NewsResponse>>
            viewModel.newsSourceDetailLiveData.observeForever(observer)
            val response = viewModel.getNewsSourceDetail(1, "cnn")

            Assert.assertNotNull(response)
        }
    }

    @ExperimentalCoroutinesApi
    @After
    fun finish() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}