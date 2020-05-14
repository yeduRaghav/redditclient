package com.yrgv.redditclient.network

import retrofit2.Response

/**
 * Defines localized error responses and their associated data.
 */
sealed class ApiError {
    object BadRequest : ApiError()
    object UnAuthorized : ApiError()
    data class GenericError(val errorBody: String?) : ApiError()


    companion object {
        const val RESPONSE_CODE_BAD_REQUEST = 400
        const val RESPONSE_CODE_UNAUTHORIZED = 401

        /**
         * Returns an appropriate localized error object.
         * Note that the response codes used here might not be accurate
         * */
        fun <R> getErrorResponse(response: Response<R>): ApiError {
            return when (response.code()) {
                RESPONSE_CODE_BAD_REQUEST -> BadRequest
                RESPONSE_CODE_UNAUTHORIZED -> UnAuthorized
                else -> GenericError(response.errorBody()?.string())
            }
        }
    }
}