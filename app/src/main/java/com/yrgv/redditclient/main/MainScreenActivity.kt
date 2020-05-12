package com.yrgv.redditclient.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yrgv.redditclient.Post
import com.yrgv.redditclient.R
import com.yrgv.redditclient.main.MainScreenViewModel.PostsDataState
import com.yrgv.redditclient.main.MainScreenViewModel.PostsDataState.*
import com.yrgv.redditclient.main.recyclerview.PostsRecyclerViewAdapter
import com.yrgv.redditclient.utils.extensions.hide
import com.yrgv.redditclient.utils.extensions.show
import com.yrgv.redditclient.utils.extensions.start
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainScreenActivity : AppCompatActivity() {

    private lateinit var viewModel: MainScreenViewModel

    private val recyclerViewAdapter = PostsRecyclerViewAdapter().apply {
        setListener(object : PostsRecyclerViewAdapter.Listener {
            override fun onPostClicked(post: Post) {
                launchPostDetailScreen(post)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setupViewModel()
    }

    private fun setupViews() {
        setContentView(R.layout.activity_main)
        setSupportActionBar(main_toolbar)
        main_error_view.setRetryButtonClickListener {
            viewModel.retry()
        }
        main_recycler_view.adapter = recyclerViewAdapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainScreenViewModel::class.java).apply {
            getPostsDataState().observe(this@MainScreenActivity, Observer { state ->
                handlePostsState(state)
            })
            getPosts().observe(this@MainScreenActivity, Observer { posts ->
                handlePosts(posts)
            })
        }
    }

    private fun handlePostsState(state: PostsDataState) {
        when (state) {
            FETCHED -> showRecyclerView()
            FAILED -> showErrorView()
            LOADING -> showLoadingView()
        }
    }

    private fun handlePosts(posts: List<Post>) {
        recyclerViewAdapter.reload(posts)
    }

    private fun showLoadingView() {
        main_recycler_view.hide()
        main_error_view.hide()
        main_loading_view.start()
    }

    private fun showRecyclerView() {
        main_loading_view.hide()
        main_error_view.hide()
        main_recycler_view.show()
    }

    private fun showErrorView() {
        main_recycler_view.hide()
        main_loading_view.hide()
        main_error_view.show()
    }

    private fun launchPostDetailScreen(post: Post) {
        //todo: fix me when Detail activity is built
    }

}
