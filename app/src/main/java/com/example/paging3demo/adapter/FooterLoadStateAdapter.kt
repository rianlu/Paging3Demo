package com.example.paging3demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3demo.R
import kotlinx.android.synthetic.main.layout_footer_load_state.view.*

class FooterLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FooterLoadStateAdapter.ViewHolder>() {

    class ViewHolder(retry: () -> Unit, itemView: View) : RecyclerView.ViewHolder(itemView) {
        val loadStateHint: TextView? = itemView.loadStateHint
        val progressBar: ProgressBar? = itemView.progressBar
        init {
            loadStateHint?.setOnClickListener { retry.invoke() }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.progressBar?.isVisible = loadState is LoadState.Loading
        when (loadState) {
            is LoadState.Error -> {
                holder.loadStateHint?.text = "加载失败，点击重试"
            }
            is LoadState.Loading -> {
                holder.loadStateHint?.text = "加载中..."
            }
            else -> {
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            retry,
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.layout_footer_load_state,
                    parent,
                    false
                )
        )
    }
}