package com.yrgv.redditclient.network

import com.yrgv.redditclient.utils.Either
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Simple abstraction for Endpoint.
 * This way all the logic to parse the response and mapping the api response
 * to localized models can be done in one place.
 */
class GetKotlinPostsEndpoint constructor(private val api: RedditApi) {

    private companion object {
        const val SUB_REDDIT_NAME = "Kotlin"
    }

    fun execute(observer: SingleObserver<Either<ApiError, PostsResponse>>) {
        Single.just(api.getPosts(SUB_REDDIT_NAME))
            .subscribeOn(Schedulers.io())
            .flatMap {
                Single.just(it.execute())
            }.subscribe { apiResponse, exception ->
                exception?.let {
                    val localException = ApiError.getLocalizedErrorResponse(it)
                    observer.onSuccess(Either.error(localException))
                    return@subscribe
                }
                apiResponse.body()?.let {
                    observer.onSuccess(Either.value(it))
                } ?: run {
                    observer.onSuccess(
                        Either.error(
                            ApiError.getLocalizedErrorResponse(
                                apiResponse.code(), apiResponse.errorBody()?.string()
                            )
                        )
                    )
                }
            }
    }

}