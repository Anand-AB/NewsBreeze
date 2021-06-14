package com.anand.newsbreeze.data.models

import androidx.room.*
import com.google.gson.annotations.SerializedName

/**
 * Created by Anand A <anandabktda@gmail.com>
 * The model file contains News list params, also defined news table with additional param isSaved
 * */

class NewsListRSP : BaseResponse() {

    var articles: List<NewsListData>? = emptyList()
}

@Entity(tableName = "news", indices = [Index(value = ["title", "publishedAt"], unique = true)])
data class NewsListData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") @SerializedName("id") val id: Long?,
    @ColumnInfo(name = "author") @SerializedName("author") val author: String?,
    @ColumnInfo(name = "title") @SerializedName("title") val title: String?,
    @ColumnInfo(name = "description") @SerializedName("description") val description: String?,
    @ColumnInfo(name = "url") @SerializedName("url") val url: String?,
    @ColumnInfo(name = "urlToImage") @SerializedName("urlToImage") val urlToImage: String?,
    @ColumnInfo(name = "publishedAt") @SerializedName("publishedAt") val publishedAt: String?,
    @ColumnInfo(name = "content") @SerializedName("content") val content: String?,
    @ColumnInfo(name = "isSaved") @SerializedName("isSaved") var isSaved: Boolean = false,
    @Embedded val source: SourceData? = null
)

data class SourceData(
    val name: String
)