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
import kotlinx.android.synthetic.main.news_row_layout.view.*

/**
 * Created by Anand A <anandabktda@gmail.com>
 * News list Adapter
 * */

open class NewsListAdapter
    (val context: Context?) : RecyclerView.Adapter<NewsListAdapter.ViewHolder>() {

    var newsList: MutableList<NewsListData> = arrayListOf()

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val view =
            LayoutInflater.from(p0.context).inflate(R.layout.news_row_layout, p0, false)
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

                if (data.isSaved) {
                    itemView.iv_status.background = context!!.getDrawable(R.drawable.bg_green_view)
                    itemView.iv_status.setImageDrawable(context.getDrawable(R.drawable.ic_saved_fill))
                } else {
                    itemView.iv_status.background = context!!.getDrawable(R.drawable.bg_gray_view)
                    itemView.iv_status.setImageDrawable(context.getDrawable(R.drawable.ic_saved))
                }

                itemView.tv_title.text = data.title
                itemView.tv_description.text = data.description
                itemView.tv_date.text =
                    uTCToLocal(UTC_FORMAT, NEWS_DATE_FORMAT, data.publishedAt)
                itemView.iv_news.loadImageRoundCorner(data.urlToImage!!)

                itemView.tv_read.setOnClickListener {
                    itemOnCLick(newsList[adapterPosition])
                }

                itemView.tv_save.setOnClickListener {
                    itemOnSave(newsList[adapterPosition], true)
                }

                itemView.iv_status.setOnClickListener {
                    itemOnSave(newsList[adapterPosition], false)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    open fun itemOnCLick(data: NewsListData) {}
    open fun itemOnSave(data: NewsListData, isFromSave: Boolean) {}

    fun addAll(newsList: List<NewsListData>) {
        this.newsList.addAll(newsList)
        notifyDataSetChanged()
    }

    fun updateList() {
        notifyDataSetChanged()
    }

    fun clear() {
        this.newsList.clear()
    }
}