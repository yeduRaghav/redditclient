package com.yrgv.redditclient.utils.extensions

import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests for String.getSanitizedThumbnailUrl()
 */
class GetSanitizedThumbnailUrlTest {

    @Test
    fun testEmptyUrl() {
        val url = ""
        assertTrue(url.getSanitizedThumbnailUrl() == null)
    }

    @Test
    fun testWhitespaceUrl() {
        val url = " "
        assertTrue(url.getSanitizedThumbnailUrl() == null)
    }

    @Test
    fun testUrlSaysSelf() {
        val url = "self"
        assertTrue(url.getSanitizedThumbnailUrl() == null)
    }

    @Test
    fun testUrlStartsWithWww() {
        val url = "www.twitter.com"
        assertTrue(url.getSanitizedThumbnailUrl() == null)
    }

    @Test
    fun testUrlStartsWithHttpLowerCaseUrl() {
        val url = "http://www.twitter.com"
        assertTrue(url.getSanitizedThumbnailUrl() == null)
    }

    @Test
    fun testUrlStartsWithHttpUpperCaseUrl() {
        val url = "HTTP://www.twitter.com"
        assertTrue(url.getSanitizedThumbnailUrl() == null)
    }

    @Test
    fun testUrlStartsWithHttpsMixedCaseUrl() {
        val url = "httPs://www.twiTter.com"
        assertTrue(url.getSanitizedThumbnailUrl()?.equals(url, true) == true)
    }

    @Test
    fun testHttpsLowerCaseUrl() {
        val url = "https://www.twitter.com"
        assertTrue(url.getSanitizedThumbnailUrl()?.equals(url, true) == true)
    }

    @Test
    fun testHttpsUpperCaseUrl() {
        val url = "HTTPS://WWW.TWITTER.COM"
        assertTrue(url.getSanitizedThumbnailUrl()?.equals(url, true) == true)
    }

}