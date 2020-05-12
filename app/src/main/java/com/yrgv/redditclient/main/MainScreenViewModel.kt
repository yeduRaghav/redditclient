package com.yrgv.redditclient.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yrgv.redditclient.Post
import com.yrgv.redditclient.main.MainScreenViewModel.PostsDataState.*
import com.yrgv.redditclient.network.PostsResponse
import com.yrgv.redditclient.network.RedditApi
import com.yrgv.redditclient.utils.extensions.toLocalModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * ViewModel for Main Screen
 */
class MainScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val posts = MutableLiveData<List<Post>>()
    private val postsDataState = MutableLiveData<PostsDataState>()

    private val api = RedditApi.newInstance()

    init {
        fetchPostsFromApi()
    }

    fun getPosts(): LiveData<List<Post>> {
        return posts
    }

    fun getPostsDataState(): LiveData<PostsDataState> {
        return postsDataState
    }

    fun retry() {
        fetchPostsFromApi()
    }

    private fun fetchPostsFromApi() {
        postsDataState.postValue(LOADING)
        api.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response, _ ->
                response?.data?.children?.let {
                    handlePostsResponse(it)
                } ?: postsDataState.postValue(FAILED)
            }
    }

    private fun handlePostsResponse(postFromApi: List<PostsResponse.Post>) {
        Single.just(postFromApi)
            .subscribeOn(Schedulers.io())
            .flatMap { postsFromApi ->
                Single.just(postsFromApi.map { post ->
                    post.data.toLocalModel()
                })
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { localizedPosts ->
                postsDataState.postValue(FETCHED)
                posts.postValue(localizedPosts)
            }
    }

    /**
     * Describes the state of posts data
     * @property FETCHED Posts fetched successfully
     * @property FAILED Posts fetch failed
     * @property LOADING Posts are being fetched
     * */
    enum class PostsDataState {
        FETCHED, FAILED, LOADING
    }

}