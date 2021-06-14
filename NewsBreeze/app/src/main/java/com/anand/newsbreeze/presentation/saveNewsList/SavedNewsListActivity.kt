package com.anand.newsbreeze.presentation.saveNewsList

import android.view.View
import android.widget.SearchView
import androidx.lifecycle.Observer
import com.anand.newsbreeze.MyApplication
import com.anand.newsbreeze.R
import com.anand.newsbreeze.data.models.NewsListData
import com.anand.newsbreeze.presentation.adapter.SaveNewsListAdapter
import com.anand.newsbreeze.presentation.core.BaseActivity
import com.anand.newsbreeze.presentation.utility.AppConstants.Companion.BLANK_STRING
import com.anand.newsbreeze.presentation.utility.UiHelper
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.layout_toolbar_main.*
import kotlinx.android.synthetic.main.layout_toolbar_saved.*
import kotlinx.android.synthetic.main.layout_toolbar_saved.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The Activity used for showing saved news list
 * */

class SavedNewsListActivity : BaseActivity() {

    private var saveNewsListAdapter: SaveNewsListAdapter? = null
    private var newsList: List<NewsListData> = arrayListOf()

    override fun getLayout(): Int {
        return R.layout.activity_saved_news_list
    }

    private val newsListViewModel: SavedNewsListViewModel by viewModel()
    override fun getBaseViewModel() = newsListViewModel

    override fun initiation() {
        super.initiation()

        setSupportActionBar(toolbar)
        setAdapter()

        newsListViewModel.getSavedNewsList()
        showProgress()

    }

    private fun setAdapter() {

        saveNewsListAdapter = object : SaveNewsListAdapter(this) {

            override fun itemOnCLick(data: NewsListData) {
                super.itemOnCLick(data)

                MyApplication.setSelectedNews(data)
                UiHelper.startNewsDetail(this@SavedNewsListActivity)

            }

        }

        rv_news.adapter = saveNewsListAdapter

    }

    private fun searchData(query: String) {

        if (query != BLANK_STRING) {
            val newsSearch = arrayListOf<NewsListData>()

            if (newsList.isNotEmpty()) {

                for (i in newsList.indices) {

                    if (newsList[i].title!!.contains(query, true)
                    ) {
                        newsSearch.add(newsList[i])
                    }

                }

            }

            if (newsSearch.isNotEmpty()) {
                setNewsData(newsSearch)
            } else {
                newsListEmpty(getString(R.string.text_no_data))
            }

        } else {

            if (newsList.isNotEmpty()) {
                setNewsData(newsList)
            } else {
                newsListEmpty(getString(R.string.text_no_data))
            }

        }

    }

    private fun setNewsData(newsList: List<NewsListData>) {
        hideProgress()
        saveNewsListAdapter?.clear()
        saveNewsListAdapter?.addAll(newsList)

        rv_news.visibility = View.VISIBLE
        tv_no_data.visibility = View.GONE
    }

    private fun newsListEmpty(message: String) {
        hideProgress()
        rv_news.visibility = View.GONE
        tv_no_data.visibility = View.VISIBLE
        tv_no_data.text = message
    }

    override fun onResume() {
        super.onResume()

        if (newsList.isNotEmpty())
            newsListViewModel.getSavedNewsList()

    }

    override fun setOnClickListener() {
        super.setOnClickListener()

        search!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(query: String): Boolean {
                searchData(query)
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchData(query)
                return false
            }

        })

        toolbar_saved.iv_back.setOnClickListener {
            onBackPressed()
        }
    }

    override fun getApiResponse() {
        super.getApiResponse()

        newsListViewModel.newsListSavedLiveData.observe(this, Observer {

            if (it.isNotEmpty()) {
                newsList = it
                setNewsData(it)
            } else {
                newsListEmpty(getString(R.string.text_no_data))
            }
        })
    }

}
