package com.yrgv.redditclient.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import com.yrgv.redditclient.Post
import com.yrgv.redditclient.R
import com.yrgv.redditclient.utils.extensions.show
import io.noties.markwon.Markwon
import kotlinx.android.synthetic.main.activity_detail_screen.*
import kotlinx.android.synthetic.main.content_detail_screen.*

/**
 * Activity for the Detail screen
 */
class PostDetailScreenActivity : AppCompatActivity() {

    companion object {
        private const val EXTRAS_KEY_POST = "EXTRAS_KEY_POST"

        fun getIntent(post: Post, context: Context): Intent {
            return Intent(context, PostDetailScreenActivity::class.java).apply {
                putExtra(EXTRAS_KEY_POST, post)
            }
        }

        private fun getPostFromIntent(intent: Intent): Post? {
            return intent.getParcelableExtra(EXTRAS_KEY_POST)
        }
    }

    private lateinit var viewModel: PostDetailScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPostFromIntent(intent)?.let {
            setupViews()
            setupViewModel(it)
        } ?: finish()
    }

    private fun setupViews() {
        setContentView(R.layout.activity_detail_screen)
        //todo: figure out coordinator scroll behaviour
        setSupportActionBar(detail_screen_toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setDisplayShowCustomEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setupViewModel(post: Post) {
        viewModel = ViewModelProviders.of(this).get(PostDetailScreenViewModel::class.java)

        with(viewModel) {
            setData(post)
            getTitle().observe(this@PostDetailScreenActivity, Observer { title ->
                detail_screen_toolbar_title.text = title
            })
            getAuthor().observe(this@PostDetailScreenActivity, Observer { author ->
                detail_screen_author.text = author
            })
            val markwon = Markwon.builder(this@PostDetailScreenActivity).build()
            getDescription().observe(this@PostDetailScreenActivity, Observer { description ->
                markwon.setMarkdown(detail_screen_description, description)
            })
            getThumbnailUrl().observe(this@PostDetailScreenActivity, Observer { url ->
                setupThumbnail(url)
            })
        }
    }

    //todo: fix my sizing& aspect ratio
    private fun setupThumbnail(url: String) {
        detail_screen_thumbnail.show()
        Picasso.get()
            .load(url)
            .fit()
            .placeholder(R.drawable.post_thumbnail_placeholder)
            .into(detail_screen_thumbnail)
    }

}