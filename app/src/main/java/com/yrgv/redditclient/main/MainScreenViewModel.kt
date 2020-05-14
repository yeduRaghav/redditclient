package com.yrgv.redditclient.main

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yrgv.redditclient.Post
import com.yrgv.redditclient.R
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
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * ViewModel for Main Screen
 */
class MainScreenViewModel(application: Application) : AndroidViewModel(application) {

    private val resourceProvider: ResourceProvider =
        DefaultResourceProvider.getInstance(application)
    private val getPostsApiCall = GetKotlinPostsEndpoint(RedditApi.getInstance())

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
        postsDataState.postValue(Loading)
        getPostsApiCall.execute(object : SingleObserver<Either<ApiError, PostsResponse>> {
            override fun onSubscribe(d: Disposable?) {}
            override fun onError(e: Throwable?) {}
            override fun onSuccess(value: Either<ApiError, PostsResponse>?) {
                handlePostsResponse(value)
            }
        })
    }

    private fun handlePostsResponse(response: Either<ApiError, PostsResponse>?) {
        if (response == null) {
            postsDataState.postValue(FetchFailed(getErrorMessage(ApiError.getLocalizedErrorResponse())))
            return
        }
        when (response) {
            is Either.Error -> {
                postsDataState.postValue(FetchFailed(getErrorMessage(response.error)))
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
                        postsDataState.postValue(Fetched)
                        posts.postValue(localizedPosts)
                    }
            }
        }
    }


    private fun getErrorMessage(apiError: ApiError): Int {
        return when (apiError) {
            is ApiError.BadRequest,
            is ApiError.UnAuthorized,
            is ApiError.GenericError -> R.string.error_view_default_message
            is ApiError.NetworkError -> R.string.error_view_network_issue_message
        }
    }


    /**
     * Describes the state of posts data
     **/
    sealed class PostsDataState {
        object Fetched : PostsDataState()
        object Loading : PostsDataState()
        data class FetchFailed(@StringRes val messageResId: Int) : PostsDataState()
    }


}