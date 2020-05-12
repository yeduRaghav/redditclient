package com.yrgv.redditclient.utils.extensions

import com.yrgv.redditclient.Post
import com.yrgv.redditclient.network.PostsResponse

/**
 * Extension functions for Post objects
 */
//todo:test and comment why
fun PostsResponse.Post.PostData.toLocalModel(): Post {
    return Post(
        id = id,
        title = title.fromHtml(),
        author = author,
        description = selftext.fromHtml(),
        thumbnailUrl = thumbnail.getSanitizedThumbnailUrl()
    )
}