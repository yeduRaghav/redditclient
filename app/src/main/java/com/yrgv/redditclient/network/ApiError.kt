package com.yrgv.redditclient.network

/**
 * Defines localized error responses and their associated data.
 */
sealed class ApiError {
    object BadRequest : ApiError()
    object UnAuthorized : ApiError()
    data class GenericError(val message: String?) : ApiError()
}