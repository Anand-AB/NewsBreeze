package com.anand.newsbreeze.presentation.newsDetail

import android.graphics.PorterDuff
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.anand.newsbreeze.R
import com.anand.newsbreeze.data.models.NewsListData
import com.anand.newsbreeze.presentation.core.BaseActivity
import com.anand.newsbreeze.presentation.utility.AppConstants.Companion.BLANK_STRING
import com.anand.newsbreeze.presentation.utility.AppConstants.Companion.NEWS_DATE_FORMAT
import com.anand.newsbreeze.presentation.utility.AppConstants.Companion.UTC_FORMAT
import com.anand.newsbreeze.presentation.utility.Helper
import com.anand.newsbreeze.presentation.utility.PrefKeys
import com.anand.newsbreeze.presentation.utility.loadImage
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs
import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.layout_toolbar_detail.*
import kotlinx.android.synthetic.main.layout_toolbar_detail.view.*
import kotlinx.android.synthetic.main.layout_toolbar_main.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The Activity used for showing news details
 * */

class NewsDetailActivity : BaseActivity() {

    private var newsData: NewsListData? = null

    override fun getLayout(): Int {
        return R.layout.activity_news_detail
    }

    private val newsDetailViewModel: NewsDetailViewModel by viewModel()
    override fun getBaseViewModel() = newsDetailViewModel

    override fun initiation() {
        super.initiation()

        setSupportActionBar(toolbar)

        val newsDetailStr = Prefs.getString(PrefKeys.SelectedNewsData, BLANK_STRING)

        if (newsDetailStr.isNotEmpty()) {
            newsData = Gson().fromJson(newsDetailStr, NewsListData::class.java)
            setData()
        }

    }

    private fun updateSaveIcon() {
        if (newsData!!.isSaved)
            toolbar_detail.iv_save.setImageDrawable(resources.getDrawable(R.drawable.ic_saved_fill))
        else
            toolbar_detail.iv_save.setImageDrawable(resources.getDrawable(R.drawable.ic_saved_small))
    }

    private fun setData() {
        tv_date.text =
            Helper.uTCToLocal(UTC_FORMAT, NEWS_DATE_FORMAT, newsData!!.publishedAt)
        tv_title.text = newsData!!.title
        tv_author.text = newsData!!.author
        tv_description.text = newsData!!.description

        header.loadImage(newsData!!.urlToImage!!)

        updateSaveIcon()
    }

    override fun setOnClickListener() {
        super.setOnClickListener()

        app_bar.addOnOffsetChangedListener(OnOffsetChangedListener { _, verticalOffset ->
            if (toolbar_layout.height + verticalOffset < 2 * ViewCompat.getMinimumHeight(
                    toolbar_layout
                )
            ) {

                val param = nsv_main.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(0, 0, 0, 0)
                nsv_main.layoutParams =
                    param // Tested!! - You need this line for the params to be applied.

                toolbar_detail.iv_back!!.setColorFilter(
                    resources.getColor(R.color.title_color),
                    PorterDuff.Mode.SRC_ATOP
                )

                toolbar_detail.iv_save!!.setColorFilter(
                    resources.getColor(R.color.title_color),
                    PorterDuff.Mode.SRC_ATOP
                )
            } else {

                val param = nsv_main.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(0, -45, 0, 0)
                nsv_main.layoutParams =
                    param // Tested!! - You need this line for the params to be applied.

                toolbar_detail.iv_back!!.setColorFilter(
                    resources.getColor(R.color.text_white),
                    PorterDuff.Mode.SRC_ATOP
                )

                toolbar_detail.iv_save!!.setColorFilter(
                    resources.getColor(R.color.text_white),
                    PorterDuff.Mode.SRC_ATOP
                )
            }
        })

        toolbar_detail.iv_back.setOnClickListener {
            onBackPressed()
        }

        toolbar_detail.iv_save.setOnClickListener {
            if (!newsData!!.isSaved) {
                newsDetailViewModel.updateNews(newsData!!.id!!, true)
                newsData!!.isSaved = true
            } else {
                newsDetailViewModel.updateNews(newsData!!.id!!, false)
                newsData!!.isSaved = false
            }
            updateSaveIcon()
        }

        tv_save.setOnClickListener {
            if (!newsData!!.isSaved) {
                newsDetailViewModel.updateNews(newsData!!.id!!, true)
                newsData!!.isSaved = true
                updateSaveIcon()
            } else {
                toast(getString(R.string.text_already_saved))
            }
        }
    }

}
