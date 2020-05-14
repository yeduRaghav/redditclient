package com.yrgv.redditclient.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yrgv.redditclient.Post
import com.yrgv.redditclient.main.MainScreenViewModel.PostsDataState.*
import com.yrgv.redditclient.network.ApiError
import com.yrgv.redditclient.network.GetKotlinPostsEndpoint
import com.yrgv.redditclient.network.PostsResponse
import com.yrgv.redditclient.network.RedditApi
import com.yrgv.redditclient.utils.Either
import com.yrgv.redditclient.utils.extensions.toLocalModel
import com.yrgv.redditclient.utils.resourceprovider.DefaultResourceProvider
import com.yrgv.redditclient.utils.resourceprovider.ResourceProvider
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * ViewModel for Main Screen
 */
class MainScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val resourceProvider: ResourceProvider =
        DefaultResourceProvider.newInstance(application)
    private val getPostsApiCall = GetKotlinPostsEndpoint(RedditApi.newInstance())

    private val posts = MutableLiveData<List<Post>>()
    private val postsDataState = MutableLiveData<PostsDataState>()

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
        getPostsApiCall.execute()
            .subscribeOn(Schedulers.io())
            .subscribe { response ->
                handlePostsResponse(response)
            }
    }

    private fun handlePostsResponse(response: Either<ApiError, PostsResponse>) {
        when (response) {
            is Either.Error -> {
                postsDataState.postValue(FAILED)
            }
            is Either.Value -> {
                Single.just(response.value.data.children)
                    .subscribeOn(Schedulers.io())
                    .flatMap { postsFromApi ->
                        Single.just(postsFromApi.map { post ->
                            post.data.toLocalModel { resId -> resourceProvider.getString(resId) }
                        })
                    }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe { localizedPosts ->
                        postsDataState.postValue(FETCHED)
                        posts.postValue(localizedPosts)
                    }
            }
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