package com.yrgv.redditclient.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yrgv.redditclient.Post

/**
 * View Model for the Post Detail screen.
 */
class PostDetailScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val title = MutableLiveData<String>()
    private val author = MutableLiveData<String>()
    private val thumbnail = MutableLiveData<String>()
    private val description = MutableLiveData<String>()

    fun setData(post: Post) {
        title.postValue(post.title)
        author.postValue(post.author)
        post.thumbnailUrl?.let {
            thumbnail.postValue(it)
        }
        post.description?.let {
            description.postValue(it)
        }
    }

    fun getTitle(): LiveData<String> {
        return title
    }

    fun getAuthor(): LiveData<String> {
        return author
    }

    fun getThumbnail(): LiveData<String> {
        return thumbnail
    }

    fun getDescription(): LiveData<String> {
        return description
    }

}