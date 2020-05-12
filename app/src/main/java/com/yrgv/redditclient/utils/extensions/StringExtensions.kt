package com.yrgv.redditclient.utils.extensions

import android.text.Html
import androidx.core.text.HtmlCompat.TO_HTML_PARAGRAPH_LINES_CONSECUTIVE

/**
 * Extension functions for String objects
 */

//todo: add tests for empty replace, number replace, replace chars including {}
fun String.replaceBrace(replaceWith: String): String {
    return this.replaceFirst("{}", replaceWith)
}

//todo: add comments, try to add tests

fun String.fromHtml(): String {
    return Html.fromHtml(this).toString()
}

//todo: add tests for empty, values of self and comment defining why this is needed
fun String.getSanitizedThumbnailUrl(): String? {
    return this.takeIf { startsWith("https", true) }
}