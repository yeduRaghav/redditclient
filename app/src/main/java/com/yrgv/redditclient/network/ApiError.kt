package com.yrgv.redditclient.network

import java.net.UnknownHostException

/**
 * Defines localized error responses and their associated data.
 */
sealed class ApiError {
    object BadRequest : ApiError()
    object UnAuthorized : ApiError()
    data class NetworkError(val message: String?) : ApiError()
    data class GenericError(val errorBody: String?) : ApiError()


    companion object {
        const val RESPONSE_CODE_BAD_REQUEST = 400
        const val RESPONSE_CODE_UNAUTHORIZED = 401

        /**
         * Returns an appropriate localized error object.
         * Note that the response codes used here might not be accurate
         * */
        fun getLocalizedErrorResponse(responseCode: Int, errorBody: String?): ApiError {
            return when (responseCode) {
                RESPONSE_CODE_BAD_REQUEST -> BadRequest
                RESPONSE_CODE_UNAUTHORIZED -> UnAuthorized
                else -> GenericError(errorBody)
            }
        }

        fun getLocalizedErrorResponse(exception: Throwable? = null): ApiError {
            return when (exception) {
                is UnknownHostException -> NetworkError(exception.message)
                else -> GenericError(exception?.message)
            }
        }

    }
}