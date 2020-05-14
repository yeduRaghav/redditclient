package com.yrgv.redditclient.network

import com.yrgv.redditclient.utils.Either
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Simple abstraction for Endpoint.
 * This way all the logic to parse the response and mapping the api response
 * to localized models can be done in one place.
 */
class GetKotlinPostsEndpoint constructor(api: RedditApi) {
    private var call = api.getPosts()

    fun execute(): Single<Either<ApiError, PostsResponse>> {
        return Single.just(call)
            .subscribeOn(Schedulers.io())
            .flatMap {
                Single.just(it.execute())
            }
            .flatMap { apiResponse ->
                return@flatMap apiResponse.body()?.let {
                    Single.just(Either.value(it))
                } ?: Single.just(Either.error(ApiError.getErrorResponse(apiResponse)))
            }
    }

}