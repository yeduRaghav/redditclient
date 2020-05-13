package com.yrgv.redditclient.network

import com.yrgv.redditclient.utils.Either
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

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
                } ?: Single.just(Either.error(getErrorResponse(apiResponse)))
            }
    }


    /**
     * Returns an appropriate localized error object.
     * Note that the response codes used here might not be accurate
     * */
    private fun getErrorResponse(response: Response<PostsResponse>): ApiError {
        return when (response.code()) {
            400 -> ApiError.BadRequest
            401 -> ApiError.UnAuthorized
            else -> ApiError.GenericError(response.errorBody()?.string())
        }
    }

}