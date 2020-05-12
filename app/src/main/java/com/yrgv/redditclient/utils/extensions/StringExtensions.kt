package com.yrgv.redditclient.utils.extensions

/**
 * Extension functions for String objects
 */

//todo: add tests for empty, values of self and comment defining why this is needed
fun String.getSanitizedThumbnailUrl(): String? {
    return this.takeIf { startsWith("https", true) }
}