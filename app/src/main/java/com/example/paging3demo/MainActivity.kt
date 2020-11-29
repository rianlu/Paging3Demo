package com.example.paging3demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paging3demo.adapter.ArticlePagingDataAdapter
import com.example.paging3demo.adapter.FooterLoadStateAdapter
import com.example.paging3demo.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private val viewModel: ArticleViewModel by viewModels<ArticleViewModel>()
    private lateinit var adapter: ArticlePagingDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ArticlePagingDataAdapter()

        lifecycleScope.launchWhenCreated {
            viewModel.getArticles().collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        adapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.Loading -> {
                    loadStateHint.isVisible = true
                    recyclerView.isVisible = false
                    loadStateHint.text = "加载中..."
                }
                is LoadState.NotLoading -> {
                    if (adapter.snapshot().items.isEmpty()) {
                        loadStateHint.isVisible = true
                        recyclerView.isVisible = false
                        loadStateHint.text = "暂无数据"
                    } else {
                        loadStateHint.isVisible = false
                        recyclerView.isVisible = true
                    }
                }
                is LoadState.Error -> {
                    loadStateHint.isVisible = true
                    recyclerView.isVisible = false
                    loadStateHint.text = "网络错误请重试"
                    loadStateHint.setOnClickListener { adapter.retry() }
                }
            }
        }
        recyclerView.adapter = adapter.withLoadStateFooter(FooterLoadStateAdapter { adapter.retry() })
    }
}