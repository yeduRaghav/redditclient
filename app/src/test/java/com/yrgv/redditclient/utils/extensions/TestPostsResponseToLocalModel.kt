package com.yrgv.redditclient.utils.extensions

import com.yrgv.redditclient.network.PostsResponse
import org.junit.Assert
import org.junit.Test

/**
 * Tests the logic to convert posts api response mode to local model
 */
class TestPostsResponseToLocalModel {

    //author not tested as it is covered by other tests.
    @Test
    fun testNonEmptyValuesAreSame() {
        val apiPost = PostsResponse.Post.PostData(
            id = "123",
            author = "emotionallystable007",
            title = "Orange juice offensive and here is why",
            selftext = "Big words big words",
            thumbnail = "https://www.twitter.com"
        )
        val localPost = apiPost.toLocalModel { null }
        Assert.assertTrue(localPost.id.equals(apiPost.id, true))
        Assert.assertTrue(localPost.title.equals(apiPost.title, true))
        Assert.assertTrue(localPost.description.equals(apiPost.selftext, true))
        Assert.assertTrue(localPost.thumbnailUrl.equals(apiPost.thumbnail, true))
    }

    //author not tested as it is covered by other tests.
    @Test
    fun testEmptyDescriptionIsNull() {
        val apiPost = PostsResponse.Post.PostData(
            id = "123",
            author = "emotionallystable007",
            title = "",
            selftext = "",
            thumbnail = ""
        )
        val localPost = apiPost.toLocalModel { null }
        Assert.assertTrue(localPost.description == null)
    }

    @Test
    fun testBlankDescriptionIsNull() {
        val apiPost = PostsResponse.Post.PostData(
            id = "123",
            author = "emotionallystable007",
            title = "",
            selftext = " ",
            thumbnail = ""
        )
        val localPost = apiPost.toLocalModel { null }
        Assert.assertTrue(localPost.description == null)
    }

    @Test
    fun testGetLocalizedAuthorWithStringResReturned() {
        val originalAuthor = "emotionallystable007"
        val resultantAuthor = originalAuthor.getLocalizedAuthor { "u/" }
        assert(resultantAuthor.equals("u/emotionallystable007", true))
    }

    @Test
    fun testGetLocalizedAuthorWithoutStringResReturned() {
        val originalAuthor = "emotionallystable007"
        val resultantAuthor = originalAuthor.getLocalizedAuthor { null }
        assert(resultantAuthor.equals(originalAuthor, true))
    }

}