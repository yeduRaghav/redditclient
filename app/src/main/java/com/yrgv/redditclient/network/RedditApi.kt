package com.yrgv.redditclient.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *  Retrofit Api definition.
 *  Only one endpoint in this case.
 */
interface RedditApi {

    companion object {
        private const val BASE_URL = "https://www.reddit.com/r/"
        private const val RESPONSE_TYPE_JSON = ".json"
        private lateinit var api: RedditApi
        /**
         * A companion function that creates a retrofit instance for the Api
         * */
        fun getInstance(): RedditApi {
            if (!::api.isInitialized) {
                val loggingInterceptor =
                    HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                        override fun log(message: String) {
                            Log.i("Guruve", message)
                        }
                    }).setLevel(HttpLoggingInterceptor.Level.BODY)

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

                api = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .build()
                    .create(RedditApi::class.java)
            }
            return api
        }
    }


    /**
     * Returns a list of posts for a subReddit
     * @param subReddit The subReddit to returns the post from.
     * */
    @GET("{subReddit}$RESPONSE_TYPE_JSON")
    fun getPosts(
        @Path("subReddit") subReddit: String
    ): Call<PostsResponse>

}