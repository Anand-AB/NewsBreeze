package com.anand.newsbreeze.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.anand.newsbreeze.R
import com.anand.newsbreeze.data.models.NewsListData
import com.anand.newsbreeze.presentation.utility.AppConstants.Companion.NEWS_DATE_FORMAT
import com.anand.newsbreeze.presentation.utility.AppConstants.Companion.UTC_FORMAT
import com.anand.newsbreeze.presentation.utility.Helper.Companion.uTCToLocal
import com.anand.newsbreeze.presentation.utility.loadImageRoundCorner
import kotlinx.android.synthetic.main.news_row_layout.view.iv_news
import kotlinx.android.synthetic.main.news_row_layout.view.tv_date
import kotlinx.android.synthetic.main.news_row_layout.view.tv_title
import kotlinx.android.synthetic.main.save_news_row_layout.view.*

/**
 * Created by Anand A <anandabktda@gmail.com>
 * Save News list Adapter
 * */

open class SaveNewsListAdapter
    (val context: Context?) : RecyclerView.Adapter<SaveNewsListAdapter.ViewHolder>() {

    var newsList: MutableList<NewsListData> = arrayListOf()

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(p0.context).inflate(R.layout.save_news_row_layout, p0, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: ViewHolder, p1: Int) {
        holder.bind(newsList[p1])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                itemOnCLick(newsList[adapterPosition])
            }
        }

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(data: NewsListData) {

            try {

                val hashtag = "#${data.source!!.name}"
                val author = " \u2022 ${data.author}"

                itemView.tv_hashtag.text = hashtag
                itemView.tv_author.text = author
                itemView.tv_title.text = data.title
                itemView.tv_date.text =
                    uTCToLocal(UTC_FORMAT, NEWS_DATE_FORMAT, data.publishedAt)
                itemView.iv_news.loadImageRoundCorner(data.urlToImage!!)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    open fun itemOnCLick(data: NewsListData) {}

    fun addAll(newsList: List<NewsListData>) {
        this.newsList.addAll(newsList)
        notifyDataSetChanged()
    }

    fun clear() {
        this.newsList.clear()
    }
}