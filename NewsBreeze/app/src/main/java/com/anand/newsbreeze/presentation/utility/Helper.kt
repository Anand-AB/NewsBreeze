package com.anand.newsbreeze.presentation.utility

import android.content.Context
import android.content.SharedPreferences
import com.anand.newsbreeze.MyApplication
import com.anand.newsbreeze.presentation.utility.AppConstants.Companion.CURRENT_DATE_FORMAT
import com.anand.newsbreeze.presentation.utility.AppConstants.Companion.TIME_ZONE
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Helper {
    companion object {
        private var sharedpreferences: SharedPreferences? = null
        private val PREFERENCE = "pref_health_provider"

        /**
         * Get string value from shared preferences
         *
         * @param key key of value
         * @return value of the key
         */
        fun getStringValue(key: String): String? {
            sharedpreferences = MyApplication.getAppInstance()
                ?.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            return sharedpreferences?.getString(key, "")
        }

        fun getCurrentDate(): String? {
            val sdf = SimpleDateFormat(CURRENT_DATE_FORMAT)
            return sdf.format(Date())
        }

        fun uTCToLocal(
            dateFormatInPut: String?,
            dateFomratOutPut: String?,
            datesToConvert: String?
        ): String? {
            var dateToReturn = datesToConvert
            val sdf = SimpleDateFormat(dateFormatInPut)
            sdf.timeZone = TimeZone.getTimeZone(TIME_ZONE)
            var gmt: Date? = null
            val sdfOutPutToSend =
                SimpleDateFormat(dateFomratOutPut)
            sdfOutPutToSend.timeZone = TimeZone.getDefault()
            try {
                gmt = sdf.parse(datesToConvert)
                dateToReturn = sdfOutPutToSend.format(gmt)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return dateToReturn
        }

    }
}