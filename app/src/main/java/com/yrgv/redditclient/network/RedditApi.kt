package com.yrgv.redditclient.network

import android.util.Log
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 *  Retrofit Api definition.
 *  Only one endpoint in this case.
 */
interface RedditApi {

    companion object {
        private const val BASE_URL = "https://www.reddit.com/r/"
        private const val SUB_REDDIT_KOTLIN = "Kotlin"


        /**
         * A companion function that creates a retrofit instance for the Api
         * */
        fun newInstance(): RedditApi {
            val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String) {
                    Log.i("Guruve", message)
                }
            }).setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
                .create(RedditApi::class.java)
        }
    }


    @GET(SUB_REDDIT_KOTLIN)
    fun getKotlinPosts(): Single<PostsResponse>

}