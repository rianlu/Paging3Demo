package com.example.paging3demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.paging3demo.R
import com.example.paging3demo.viewmodel.ArticleViewModel.UiModel
import kotlinx.android.synthetic.main.item_article.view.*
import java.lang.UnsupportedOperationException

class ArticlePagingDataAdapter :
    PagingDataAdapter<UiModel, RecyclerView.ViewHolder>(
        ArticleComparator
    ) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView? = itemView.title
        val author: TextView? = itemView.author
    }

    class SeparatorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {
        val ArticleComparator = object : DiffUtil.ItemCallback<UiModel>() {
            override fun areItemsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return (oldItem is UiModel.ArticleItem && newItem is UiModel.ArticleItem &&
                        oldItem.article.id == newItem.article.id) || (oldItem is UiModel.SeparatorItem &&
                        newItem is UiModel.SeparatorItem && oldItem.articleId == newItem.articleId)
            }

            override fun areContentsTheSame(oldItem: UiModel, newItem: UiModel): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { uiModel ->
            when (uiModel) {
                is UiModel.ArticleItem -> {
                    holder as ViewHolder
                    holder.title?.text = uiModel.article.title
                    uiModel.article.author.let {
                        holder.author?.text = if (it.isEmpty()) "Unknown" else it
                    }
                }
                is UiModel.SeparatorItem -> {
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is UiModel.ArticleItem -> {
                R.layout.item_article
            }
            is UiModel.SeparatorItem -> {
                R.layout.item_separator
            }
            else -> {
                throw UnsupportedOperationException("Unknown View")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == R.layout.item_article) {
            ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_article,
                    parent,
                    false
                )
            )
        } else {
            SeparatorViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_separator,
                    parent,
                    false
                )
            )
        }
    }
}