package com.djphy.myapptemplate.home.model

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Keep
@Parcelize
data class PopularListResponse(
    @SerializedName("results")
    @Expose
    var itemList: List<ArticleItem>? = null

) : Parcelable {
    @Keep
    @Parcelize
    data class ArticleItem(
        @SerializedName("title")
        @Expose
        var title: String = "",
        @SerializedName("byline")
        @Expose
        var author: String = "",
        @SerializedName("published_date")
        @Expose
        var published: String = "",
        @SerializedName("url")
        @Expose
        var articleLink: String? = null,
        @SerializedName("media")
        @Expose
        var mediaList: List<MediaItem>? = null,
    ) : Parcelable {

        @Keep
        @Parcelize
        data class MediaItem(
            @SerializedName("media-metadata")
            @Expose
            var metaDataList: List<MediaMetaDataItem>? = null,
        ) : Parcelable {

            @Keep
            @Parcelize
            data class MediaMetaDataItem(
                @SerializedName("url")
                @Expose
                var imageUrl: String? = null
            ) : Parcelable
        }
    }
}