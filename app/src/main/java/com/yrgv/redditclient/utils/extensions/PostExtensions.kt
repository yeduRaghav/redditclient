package com.yrgv.redditclient.utils.extensions

import com.yrgv.redditclient.Post
import com.yrgv.redditclient.R
import com.yrgv.redditclient.network.PostsResponse

/**
 * Extension functions for Post objects
 */

/**
 * Converts Post Api response into local model
 * @param stringResourceProvider function must return the String for the string resId.
 * */
fun PostsResponse.Post.PostData.toLocalModel(stringResourceProvider: (resourceId: Int) -> String?): Post {
    return Post(
        id = id,
        title = title,
        author = author.getLocalizedAuthor(stringResourceProvider),
        description = selftext.takeIf { it.isNotBlank() },
        thumbnailUrl = thumbnail.getSanitizedThumbnailUrl()
    )
}

/**
 * Prepends "u/" to the author's name
 * */
fun String.getLocalizedAuthor(
    stringResourceProvider: (resourceId: Int) -> String?
): String {
    return stringResourceProvider(R.string.post_author)?.plus(this) ?: this
}


/**
 * THis is necessary because the url from api can be empty, contain
 * just whitespace or in some cases say 'self' based on the Post category.
 * */
fun String.getSanitizedThumbnailUrl(): String? {
    return this.takeIf { startsWith("https://", true) }
}