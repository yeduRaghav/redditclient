package com.yrgv.redditclient.network

import org.junit.Assert
import org.junit.Test


class ApiErrorTest {
    @Test
    fun testBadRequestApiResponse() {
        val localizedErrorResponse =
            ApiError.getLocalizedErrorResponse(ApiError.RESPONSE_CODE_BAD_REQUEST, null)
        Assert.assertTrue(localizedErrorResponse is ApiError.BadRequest)
    }

    @Test
    fun testUnauthorizedApiResponse() {
        val localizedErrorResponse =
            ApiError.getLocalizedErrorResponse(ApiError.RESPONSE_CODE_UNAUTHORIZED, null)
        Assert.assertTrue(localizedErrorResponse is ApiError.UnAuthorized)
    }


    @Test
    fun testUnhandledErrorReturnGenericResponse() {
        val errorString = "The Quick Brown Fox"
        val localizedErrorResponse = ApiError.getLocalizedErrorResponse(411, errorString)
        Assert.assertTrue(
            localizedErrorResponse is ApiError.GenericError
                    && localizedErrorResponse.errorBody.equals(errorString, true)
        )
    }

}