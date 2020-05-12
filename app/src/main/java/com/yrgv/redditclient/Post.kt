package com.yrgv.redditclient

/**
 * Defines the local model for a Post
 */
data class Post(
    val id: String,
    val title: String,
    val author: String,
    val description: String?,
    val thumbnailUrl: String?
)
//todo: make this parcelable