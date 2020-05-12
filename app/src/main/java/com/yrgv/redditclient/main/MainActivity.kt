package com.yrgv.redditclient.main

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.yrgv.redditclient.Post
import com.yrgv.redditclient.R
import com.yrgv.redditclient.main.recyclerview.PostsRecyclerViewAdapter
import com.yrgv.redditclient.network.PostsResponse
import com.yrgv.redditclient.network.RedditApi
import com.yrgv.redditclient.utils.extensions.hide
import com.yrgv.redditclient.utils.extensions.show
import com.yrgv.redditclient.utils.extensions.start
import com.yrgv.redditclient.utils.extensions.toLocalModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    //todo: build prover VM architecture

    private val recyclerViewAdapter = PostsRecyclerViewAdapter().apply {
        setListener(object : PostsRecyclerViewAdapter.Listener {
            override fun onPostClicked(post: Post) {
                //todo: invoke VM
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        performApiCall() //todo:vm function
    }

    private fun setupViews() {
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        main_error_view.setRetryButtonClickListener {
            //todo: invoke VM
            performApiCall()
        }
        main_recycler_view.adapter = recyclerViewAdapter
    }

    private fun showLoading() {
        main_recycler_view.hide()
        main_error_view.hide()
        main_loading_view.start()
    }

    private fun showRecycle() {
        main_loading_view.hide()
        main_error_view.hide()
        main_recycler_view.show()
    }

    private fun showError() {
        main_recycler_view.hide()
        main_loading_view.hide()
        main_error_view.show()
    }

    //todo: vm function
    private fun performApiCall() {
        showLoading()
        RedditApi.newInstance()
            .getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response, exception ->
                response?.data?.children?.let {
                    handleResponse(it)
                } ?: showError()
        }
    }

    //todo: vm function
    private fun handleResponse(postFromApi: List<PostsResponse.Post>) {
        Single.just(postFromApi)
            .subscribeOn(Schedulers.io())
            .flatMap { postsFromApi ->
                Single.just(postsFromApi.map { post ->
                    post.data.toLocalModel()
                })
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { posts ->
                showRecycle()
                recyclerViewAdapter.reload(posts)
            }
    }

}
