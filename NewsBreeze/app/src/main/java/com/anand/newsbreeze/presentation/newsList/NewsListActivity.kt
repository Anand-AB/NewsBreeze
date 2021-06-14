package com.anand.newsbreeze.presentation.newsList

import android.view.View
import android.widget.RadioButton
import android.widget.SearchView
import androidx.lifecycle.Observer
import com.anand.newsbreeze.MyApplication
import com.anand.newsbreeze.R
import com.anand.newsbreeze.data.models.NewsListData
import com.anand.newsbreeze.presentation.adapter.NewsListAdapter
import com.anand.newsbreeze.presentation.core.BaseActivity
import com.anand.newsbreeze.presentation.utility.AppConstants.Companion.BLANK_STRING
import com.anand.newsbreeze.presentation.utility.UiHelper
import com.anand.newsbreeze.presentation.utility.isNetworkConnected
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.filter_bottom_sheet.view.*
import kotlinx.android.synthetic.main.layout_toolbar_main.*
import kotlinx.android.synthetic.main.layout_toolbar_main.view.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The Activity used for showing news list
 * */

class NewsListActivity : BaseActivity() {

    private var newsListAdapter: NewsListAdapter? = null
    private var newsList: List<NewsListData> = arrayListOf()
    private var selectedFilter: String? = null
    private var selectedFilterId: Int? = null

    override fun getLayout(): Int {
        return R.layout.activity_news_list
    }

    private val newsListViewModel: NewsListViewModel by viewModel()
    override fun getBaseViewModel() = newsListViewModel

    override fun initiation() {
        super.initiation()

        setSupportActionBar(toolbar)
        setAdapter()

        if (isNetworkConnected()) {
            newsListViewModel.getNewsListCall()
            showProgress()
        } else
            newsListViewModel.getNewsListSaved()

    }

    private fun setAdapter() {

        newsListAdapter = object : NewsListAdapter(this) {

            override fun itemOnCLick(data: NewsListData) {
                super.itemOnCLick(data)

                MyApplication.setSelectedNews(data)
                UiHelper.startNewsDetail(this@NewsListActivity)

            }

            override fun itemOnSave(data: NewsListData, isFromSave: Boolean) {
                super.itemOnSave(data, isFromSave)

                if (!data.isSaved) {
                    newsListViewModel.updateNews(data.id!!, true)
                    data.isSaved = true
                } else if (isFromSave)
                    toast(getString(R.string.text_already_saved))
                else {
                    newsListViewModel.updateNews(data.id!!, false)
                    data.isSaved = false
                }
            }
        }

        rv_news.adapter = newsListAdapter

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

    private fun filterList() {

        if (selectedFilter != null) {

            if (selectedFilter == getString(R.string.text_relevance)) {
                setNewsData(newsList)
            } else if (selectedFilter == getString(R.string.text_saved_first)) {

                val savedList = arrayListOf<NewsListData>()
                val unSavedList = arrayListOf<NewsListData>()

                for (i in newsList.indices) {

                    if (newsList[i].isSaved)
                        savedList.add(newsList[i])
                    else
                        unSavedList.add(newsList[i])
                }

                savedList.addAll(unSavedList)
                setNewsData(savedList)

            } else if (selectedFilter == getString(R.string.text_saved_last)) {

                val savedList = arrayListOf<NewsListData>()
                val unSavedList = arrayListOf<NewsListData>()

                for (i in newsList.indices) {

                    if (newsList[i].isSaved)
                        savedList.add(newsList[i])
                    else
                        unSavedList.add(newsList[i])
                }

                unSavedList.addAll(savedList)
                setNewsData(unSavedList)

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
        newsListAdapter?.clear()
        newsListAdapter?.addAll(newsList)

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
            newsListViewModel.getNewsListSaved()

    }

    override fun setOnClickListener() {
        super.setOnClickListener()

        toolbar.iv_save.setOnClickListener {
            UiHelper.startSavedNewsList(this@NewsListActivity)
        }

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

        iv_filter.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.filter_bottom_sheet, null)
            val dialog = BottomSheetDialog(this@NewsListActivity)
            dialog.setContentView(view)
            dialog.show()

            if (selectedFilterId != null) {

                val rb = view.findViewById(selectedFilterId!!) as RadioButton
                rb.isChecked = true
            } else {
                val rb = view.findViewById(R.id.rb_relevance) as RadioButton
                rb.isChecked = true
            }

            view.rg_filter.setOnCheckedChangeListener { _, checkedId ->
                val rb = view.findViewById(checkedId) as RadioButton
                selectedFilter = rb.text.toString()
                selectedFilterId = checkedId
                dialog.dismiss()

                filterList()
            }

        }

    }

    override fun getApiResponse() {
        super.getApiResponse()

        newsListViewModel.newsListLiveData.observe(this, Observer {

            if (it.isSuccess) {
                if (it.articles!!.isNotEmpty()) {
                    newsListViewModel.insertNewsList(it.articles!!)
                }
            } else {
                newsListEmpty(it.message!!)
            }
        })

        newsListViewModel.newsListSavedLiveData.observe(this, Observer {

            if (it.isNotEmpty()) {
                newsList = it
                setNewsData(it)
            } else {
                newsListEmpty(getString(R.string.text_no_data))
            }
        })

        newsListViewModel.updateNewsLiveData.observe(this, Observer {
            newsListAdapter!!.updateList()
        })

    }

}
