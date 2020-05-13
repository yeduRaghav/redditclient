package com.yrgv.redditclient.utils.extensions

import com.yrgv.redditclient.Post
import com.yrgv.redditclient.R
import com.yrgv.redditclient.network.PostsResponse
import com.yrgv.redditclient.utils.ResourceProvider

/**
 * Extension functions for Post objects
 */
//todo:test and comment why
fun PostsResponse.Post.PostData.toLocalModel(resourceProvider: ResourceProvider): Post {
    return Post(
        id = id,
        title = title,
        author = resourceProvider.getString(R.string.post_author)?.plus(author) ?: author,
        description = selftext.takeIf { it.isNotBlank() },
        thumbnailUrl = thumbnail.getSanitizedThumbnailUrl()
    )
}


//todo: test
fun String.getSanitizedThumbnailUrl(): String? {
    return this.takeIf { startsWith("https", true) }
}