package com.anand.newsbreeze.presentation.utility

interface AppConstants {
    companion object {

        ////////////////////////////////////////// Local Store Constants /////////////////////////////////////////////////
        const val PREF_KEY_AUTH_KEY = "pref_auth_key"
        const val DB_NAME = "NewsBreezeDatabase"

        ////////////////////////////////////////// API Constants /////////////////////////////////////////////////
        const val BASE_URL = "https://newsapi.org/v2/"
        const val API_KEY = "6101660455424d43819567f9c8c3b382"
        const val SORT_BY = "publishedAt"

        ////////////////////////////////////////// commonly using values //////////////////////////////////////////
        const val BLANK_STRING = ""
        const val UTC_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        const val NEWS_DATE_FORMAT = "dd-MM-yy"
        const val CURRENT_DATE_FORMAT = "yyyy-MM-dd"
        const val TIME_ZONE = "UTC"

        const val CHIVO_REGULAR_TTF = "chivo_regular.ttf"
        const val PUBLIC_SANS_TTF = "publicsans_regular.ttf"
        const val QUEENS_PARK_TTF = "queens_park_bold.ttf"
        const val QUEENS_PARK_NORMAL_TTF = "queens_park.ttf"
    }
}