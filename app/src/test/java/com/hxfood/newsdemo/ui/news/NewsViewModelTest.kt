package com.hxfood.newsdemo.ui.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hxfood.newsdemo.data.NewsResponse
import com.hxfood.newsdemo.remote.NewsRepository
import kotlinx.coroutines.*
import com.hxfood.newsdemo.utils.Result
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ObsoleteCoroutinesApi
class NewsViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: NewsRepository

    private lateinit var viewModel: NewsViewModel

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @Before
    fun init() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = NewsViewModel(repository)
    }

    @Test
    fun `get news list`() {
        runBlocking {
            val observer = Mockito.mock(Observer::class.java) as Observer<Result<NewsResponse>>
            viewModel.newsLiveData.observeForever(observer)
            val response = viewModel.getNews(1)

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