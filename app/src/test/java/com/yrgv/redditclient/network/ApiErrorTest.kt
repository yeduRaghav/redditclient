package com.yrgv.redditclient.network

import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response


class ApiErrorTest {
    @Test
    fun testBadRequestApiResponse() {
        val responseBody = Mockito.mock(ResponseBody::class.java)
        val apiResponse = Response.error<PostsResponse>(
            ApiError.RESPONSE_CODE_BAD_REQUEST,
            responseBody
        )
        val errorResponse = ApiError.getErrorResponse(apiResponse)
        Assert.assertTrue(errorResponse is ApiError.BadRequest)
    }

    @Test
    fun testUnauthorizedApiResponse() {
        val responseBody = Mockito.mock(ResponseBody::class.java)
        val apiResponse = Response.error<PostsResponse>(
            ApiError.RESPONSE_CODE_UNAUTHORIZED,
            responseBody
        )
        val errorResponse = ApiError.getErrorResponse(apiResponse)
        Assert.assertTrue(errorResponse is ApiError.UnAuthorized)
    }


    //todo: fix me
    @Test
    fun testUnhandledErrorReturnGenericResponse() {
//        val responseBody = Mockito.mock(ResponseBody::class.java)
//        val apiResponse = Response.error<PostsResponse>(411, responseBody)
//        val errorResponse = ApiError.getErrorResponse(apiResponse)
//        Assert.assertTrue(errorResponse is ApiError.UnAuthorized)
    }

}