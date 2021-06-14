package com.anand.newsbreeze.presentation.utility

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.anand.newsbreeze.R
import com.anand.newsbreeze.presentation.newsDetail.NewsDetailActivity
import com.anand.newsbreeze.presentation.saveNewsList.SavedNewsListActivity

class UiHelper {

    companion object {

        fun startNewsDetail(context: AppCompatActivity, clearTop: Boolean? = false) {
            val intent = Intent(context, NewsDetailActivity::class.java)
            if (clearTop!!) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            context.startActivity(intent)
            animateTransition(context)
        }

        fun startSavedNewsList(context: AppCompatActivity, clearTop: Boolean? = false) {
            val intent = Intent(context, SavedNewsListActivity::class.java)
            if (clearTop!!) {
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            context.startActivity(intent)
            animateTransition(context)
        }

        private fun animateTransition(activity: Activity) {
            activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}