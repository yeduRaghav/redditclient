package com.yrgv.redditclient

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Defines the local model for a Post
 */
@Parcelize
data class Post(
    val id: String,
    val title: String,
    val author: String,
    val description: String?,
    val thumbnailUrl: String?
) : Parcelable