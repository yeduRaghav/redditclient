package com.yrgv.redditclient.network

import com.google.gson.annotations.SerializedName

/**
 * Defines the Api Response models with only necessary value.
 */

data class PostsResponse(
    @SerializedName("data") val data: PostsData
) {
    data class PostsData(
        @SerializedName("children") val children: List<Post>
    )

    data class Post(
        @SerializedName("data") val data: PostData
    ) {
        data class PostData(
            @SerializedName("title") val title: String,
            @SerializedName("thumbnail") val thumbnail: String,
            @SerializedName("selftext") val selftext: String,
            @SerializedName("author") val author: String
        )
    }
}


